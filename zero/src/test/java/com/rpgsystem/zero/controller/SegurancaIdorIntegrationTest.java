package com.rpgsystem.zero.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.rpgsystem.zero.database.ConexaoDB;
import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.ItemRPG;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.repository.ItemDAO;
import com.rpgsystem.zero.repository.UsuarioDAO;

@SpringBootTest
@AutoConfigureMockMvc
class SegurancaIdorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemDAO itemDAO;

    private Usuario alice;
    private Usuario bob;
    private Ficha fichaAlice;
    private Ficha fichaBob;
    private ItemRPG itemBob;

    @BeforeEach
    void prepararBaseIsolada() throws Exception {
        try (Connection conn = ConexaoDB.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM inventario_rpg");
            stmt.execute("DELETE FROM ficha_habilidades");
            stmt.execute("DELETE FROM fichas");
            stmt.execute("DELETE FROM usuarios");
        }

        UsuarioDAO.cadastrarUsuario("Alice", "alice_sec", "SenhaAlice123!", PerfilAcesso.PLAYER);
        UsuarioDAO.cadastrarUsuario("Bob", "bob_sec", "SenhaBob123!", PerfilAcesso.PLAYER);
        UsuarioDAO.cadastrarUsuario("Mestre", "mestre_sec", "SenhaMestre123!", PerfilAcesso.MESTRE);

        alice = UsuarioDAO.buscarUsuarioPorLogin("alice_sec");
        bob = UsuarioDAO.buscarUsuarioPorLogin("bob_sec");

        FichaDAO.criarFicha(alice.getId(), "Heroina Alice", "PLAYER");
        FichaDAO.criarFicha(bob.getId(), "Guerreiro Bob", "PLAYER");

        fichaAlice = FichaDAO.buscarFichaPorUsuario(alice.getId());
        fichaBob = FichaDAO.buscarFichaPorUsuario(bob.getId());

        itemDAO.adicionarItem(new ItemRPG(null, fichaBob.getId(), "Espada do Bob", "Arma", "Raro", 1, 15));
        List<ItemRPG> itensBob = itemDAO.listarInventarioPorFicha(fichaBob.getId());
        itemBob = itensBob.get(0);
    }

    @Test
    @WithMockUser(username = "alice_sec", roles = {"PLAYER"})
    void jogadorPodeConsultarEEditarPropriaFichaEInventario() throws Exception {
        mockMvc.perform(get("/fichas/editar").param("fichaId", String.valueOf(fichaAlice.getId())))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/inventario").param("fichaId", String.valueOf(fichaAlice.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(post("/api/inventario/adicionar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fichaId\":" + fichaAlice.getId()
                                + ",\"nome\":\"Poção\",\"tipo\":\"Consumível\",\"raridade\":\"Comum\",\"quantidade\":2,\"poder\":5}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/inventario/adicionar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"SemFicha\",\"tipo\":\"Arma\",\"raridade\":\"Comum\",\"quantidade\":1,\"poder\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("fichaId é obrigatório."));
    }

    @Test
    @WithMockUser(username = "alice_sec", roles = {"PLAYER"})
    void jogadorNaoPodeConsultarEditarOuDeletarFichaDeOutroUsuario() throws Exception {
        // Tentativa de IDOR na visualização/edição
        mockMvc.perform(get("/fichas/editar")
                        .param("fichaId", String.valueOf(fichaBob.getId()))
                        .param("usuarioId", String.valueOf(bob.getId())))
                .andExpect(status().isForbidden());

        // Tentativa de IDOR no salvamento (mesmo tentando forjar usuarioId do dono)
        mockMvc.perform(post("/fichas/salvar")
                        .with(csrf())
                        .param("id", String.valueOf(fichaBob.getId()))
                        .param("usuarioId", String.valueOf(bob.getId()))
                        .param("nome", "Hackeado"))
                .andExpect(status().isForbidden());

        // Tentativa de IDOR na exclusão
        mockMvc.perform(post("/fichas/deletar")
                        .with(csrf())
                        .param("fichaId", String.valueOf(fichaBob.getId()))
                        .param("usuarioId", String.valueOf(bob.getId())))
                .andExpect(status().isForbidden());

        // Confirma integridade no banco: ficha de Bob permanece inalterada
        Ficha persistida = FichaDAO.buscarFichaPorId(fichaBob.getId());
        assertNotNull(persistida);
        assertEquals("Guerreiro Bob", persistida.getNome());
    }

    @Test
    @WithMockUser(username = "alice_sec", roles = {"PLAYER"})
    void jogadorNaoPodeListarAdicionarAlterarOuRemoverInventarioDeOutroUsuario() throws Exception {
        mockMvc.perform(get("/inventario").param("fichaId", String.valueOf(fichaBob.getId())))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/inventario").param("fichaId", String.valueOf(fichaBob.getId())))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/inventario/adicionar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fichaId\":" + fichaBob.getId()
                                + ",\"nome\":\"Item Invasor\",\"tipo\":\"Arma\",\"raridade\":\"Comum\",\"quantidade\":1,\"poder\":99}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(put("/api/inventario/atualizar/" + itemBob.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Roubado\",\"tipo\":\"Arma\",\"raridade\":\"Comum\",\"quantidade\":1,\"poder\":0}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/api/inventario/deletar/" + itemBob.getId()).with(csrf()))
                .andExpect(status().isForbidden());

        ItemRPG itemPersistido = itemDAO.buscarItemPorId(itemBob.getId());
        assertNotNull(itemPersistido);
        assertEquals("Espada do Bob", itemPersistido.getNome());
    }

    @Test
    @WithMockUser(username = "alice_sec", roles = {"PLAYER"})
    void jogadorNaoPodeAcessarRotasAdministrativasDoMestre() throws Exception {
        mockMvc.perform(get("/painel-mestre")).andExpect(status().isForbidden());
        mockMvc.perform(post("/painel-mestre/fichas/deletar")
                        .with(csrf())
                        .param("fichaId", String.valueOf(fichaBob.getId())))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/fichas/criar")
                        .with(csrf())
                        .param("nomePersonagem", "Boss Ilegal")
                        .param("tipoFicha", "BOSS"))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/Manual/criar")
                        .with(csrf())
                        .param("categoria", "Combate")
                        .param("titulo", "Regra")
                        .param("descricao", "Texto"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mestre_sec", roles = {"MESTRE"})
    void mestrePodeAcessarPainelMestreEFichaDeJogadorDentroDoEscopoAutorizado() throws Exception {
        mockMvc.perform(get("/painel-mestre")).andExpect(status().isOk());
        mockMvc.perform(get("/fichas/editar").param("fichaId", String.valueOf(fichaBob.getId())))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/inventario").param("fichaId", String.valueOf(fichaBob.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Espada do Bob"));
    }

    @Test
    @WithMockUser(username = "alice_sec", roles = {"PLAYER"})
    void fichaOuItemInexistenteRetorna404() throws Exception {
        mockMvc.perform(get("/fichas/editar").param("fichaId", "999999"))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/inventario/deletar/999999").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
