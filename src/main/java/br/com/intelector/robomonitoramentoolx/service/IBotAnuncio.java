package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.exception.BuscarAnuncioException;
import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import br.com.intelector.robomonitoramentoolx.model.Monitoramento;

import java.util.List;

public interface IBotAnuncio {

    String getUsuarioBot();

    List<Anuncio> carregarAnunciosFromUrl(String urlBase) throws BuscarAnuncioException;

    default List<Anuncio> buscarAnuncios(Monitoramento monitoramento) throws BuscarAnuncioException {
        return carregarAnunciosFromUrl(monitoramento.getLink());
    }

}
