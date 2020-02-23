package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO.AnuncioDTO;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@Service
public class OlxService {

    public Optional<AnuncioDTO> buscarVeiculoRecente(String marca, String modelo, String ano) throws IOException {
        String baseUrl = gerarLinkBuscaVeiculo(marca, modelo, ano);
        return buscarAnuncioFromUrl(baseUrl);
    }

    private Optional<AnuncioDTO> buscarAnuncioFromUrl(String baseUrl) throws IOException {
        AnuncioDTO anuncioDTO = new AnuncioDTO();
        Document document = Jsoup.connect(baseUrl).get();
        Element ultimoAnuncio = document.select("#main-ad-list > li:nth-child(1)").get(0);
        anuncioDTO.setId(ultimoAnuncio.attributes().get("data-list_id"));
        anuncioDTO.setTitulo(ultimoAnuncio.select("h2.OLXad-list-title").get(0).html());
        anuncioDTO.setValor(ultimoAnuncio.select("p.OLXad-list-price").get(0).html());
        anuncioDTO.setDetalhe(ultimoAnuncio.select("p.detail-specific").get(0).html());
        anuncioDTO.setLink(ultimoAnuncio.select("a.OLXad-list-link-featured").get(0).attributes().get("href"));
        anuncioDTO.setImagem(ultimoAnuncio.select("div.OLXad-list-image-box > img").get(0).toString());
        return Optional.ofNullable(anuncioDTO);
    }

    private String gerarLinkBuscaVeiculo(String marca, String modelo, String ano) {
        StringBuilder link = new StringBuilder("https://pa.olx.com.br/autos-e-pecas/carros-vans-e-utilitarios");
        link.append("/");
        link.append(marca);
        link.append("/");
        link.append(modelo);
        link.append("/");
        link.append(ano);
        link.append("?sf=1");
        return link.toString();
    }

}
