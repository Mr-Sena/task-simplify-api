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
    @Column(name = "done", nullable = false)
    private boolean realizado;

    @NotNull
    @Column(name = "priority", nullable = false)
    private int prioridade;

}
