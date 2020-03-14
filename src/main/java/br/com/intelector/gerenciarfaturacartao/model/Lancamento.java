package br.com.intelector.gerenciarfaturacartao.model;

import br.com.intelector.gerenciarfaturacartao.domain.SituacaoLancamento;
import br.com.intelector.gerenciarfaturacartao.domain.TagResponsavel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
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

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "qtd_parcela")
    private Integer qtdParcela;

    @Column(name = "qtd_parcela_restante")
    private Integer qtdParcelaRestante;

    @Column(name = "qtd_parcela_paga")
    private Integer qtdParcelaPaga;

    @Enumerated(EnumType.STRING)
    @Column(name = "sit_lancamento")
    private SituacaoLancamento situacaoLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_responsavel")
    private TagResponsavel tag;

}
