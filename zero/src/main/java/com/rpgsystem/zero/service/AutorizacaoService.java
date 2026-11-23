package com.rpgsystem.zero.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rpgsystem.zero.model.Ficha;
import com.rpgsystem.zero.model.ItemRPG;
import com.rpgsystem.zero.model.PerfilAcesso;
import com.rpgsystem.zero.model.Usuario;
import com.rpgsystem.zero.repository.FichaDAO;
import com.rpgsystem.zero.repository.ItemDAO;
import com.rpgsystem.zero.repository.UsuarioDAO;

@Service
public class AutorizacaoService {

    private static final Logger log = LoggerFactory.getLogger(AutorizacaoService.class);

    private final ItemDAO itemDAO;

    public AutorizacaoService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public Usuario exigirUsuarioAutenticado(Authentication authentication) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado.");
        }

        Usuario usuario = UsuarioDAO.buscarUsuarioPorLogin(authentication.getName());
        if (usuario == null) {
            log.warn("Acesso negado: login autenticado não encontrado na base de usuários.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida.");
        }
        return usuario;
    }

    public boolean isMestreOuAdmin(Usuario usuario) {
        if (usuario == null || usuario.getPerfil() == null) {
            return false;
        }
        return PerfilAcesso.MESTRE.equals(usuario.getPerfil())
                || PerfilAcesso.ADMIN.equals(usuario.getPerfil());
    }

    public void exigirMestreOuAdmin(Usuario usuario, String operacao) {
        if (!isMestreOuAdmin(usuario)) {
            log.warn("Acesso negado [403]: usuarioId={} perfil={} tentou operacao={}",
                    usuario.getId(), usuario.getPerfil(), operacao);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso restrito a Mestres e Administradores.");
        }
    }

    public Ficha exigirAcessoFicha(Usuario usuario, int fichaId) {
        Ficha ficha = FichaDAO.buscarFichaPorId(fichaId);
        if (ficha == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ficha não encontrada.");
        }

        boolean isDono = ficha.getUsuarioId() == usuario.getId();
        if (!isDono && !isMestreOuAdmin(usuario)) {
            log.warn("Tentativa de acesso indevido (IDOR) bloqueada: usuarioId={} tentou acessar fichaId={}",
                    usuario.getId(), fichaId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar esta ficha.");
        }
        return ficha;
    }

    public ItemRPG exigirAcessoItem(Usuario usuario, Long itemId) {
        ItemRPG item = itemDAO.buscarItemPorId(itemId);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado.");
        }
        if (item.getFichaId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Item sem vínculo válido de ficha.");
        }
        exigirAcessoFicha(usuario, item.getFichaId());
        return item;
    }
}
