package com.rpgsystem.zero.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class LogEmailService {

    private static final String EMAIL = System.getenv("SMTP_EMAIL");
    private static final String SENHA = System.getenv("SMTP_PASSWORD");
    private static final String SMTP_HOST = System.getenv("SMTP_HOST");
    private static final String SMTP_PORT = System.getenv("SMTP_PORT");

    public static void enviarLog(String assunto, String mensagem) {

        if (EMAIL == null || SENHA == null || SMTP_HOST == null || SMTP_PORT == null) {
            System.err.println("LogEmailService: variáveis de ambiente SMTP não configuradas. Log não enviado.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, SENHA);
            }
        });

        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(EMAIL));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL));
            email.setSubject(assunto);
            email.setText(mensagem);
            Transport.send(email);
            System.out.println("Log enviado por email!");
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }
}