package br.com.intelector.gerenciarfaturacartao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GerenciarFaturaCartaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciarFaturaCartaoApplication.class, args);
    }

}
