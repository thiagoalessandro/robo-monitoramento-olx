package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.domain.DominioSituacaoRegistro;
import br.com.intelector.robomonitoramentoolx.service.dto.ConfiguracaoDTO;
import br.com.intelector.robomonitoramentoolx.exception.BuscarAnuncioException;
import br.com.intelector.robomonitoramentoolx.exception.CarregarConfiguracaoException;
import br.com.intelector.robomonitoramentoolx.exception.NotificarException;
import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import br.com.intelector.robomonitoramentoolx.model.Monitoramento;
import br.com.intelector.robomonitoramentoolx.repository.MonitoramentoRepository;
import br.com.intelector.robomonitoramentoolx.service.mail.NovoAnuncioMail;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MonitoramentoService {

    @Autowired
    private MonitoramentoRepository monitoramentoRepository;

    @Autowired
    private OlxBotService olxBotService;

    @Autowired
    private NovoAnuncioMail novoAnuncioMail;

    @Autowired
    private NotificacaoTelegramService notificacaoTelegramService;

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private ConfiguracaoService configuracaoService;

    ConfiguracaoDTO configuracaoDTO;

    public void rastrear(Monitoramento monitoramento) {
        log.info("Iniciando rastreamento de {}", monitoramento.getTitulo());
        List<Anuncio> anuncios;
        try {
            configuracaoDTO = configuracaoService.inicializarConfiguracao();
            anuncios = rastrearAnuncioFromBot(monitoramento);
            if (anuncios != null && anuncios.size() > 0) {
                for (Anuncio anuncio : anuncios) {
                    analisarNovoAnuncioAndNotificar(anuncio, monitoramento);
                }
            }
        } catch (NotificarException e) {
            log.error("Ocorreu um erro ao notificar", e);
        } catch (BuscarAnuncioException e) {
            log.error("Ocorreu um erro ao buscar anúncio", e);
        } catch (CarregarConfiguracaoException e) {
            log.error("Ocorreu um erro ao carregar configurações", e);
        }
    }

    private List<Anuncio> rastrearAnuncioFromBot(Monitoramento monitoramento) throws BuscarAnuncioException {
        List<Anuncio> anuncios = null;
        log.info("BOT {}", monitoramento.getBot().name());
        switch (monitoramento.getBot()) {
            case OLX:
                anuncios = olxBotService.buscarAnuncios(monitoramento);
                break;
            default:
                log.info("Não existe implementação para o BOT {}", monitoramento.getBot().name());
        }
        return anuncios;
    }

    private void analisarNovoAnuncioAndNotificar(Anuncio anuncio, Monitoramento monitoramento) throws NotificarException {
        if (!anuncioService.existsAnuncio(anuncio.getId())) {
            log.info("Anúncio encontrado: {} - {}", anuncio.getId(), anuncio.getTitulo());
            salvarNovoAnuncio(anuncio, monitoramento);
            notificar(anuncio);
        }
    }

    private void salvarNovoAnuncio(Anuncio anuncio, Monitoramento monitoramento) {
        anuncio.setMonitoramento(monitoramento);
        anuncioService.salvar(anuncio);
    }

    private void notificar(Anuncio anuncio) throws NotificarException {
        try {
            if (configuracaoDTO.getPrimeiraExecucao().equals("N")) {
                if (configuracaoDTO.getNotificarViaEmail().equals("S"))
                    novoAnuncioMail.sendEmail(configuracaoDTO.getEmailTo(), ":)", anuncio);
                if (configuracaoDTO.getNotificarViaTelegram().equals("S"))
                    notificacaoTelegramService.notificar(anuncio.getTitulo(), anuncio.getLink());
            }
        } catch (Exception e) {
            throw new NotificarException(e);
        }
    }

    public List<Monitoramento> listAllByAtivo() {
        return monitoramentoRepository.findBySituacao(DominioSituacaoRegistro.ATIVO);
    }

}
