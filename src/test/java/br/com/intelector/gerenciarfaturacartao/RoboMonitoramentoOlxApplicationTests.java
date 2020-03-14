package br.com.intelector.gerenciarfaturacartao;

import br.com.intelector.gerenciarfaturacartao.service.ArquivoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class RoboMonitoramentoOlxApplicationTests {

    @Autowired
    private ArquivoService arquivoService;

    @Test
    void importarArquivo() {
        try {
            arquivoService.processarArquivo();
        } catch (Exception e) {
            fail("Erro ao importar arquivo", e);
        }
    }

}
