package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import br.com.intelector.robomonitoramentoolx.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    public boolean existsAnuncio(Long id) {
        return anuncioRepository.existsById(id);
    }

    public void salvar(Anuncio anuncio) {
        anuncioRepository.save(anuncio);
    }
}
