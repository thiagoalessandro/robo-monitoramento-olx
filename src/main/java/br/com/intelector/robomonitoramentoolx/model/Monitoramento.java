package br.com.intelector.robomonitoramentoolx.model;

import br.com.intelector.robomonitoramentoolx.domain.DominioBot;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@Table(name = "tbl_monitoramento")
public class Monitoramento extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bot")
    private DominioBot bot;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "link")
    private String link;

    @Column(name = "primeira_exec")
    private String primeiraExecucao;

}
