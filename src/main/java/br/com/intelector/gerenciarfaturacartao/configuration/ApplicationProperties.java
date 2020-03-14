package br.com.intelector.gerenciarfaturacartao.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${app.email.from}")
    private String appEmailFrom;

    @Value("${app.nomeArquivo}")
    private String nomeArquivo;

    @Value("${app.diretorioArquivo}")
    private String appDiretorioArquivo;

}
