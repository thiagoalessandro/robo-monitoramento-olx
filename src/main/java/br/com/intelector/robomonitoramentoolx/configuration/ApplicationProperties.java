package br.com.intelector.robomonitoramentoolx.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${app.email.from}")
    private String appEmailFrom;

    @Value("${app.email.to}")
    private String appEmailTo;

    @Value("${app.email.recipientName}")
    private String appEmailRecipientName;

}
