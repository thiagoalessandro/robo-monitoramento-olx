package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_lembrete")
public class Lembrete extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "observacao")
    private String observacao;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_compra")
    private Date dataCompra;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private DominioResponsavel responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private DominioCategoriaLancamento categoria;

}
