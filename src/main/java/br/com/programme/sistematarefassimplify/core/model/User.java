package br.com.programme.sistematarefassimplify.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String username;

    private String password;

    private String name;

    private LocalDateTime createdAt;  // -->  yyyy-MM-ddTHH:mm:ss
    //2023-10-15T17:57:00

}
