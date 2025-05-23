package com.example.squad2_suporte.Laudos;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "laudos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
public class Laudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nome;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] dados;


}
