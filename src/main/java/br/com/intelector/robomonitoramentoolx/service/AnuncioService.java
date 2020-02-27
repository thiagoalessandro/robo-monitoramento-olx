package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import br.com.intelector.robomonitoramentoolx.repository.AnuncioRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    public boolean existsAnuncio(Long id) {
        return anuncioRepository.existsById(id);
    }

    public Optional<Anuncio> findById(Long id) {
        return anuncioRepository.findById(id);
    }

    public void salvar(Anuncio anuncio) {
        log.debug("Anuncio salvo: {}", anuncio);
        anuncioRepository.save(anuncio);
    }

    public void deletar(Anuncio anuncio) {
        log.debug("Anuncio removido: {}", anuncio);
        anuncioRepository.delete(anuncio);
    }

    public void removerAnuncioAntigo(){
        List<Anuncio> anuncios =  anuncioRepository.findByDataRetroativa(diaAnterior());
        if(!anuncios.isEmpty()) {
            log.info("{} anúncios a serem removidos", anuncios.size());
            anuncios.forEach(this::deletar);
        }
    }

    public Date diaAnterior(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH , -1);
        return cal.getTime();
    }

}
