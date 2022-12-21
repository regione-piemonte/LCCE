/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.util;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import it.csi.solconfig.batchabilitazconfig.configuration.Configuration;

import java.util.Properties;

public class JavaMailUtil {

    private  String confMailHost = Configuration.get("mail.host");

    private  String confMailPort = Configuration.get("mail.port");

    private  String confMailUsername = Configuration.get("mail.username");

    private  String confMailPassword = Configuration.get("mail.password");

    private  String auth = Configuration.get("mail.smtpAuth");

    private  String sslEnable = Configuration.get("mail.smtpSslEnable");
    
    private  String mailFrom = Configuration.get("mail.from");

    public String getConfMailHost() {
        return confMailHost;
    }

    public void setConfMailHost(String confMailHost) {
        this.confMailHost = confMailHost;
    }

    public String getConfMailPort() {
        return confMailPort;
    }

    public void setConfMailPort(String confMailPort) {
        this.confMailPort = confMailPort;
    }

    public String getConfMailUsername() {
        return confMailUsername;
    }

    public void setConfMailUsername(String confMailUsername) {
        this.confMailUsername = confMailUsername;
    }

    public String getConfMailPassword() {
        return confMailPassword;
    }

    public void setConfMailPassword(String confMailPassword) {
        this.confMailPassword = confMailPassword;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSslEnable() {
        return sslEnable;
    }

    public void setSslEnable(String sslEnable) {
        this.sslEnable = sslEnable;
    }

    public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void sendSimpleMailMessage(String mailTo, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", confMailHost);
        properties.put("mail.smtp.port", confMailPort);
        properties.put("mail.smtp.starttls.enable", sslEnable);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.user", confMailUsername);
        properties.put("mail.smtp.password", confMailPassword);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(confMailUsername, confMailPassword);
            }
        });

        session.setDebug(true);

        prepareMessage(session, mailTo, subject, body);
    }

    private void prepareMessage(Session session, String mailTo, String subject, String body) throws MessagingException {
        Transport transport = session.getTransport();

        Message message = new MimeMessage(session);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(mailFrom));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setText(body);

        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

}
