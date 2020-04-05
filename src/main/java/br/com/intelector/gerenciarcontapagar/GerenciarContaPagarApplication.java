package br.com.intelector.gerenciarcontapagar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GerenciarContaPagarApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciarContaPagarApplication.class, args);
    }

}
