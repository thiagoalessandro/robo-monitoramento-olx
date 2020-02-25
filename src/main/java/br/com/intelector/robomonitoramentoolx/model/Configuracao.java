package br.com.intelector.robomonitoramentoolx.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_configuracao")
public class Configuracao extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 25)
    private String nome;

    @Column(name = "valor")
    private String valor;

}
