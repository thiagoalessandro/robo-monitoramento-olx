package br.com.intelector.robomonitoramentoolx;

import br.com.intelector.robomonitoramentoolx.service.NotificacaoTelegramService;
import br.com.intelector.robomonitoramentoolx.service.SchedulerMonitoramentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class RoboMonitoramentoOlxApplicationTests {

    @Autowired
    private SchedulerMonitoramentoService schedulerMonitoramentoService;

    @Autowired
    private NotificacaoTelegramService notificacaoTelegramService;

    @Test
    void buscarAnuncio() {
        try {
            schedulerMonitoramentoService.rastrearNovosAnuncios();
        } catch (Exception e) {
            fail("Erro ao rastrear novos anúncios", e);
        }
    }

    @Test
    void notificarTelegram() {
        String mensagem ="Mensagem de teste";
        String link = "https://pa.olx.com.br/regiao-de-belem/autos-e-pecas/carros-vans-e-utilitarios/fiat/argo/2018?sf=1";
        try {
            notificacaoTelegramService.notificar(mensagem, link);
        } catch (Exception e) {
            fail("Erro ao enviar mensagem ao bot do telegram", e);
        }
    }

}
