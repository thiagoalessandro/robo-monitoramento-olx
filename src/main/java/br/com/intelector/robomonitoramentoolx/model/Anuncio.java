package br.com.intelector.robomonitoramentoolx.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "tbl_anuncio")
public class Anuncio extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mnt")
    private Monitoramento monitoramento;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "valor", length = 25)
    private String valor;

    @Column(name = "detalhe")
    private String detalhe;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "link")
    private String link;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_cadastro")
    private Date dhCadastro;



}
