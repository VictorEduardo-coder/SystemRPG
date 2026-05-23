package com.rpgsystem.zero.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class LogEmailService {

    // ==========================================
    // CONFIGURAÇÕES
    // ==========================================

    private static final String EMAIL =
            "rpg.zero.0@gmail.com";

    private static final String SENHA =
            "lebe zmbb xpgw tvbf";

    // ==========================================
    // ENVIAR LOG
    // ==========================================

    public static void enviarLog(

            String assunto,

            String mensagem

    ) {

        // ======================================
        // SMTP GMAIL
        // ======================================

        Properties props = new Properties();

        props.put(
                "mail.smtp.auth",
                "true"
        );

        props.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        props.put(
                "mail.smtp.host",
                "smtp.gmail.com"
        );

        props.put(
                "mail.smtp.port",
                "587"
        );

        // ======================================
        // AUTENTICAÇÃO
        // ======================================

        Session session = Session.getInstance(

                props,

                new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                EMAIL,
                                SENHA
                        );
                    }
                }
        );

        try {

            // ==================================
            // CRIAR EMAIL
            // ==================================

            Message email = new MimeMessage(session);

            email.setFrom(
                    new InternetAddress(EMAIL)
            );

            email.setRecipients(

                    Message.RecipientType.TO,

                    InternetAddress.parse(EMAIL)
            );

            email.setSubject(assunto);

            email.setText(mensagem);

            // ==================================
            // ENVIAR
            // ==================================

            Transport.send(email);

            System.out.println(
                    "Log enviado por email!"
            );

        } catch (MessagingException e) {

            System.out.println(
                    "Erro ao enviar email:"
            );

            e.printStackTrace();
        }
    }
}