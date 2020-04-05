package br.com.intelector.gerenciarcontapagar.model;

import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_arquivo")
public class Arquivo extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_fechamento")
    private Date dataFechamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "cartao")
    private DominioCartao cartao;

}
