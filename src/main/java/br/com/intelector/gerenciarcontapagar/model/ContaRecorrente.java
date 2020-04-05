package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tbl_conta_recorrente")
public class ContaRecorrente extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsavel")
    private DominioResponsavel responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private DominioCategoriaLancamento categoria;

}
