package br.com.intelector.gerenciarfaturacartao.model;

import br.com.intelector.gerenciarfaturacartao.domain.TagResponsavel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_assinatura")
public class Assinatura extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_responsavel")
    private TagResponsavel tag;

}
