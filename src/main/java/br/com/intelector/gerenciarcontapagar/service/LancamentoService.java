package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.domain.*;
import br.com.intelector.gerenciarcontapagar.dto.LancamentoDTO;
import br.com.intelector.gerenciarcontapagar.exception.ArquivoException;
import br.com.intelector.gerenciarcontapagar.model.ContaRecorrente;
import br.com.intelector.gerenciarcontapagar.model.Lancamento;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import br.com.intelector.gerenciarcontapagar.repository.LancamentoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class LancamentoService {

    private final String USUARIO = "BOT";

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ContaRecorrenteService contaRecorrenteService;

    @Autowired
    private LembreteService lembreteService;

    public void processarLancamentosPagos(List<LancamentoDTO> lancamentosAtuais, DominioCartao cartao) {
        log.info("Processando lançamentos pagos...");
        List<Lancamento> lancamentosPendentes;
        if (lancamentosAtuais.size() > 0) {
            lancamentosPendentes = findBySituacaoLancamentoAndCartao(DominioSituacaoLancamento.PENDENTE, cartao);
            lancamentosPendentes.forEach(lancamento -> validarLancamentoPago(lancamento, lancamentosAtuais));
        }
    }

    public void processarLancamentosPendentes(List<LancamentoDTO> lancamentosAtuais) throws ArquivoException, ParseException {
        log.info("Processando lançamentos pendentes...");
        for (LancamentoDTO lancamentosAtuai : lancamentosAtuais) {
            verificarLancamentoPendente(lancamentosAtuai);
        }
    }

    public void processarLancamentosNovos(List<LancamentoDTO> lancamentosAtuais, DominioCartao dominioCartao) throws ArquivoException, ParseException {
        log.info("Processando lançamentos novos...");
        for (LancamentoDTO lancamentoAtual : lancamentosAtuais) {
            verificarLancamentoNovo(lancamentoAtual, dominioCartao);
        }
    }

    private void verificarLancamentoPendente(LancamentoDTO lancamentoDTO) throws ArquivoException, ParseException {
        findByHashAndSituacaoLancamento(DominioSituacaoLancamento.PENDENTE, lancamentoDTO.getHash())
                .ifPresent(lancamento -> atualizarLancamentoPendente(lancamento, lancamentoDTO));
    }

    private void verificarLancamentoNovo(LancamentoDTO lancamentoDTO, DominioCartao dominioCartao) throws ArquivoException, ParseException {
        if (!existsByHash(lancamentoDTO.getHash()))
            cadastrarLancamento(lancamentoDTO, dominioCartao);
    }

    private void validarLancamentoPago(Lancamento lancamento, List<LancamentoDTO> lancamentosAtuais) {
        long correspondencia = lancamentosAtuais
                .parallelStream()
                .filter(lancamentoDTO -> {
                    try {
                        return lancamento.getHash().equals(lancamentoDTO.getHash());
                    } catch (ArquivoException | ParseException e) {
                        log.error("Erro ao gerar hash", e);
                    }
                    return false;
                })
                .count();
        if (correspondencia == 0L)
            atualizarLancamentoPago(lancamento);
    }

    private void atualizarLancamentoPago(Lancamento lancamento) {
        log.info("Atualizando lançamento pago...");
        log.debug(lancamento.toString());

        lancamento.setSituacaoLancamento(DominioSituacaoLancamento.PAGO);
        if (lancamento.getQtdParcela() != null) {
            lancamento.setQtdParcelaAtual(lancamento.getQtdParcela());
            lancamento.setQtdParcelaRestante(0);
        }
        repository.save(lancamento);
    }

    private void atualizarLancamentoPendente(Lancamento lancamento, LancamentoDTO lancamentoDTO) {
        log.info("Atualizando lançamento pendente...");
        log.debug(lancamento.toString());

        int qtdParcelaTotal;
        int qtdParcelaPaga;
        int qtdParcelaRestante;
        if (lancamento.getQtdParcela() != null) {
            qtdParcelaPaga = Integer.parseInt(lancamentoDTO.getParcela()[0]);
            qtdParcelaTotal = Integer.parseInt(lancamentoDTO.getParcela()[1]);

            qtdParcelaRestante = qtdParcelaTotal - qtdParcelaPaga;
            lancamento.setQtdParcelaAtual(qtdParcelaPaga);
            lancamento.setQtdParcelaRestante(qtdParcelaRestante);
        }
        repository.save(lancamento);
    }

    private void cadastrarLancamento(LancamentoDTO lancamentoDTO, DominioCartao cartao) throws ArquivoException, ParseException {
        log.info("Cadastrando novo lançamento ...");
        log.debug(lancamentoDTO.toString());

        Optional<ContaRecorrente> contaRecorrente;
        Optional<Lembrete> lembrete;
        DominioResponsavel responsavel = DominioResponsavel.INDEFINIDO;
        String observacao = null;

        Lancamento lancamento = new Lancamento();
        lancamento.setDataCompra(lancamentoDTO.getDataCompra());
        lancamento.setHash(lancamentoDTO.getHash());
        lancamento.setDescricao(lancamentoDTO.getDescricao());
        lancamento.setQtdParcela(Integer.parseInt(lancamentoDTO.getParcela()[1]));
        lancamento.setQtdParcelaAtual(Integer.parseInt(lancamentoDTO.getParcela()[0]));
        lancamento.setQtdParcelaRestante(lancamento.getQtdParcela() - lancamento.getQtdParcelaAtual());
        lancamento.setSituacaoLancamento(DominioSituacaoLancamento.PENDENTE);
        lancamento.setValor(new BigDecimal(lancamentoDTO.getValor()));

        contaRecorrente = contaRecorrenteService.procurarContaRecorrente(lancamento.getDescricao());
        if (contaRecorrente.isPresent()) {
            lancamento.setCategoria(contaRecorrente.get().getCategoria());
            responsavel = contaRecorrente.get().getResponsavel();
            observacao = contaRecorrente.get().getObservacao();
        }else{
            lembrete = lembreteService.procurarLembrete(lancamento.getDataCompra(), lancamento.getValor());
            if(lembrete.isPresent()){
                lancamento.setCategoria(lembrete.get().getCategoria());
                responsavel = lembrete.get().getResponsavel();
                observacao = lembrete.get().getObservacao();
            }
        }

        lancamento.setResponsavel(responsavel);
        lancamento.setObservacao(observacao);

        if(lancamento.getCategoria() == null)
            lancamento.setCategoria(lancamento.getQtdParcela() > 0 ? DominioCategoriaLancamento.COMPRA_PARCELADA : DominioCategoriaLancamento.COMPRA_PARCELA_UNICA);

        lancamento.setCartao(cartao);
        lancamento.setTipoLancamento(lancamentoDTO.getValor().contains("-") ? DominioTipoLancamento.CREDITO : DominioTipoLancamento.DEBITO);
        lancamento.setCdUsuAtu(USUARIO);

        log.debug(lancamento.toString());
        repository.save(lancamento);
    }

    public List<Lancamento> findBySituacaoLancamentoAndCartao(DominioSituacaoLancamento dominioSituacaoLancamento, DominioCartao dominioCartao) {
        return repository.findBySituacaoLancamentoAndCartao(dominioSituacaoLancamento, dominioCartao);
    }

    public Optional<Lancamento> findByHashAndSituacaoLancamento(DominioSituacaoLancamento dominioSituacaoLancamento, String hash) {
        return repository.findByHashAndSituacaoLancamento(hash, dominioSituacaoLancamento);
    }

    public boolean existsByHash(String hash) {
        return repository.existsByHash(hash);
    }

}
