package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.domain.DominioSituacaoRegistro;
import br.com.intelector.robomonitoramentoolx.exception.PrimeiraExecucaoException;
import br.com.intelector.robomonitoramentoolx.model.Configuracao;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
                    log.debug("Anuncio: {}", anuncio.toString());
                    analisarNovoAnuncioAndNotificar(anuncio, monitoramento);
                }
            }
        } catch (NotificarException e) {
            log.error("Ocorreu um erro ao notificar", e);
        } catch (BuscarAnuncioException e) {
            log.error("Ocorreu um erro ao buscar anúncio", e);
        } catch (CarregarConfiguracaoException e) {
            log.error("Ocorreu um erro ao carregar configurações", e);
        }finally {
            try {
                atualizarPrimeriaExecucao(monitoramento);
            } catch (PrimeiraExecucaoException e) {
                log.error("Ocorreu um erro ao atualizar primaeira execução do monitoramento", e);
            }
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
        Optional<Anuncio> anuncioEntity = anuncioService.findById(anuncio.getId());
        if(anuncioEntity.isPresent()) {
            log.info("Atualizando anúncio: {} - {}", anuncio.getId(), anuncio.getTitulo());
            anuncioEntity.get().setDhAtu(new Date());
            anuncioService.salvar(anuncioEntity.get());
        }else {
            log.info("Anúncio encontrado: {} - {}", anuncio.getId(), anuncio.getTitulo());
            anuncio.setDhCadastro(new Date());
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
            if (anuncio.getMonitoramento().getPrimeiraExecucao().equals("N")) {
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

    public void atualizarPrimeriaExecucao(Monitoramento monitoramento) throws PrimeiraExecucaoException {
        try {
            if (monitoramento.getPrimeiraExecucao().equals("S")) {
                monitoramento.setPrimeiraExecucao("N");
                monitoramentoRepository.save(monitoramento);
            }
        } catch (Exception e) {
            throw new PrimeiraExecucaoException(e);
        }
    }

}
