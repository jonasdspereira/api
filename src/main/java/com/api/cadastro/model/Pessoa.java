package com.api.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    private LocalDate dataNascimento;
    @Column( unique = true)
    private String cpf;
    private int idade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pessoa_id")
    private List<Endereco> enderecos;

    public int calcularIdade() {
        if (this.dataNascimento != null) {
            LocalDate hoje = LocalDate.now();
            return Period.between(this.dataNascimento, hoje).getYears();
        } else {
            return 0;
        }
    }
}

