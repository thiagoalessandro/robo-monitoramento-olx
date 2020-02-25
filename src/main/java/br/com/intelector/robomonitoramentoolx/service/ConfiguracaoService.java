package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.service.dto.ConfiguracaoDTO;
import br.com.intelector.robomonitoramentoolx.exception.CarregarConfiguracaoException;
import br.com.intelector.robomonitoramentoolx.exception.PrimeiraExecucaoException;
import br.com.intelector.robomonitoramentoolx.model.Configuracao;
import br.com.intelector.robomonitoramentoolx.repository.ConfiguracaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public String getValue(String nome) {
        try {
            return configuracaoRepository.findByNome(nome).get().getValor();
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public ConfiguracaoDTO inicializarConfiguracao() throws CarregarConfiguracaoException {
        ConfiguracaoDTO configuracaoDTO = new ConfiguracaoDTO();
        log.info("Carregando configurações...");
        try {
            configuracaoDTO.setEmailTo(getValue("EMAIL_TO"));
            log.info("EMAIL_TO: {}", configuracaoDTO.getEmailTo());
            configuracaoDTO.setNotificarViaEmail(getValue("NOTIFICAR_VIA_EMAIL"));
            log.info("NOTIFICAR_VIA_EMAIL: {}", configuracaoDTO.getNotificarViaEmail());
            configuracaoDTO.setNotificarViaTelegram(getValue("NOTIFICAR_VIA_TELEGRAM"));
            log.info("NOTIFICAR_VIA_TELEGRAM: {}", configuracaoDTO.getNotificarViaTelegram());
            configuracaoDTO.setPrimeiraExecucao(getValue("PRIMEIRA_EXECUCAO"));
            log.info("PRIMEIRA_EXECUCAO: {}", configuracaoDTO.getPrimeiraExecucao());
        } catch (Exception e) {
            throw new CarregarConfiguracaoException(e);
        }
        return configuracaoDTO;
    }

    public void atualizarPrimeriaExecucao() throws PrimeiraExecucaoException {
        Optional<Configuracao> configuracao;
        try {
            configuracao = configuracaoRepository.findByNome("PRIMEIRA_EXECUCAO");
            if (configuracao.isPresent() && configuracao.get().getValor().equals("S")) {
                configuracao.get().setValor("N");
                configuracaoRepository.save(configuracao.get());
            }
        } catch (Exception e) {
            throw new PrimeiraExecucaoException(e);
        }
    }
}
