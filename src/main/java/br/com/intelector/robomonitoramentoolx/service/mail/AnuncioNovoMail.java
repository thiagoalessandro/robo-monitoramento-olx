package br.com.intelector.robomonitoramentoolx.service.mail;

import br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO.AnuncioDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AnuncioNovoMail extends MailSenderAbstract{

    private static final String SUBJECT_MAIL = "Robô OLX - Novo anúncio detectado";

    public AnuncioNovoMail(){
        super(SUBJECT_MAIL);
    }

    public void sendEmail (String to, String nome, AnuncioDTO anuncioDTO) throws IOException {
        Map<String, String> templateValues = new HashMap<>();
        templateValues.put("#NOME", nome);
        templateValues.put("#TITULO", anuncioDTO.getTitulo());
        templateValues.put("#DETALHE", anuncioDTO.getDetalhe());
        templateValues.put("#VALOR", anuncioDTO.getValor());
        templateValues.put("#IMAGEM", anuncioDTO.getImagem());
        templateValues.put("#LINK", anuncioDTO.getLink());
        sendEmail(to, templateValues);
    }

    @Override
    protected String htmlContent() {
        String html = "Olá #NOME. <br /><br />Segue abaixo o resumo do novo anúncio: <br /><br />\n" +

"                      <a href=\"#LINK\">#IMAGEM</a> <br /><br />\n" +

"                      <b>Título:</b> #TITULO <br /><br />\n" +
"                      <b>Detalhe:</b> #DETALHE <br /><br />\n" +
"                      <b>Valor:</b> #VALOR <br /><br /><br />\n" +

"                      Segue o link do anúncio:<br /> <br />\n" +
"                      <a href=\"#LINK\">#LINK</a>";
        return html;
    }

}
