package com.webatrio.test.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String titre;
    @NotBlank
    @Size(max = 2000)
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    @Size(max = 2000)
    @NotBlank
    private String lieu;
    private Long capacite;
    private Boolean annuler;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> listeUser;

}
