package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.configuration.ApplicationProperties;
import br.com.intelector.robomonitoramentoolx.exception.PrimeiraExecucaoException;
import br.com.intelector.robomonitoramentoolx.model.Monitoramento;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SchedulerMonitoramentoService {

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private MonitoramentoService monitoramentoService;

    @Autowired
    private ConfiguracaoService configuracaoService;

    @Scheduled(cron = "${app.cronScheduler}")
    public void monitorar() {
        log.info("Procurando novos anúncios...");
        rastrearNovosAnuncios();
        log.info("Encerrando busca");
    }

    public void rastrearNovosAnuncios() {
        List<Monitoramento> monitoramentos;
        try {
            monitoramentos = monitoramentoService.listAllByAtivo();
            for (Monitoramento monitoramento : monitoramentos) {
                monitoramentoService.rastrear(monitoramento);
            }
            configuracaoService.atualizarPrimeriaExecucao();
        } catch (PrimeiraExecucaoException e) {
            log.error("Ocorreu um erro ao atualizar primeira execução", e);
        }
    }

}
