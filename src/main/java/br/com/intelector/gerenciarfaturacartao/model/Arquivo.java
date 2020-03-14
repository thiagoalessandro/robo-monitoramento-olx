package br.com.intelector.gerenciarfaturacartao.model;

import br.com.intelector.gerenciarfaturacartao.domain.TagArquivo;
import lombok.Data;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "tag")
    private TagArquivo tag;

}
