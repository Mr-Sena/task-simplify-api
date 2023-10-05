package br.com.programme.sistematarefassimplify.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")

public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTable;

    @Column(name = "name", nullable = false)
//    @NotNull --> Maybe available on spring validation package.
    private String nome;

    @Column(nullable = false, name = "descricao")
    private String descricao;

    @Column(name = "done", nullable = false)
    private boolean realizado;

    @Column(name = "priority", nullable = false)
    private int prioridade;

}
