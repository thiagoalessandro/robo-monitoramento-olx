package br.com.intelector.gerenciarcontapagar;

import br.com.intelector.gerenciarcontapagar.service.ArquivoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class GerenciarContaPagarApplicationTests {

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
