package com.example.squad2_suporte.enuns.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "opcoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Opcoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String chave;
    private String valor;

}
