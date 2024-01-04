package br.com.programme.sistematarefassimplify.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private LocalDateTime createdAt;  // -->  yyyy-MM-ddTHH:mm:ss
    //2023-10-15T17:57:00

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;

        this.createdAt = LocalDateTime.now();
    }

}
