package com.example.squad2_suporte.Lamina;

import com.example.squad2_suporte.Classes.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "lamina")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Lamina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private Integer numeroOvos;

    private String resultado;

    @PrePersist
    public void definirDataHoraCriacao() {
        this.data = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS); // define a data/hora/segundos que for cadastrada
    }

}
