package br.com.intelector.robomonitoramentoolx;

import br.com.intelector.robomonitoramentoolx.service.MonitorarAnuncioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoboMonitoramentoOlxApplicationTests {

    @Autowired
    private MonitorarAnuncioService monitorarAnuncioService;

    @Test
    void buscarAnuncio() {
        monitorarAnuncioService.procurarNovoAnuncio();
    }

}
