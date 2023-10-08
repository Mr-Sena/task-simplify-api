package br.com.programme.sistematarefassimplify.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String nome;

    @NotEmpty
    @Column(nullable = false, name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "done")
    private boolean realizado;

    @NotNull
    @Column(name = "priority")
    private int prioridade;

    public Tarefa(String nome, String descricao, boolean realizado, int prioridade) {
        this.nome = nome;
        this.descricao = descricao;
        this.realizado = realizado;
        this.prioridade = prioridade;
    }

    public Tarefa() {
    }
}
