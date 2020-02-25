package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.configuration.ApplicationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Log4j2
@Service
public class NotificacaoTelegramService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public void notificar(String message, String link) throws UnsupportedEncodingException, InterruptedException {
        String baseUrl = applicationProperties.getAppTelegramBaseUrl().concat(applicationProperties.getAppTelegramToken()).concat("/sendMessage");
        message = message.replace("*","");
        message = "*"+message+"*";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("chat_id", applicationProperties.getAppTelegramChatId())
                .queryParam("parse_mode", "Markdown")
                .queryParam("text", URLEncoder.encode(message+"  ", "UTF-8").concat(link));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(builder.toUriString(), String.class);
        log.info("Notificação enviada ao bot do telegram");
    }
    
}
