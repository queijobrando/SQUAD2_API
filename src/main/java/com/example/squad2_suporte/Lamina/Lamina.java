package com.example.squad2_suporte.Lamina;

import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.lote.Lote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lamina")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lamina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(unique = true)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmostra status;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    @JsonIgnore
    private Lote lote;

    @Column
    private Integer numeroOvos;

    @Column
    private String resultado;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] laudo;

    @Column
    private String enderecoCaptura; // Novo campo

    @Column
    private String baseLegal; // Campo para LGPD, adicionado anteriormente
}