package com.umg.absolwentbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

@ComponentScan(basePackages = { "com.umg.absolwentbackend.spring.mail" })
@PropertySource(value={"classpath:application.properties"})
public class EmailConfiguration {

    @Value("${spring.mail.host}")
    private String mailServerHost;

    @Value("${spring.mail.port}")
    private Integer mailServerPort;

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;
/*
    @Value("${spring.mail.templates.path}")
    private String mailTemplatesPath;
*/
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
