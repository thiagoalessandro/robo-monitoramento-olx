package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.exception.BuscarAnuncioException;
import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class OlxBotService implements IBotAnuncio {

    @Override
    public String getUsuarioBot() {
        return "BOT_OLX";
    }

    @Override
    public List<Anuncio> carregarAnunciosFromUrl(String baseUrl) throws BuscarAnuncioException {
        List<Anuncio> anuncios = new ArrayList();
        try {
            Document document = Jsoup.connect(baseUrl).get();
            document.select("#main-ad-list > li").forEach(element -> {
                Anuncio anuncio = new Anuncio();
                if (element.attributes().hasKey("data-list_id")) {
                    anuncio.setId(Long.valueOf(element.attributes().get("data-list_id")));
                    if (!element.select("h2.OLXad-list-title").isEmpty())
                        anuncio.setTitulo(element.select("h2.OLXad-list-title").get(0).html());
                    else
                        anuncio.setTitulo("Sem titulo");
                    if (!element.select("p.OLXad-list-price").isEmpty())
                        anuncio.setValor(element.select("p.OLXad-list-price").get(0).html());
                    else
                        anuncio.setValor("Sem valor");
                    if (!element.select("p.detail-specific").isEmpty())
                        anuncio.setDetalhe(element.select("p.detail-specific").get(0).html());
                    else
                        anuncio.setDetalhe("Sem detalhe");
                    if (!element.select("a.OLXad-list-link-featured").isEmpty())
                        anuncio.setLink(element.select("a.OLXad-list-link-featured").get(0).attributes().get("href"));
                    else if (!element.select("a.OLXad-list-link").isEmpty())
                        anuncio.setLink(element.select("a.OLXad-list-link").get(0).attributes().get("href"));
                    if (!element.select("div.OLXad-list-image-box > img").isEmpty())
                        anuncio.setImagem(element.select("div.OLXad-list-image-box > img").get(0).toString());
                    anuncio.setCdUsuAtu(getUsuarioBot());
                    anuncios.add(anuncio);
                }
            });
        }catch (Exception e){
            throw new BuscarAnuncioException(e);
        }
        return anuncios;
    }


}
