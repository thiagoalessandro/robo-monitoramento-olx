package br.com.intelector.gerenciarcontapagar.service.mail;

import org.springframework.stereotype.Component;

@Component
public class NovoAnuncioMail extends MailSenderAbstract{

    private static final String SUBJECT_MAIL = "Anúncio detectado";

    public NovoAnuncioMail(){
        super(SUBJECT_MAIL);
    }

    /*public void sendEmail (String to, String nome, Anuncio anuncio) throws IOException {
        Map<String, String> templateValues = new HashMap<>();
        templateValues.put("#NOME", nome);
        templateValues.put("#TITULO", anuncio.getTitulo());
        templateValues.put("#DETALHE", anuncio.getDetalhe());
        templateValues.put("#VALOR", anuncio.getValor());
        templateValues.put("#IMAGEM", anuncio.getImagem());
        templateValues.put("#MONITORAMENTO", anuncio.getMonitoramento().getTitulo());
        templateValues.put("#BOT", anuncio.getMonitoramento().getBot().name());
        templateValues.put("#LINK", anuncio.getLink());
        sendEmail(to, templateValues);
    }*/

    @Override
    protected String htmlContent() {
        String html = "Segue abaixo o resumo do novo anúncio: <br /><br />\n" +

"                      <b>Robô:</b> #BOT <br />\n" +
"                      <b>Monitoramento:</b> #MONITORAMENTO <br /><br />\n" +

"                      <a href=\"#LINK\">#IMAGEM</a> <br />\n" +
"                      <b>Título:</b> #TITULO <br />\n" +
"                      <b>Detalhe:</b> #DETALHE <br />\n" +
"                      <b>Valor:</b> #VALOR <br /><br />\n" +

"                      Segue o link do anúncio:<br />\n" +
"                      <a href=\"#LINK\">#LINK</a>";
        return html;
    }

}
