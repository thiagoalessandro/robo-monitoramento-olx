package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.configuration.ApplicationProperties;
import br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO.AnuncioDTO;
import br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO.DbDTO;
import br.com.intelector.robomonitoramentoolx.enums.TagBusca.TagBusca;
import br.com.intelector.robomonitoramentoolx.service.mail.AnuncioNovoMail;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@Service
public class MonitorarAnuncioService {

    @Autowired
    private OlxService olxService;

    @Autowired
    private DbService dbService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private AnuncioNovoMail anuncioNovoMail;

    @Scheduled(cron = "0 0/2 * * * *")
    public void monitorar(){
        log.info("Procurando novo anúncio...");
        procurarNovoAnuncio();
        log.info("Encerrando busca");
    }

    public void procurarNovoAnuncio() {
        Optional<AnuncioDTO> anuncioDTO;
        try {
            anuncioDTO = olxService.buscarVeiculoRecente("fiat", "argo", "2018");
            verificarUltimoAnuncio(anuncioDTO, TagBusca.FIATARGO2018);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao procurar anúncio", e);
        }
    }

    private void verificarUltimoAnuncio(Optional<AnuncioDTO> anuncioDTO, TagBusca tagBusca) throws IOException {
        boolean novo = false;
        if (anuncioDTO.isPresent()) {
            if (!anuncioDTO.get().getId().equals(dbService.loadDb().getIdFiatArgo2018())) {
                log.info("Anúncio novo encontrado");
                anuncioNovoMail.sendEmail(applicationProperties.getAppEmailTo(), applicationProperties.getAppEmailRecipientName(), anuncioDTO.get());
                atualizarUltimoAnuncio(anuncioDTO.get(), tagBusca);
                novo = true;
            }
        }
        if (!novo)
            log.info("Nenhum anúncio novo encontrado");
    }

    private void atualizarUltimoAnuncio(AnuncioDTO anuncioDTO, TagBusca tagBusca) {
        DbDTO dbDTO = dbService.loadDb();
        switch (tagBusca) {
            case FIATARGO2018:
                dbDTO.setIdFiatArgo2018(anuncioDTO.getId());
                break;
        }
        dbService.atualizar(dbDTO);
    }

}
