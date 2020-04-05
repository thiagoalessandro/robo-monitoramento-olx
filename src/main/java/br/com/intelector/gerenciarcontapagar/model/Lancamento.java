package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "tbl_lancamento")
public class Lancamento extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_compra")
    private Date dataCompra;

    @Column(name = "hash")
    private String hash;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "qtd_parcela")
    private Integer qtdParcela;

    @Column(name = "qtd_parcela_restante")
    private Integer qtdParcelaRestante;

    @Column(name = "qtd_parcela_atual")
    private Integer qtdParcelaAtual;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "sit_lancamento")
    private DominioSituacaoLancamento situacaoLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private DominioTipoLancamento tipoLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private DominioResponsavel responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "cartao")
    private DominioCartao cartao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private DominioCategoriaLancamento categoria;

}
