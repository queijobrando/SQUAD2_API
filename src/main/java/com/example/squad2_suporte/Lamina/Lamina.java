package com.example.squad2_suporte.Lamina;

import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.lote.Lote;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(unique = true)
    private Long protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmostra status;

    @ManyToOne
    @JoinColumn(name = "lote_id") // Amostra pode ou não ter um lote
    @JsonIgnore
    private Lote lote;

    @Column(nullable = false)
    private Integer numeroOvos;

    private String resultado;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] laudo;

    @PrePersist
    public void geracaoAutomatica() {
        this.data = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS); // define a data/hora/segundos que for cadastrada
        this.protocolo = System.currentTimeMillis();
        this.status = StatusAmostra.PENDENTE;
    }


}
