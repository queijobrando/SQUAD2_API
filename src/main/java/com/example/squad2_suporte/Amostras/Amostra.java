package com.example.squad2_suporte.Amostras;

import com.example.squad2_suporte.Amostras.endereco.Endereco;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.lote.Lote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "amostra")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoAmostra"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Escorpioes.class, name = "ESCORPIAO"),
        @JsonSubTypes.Type(value = Flebotomineos.class, name = "FLEBOTOMINEO"),
        @JsonSubTypes.Type(value = Triatomineos.class, name = "TRIATOMINEO"),
        @JsonSubTypes.Type(value = Molusco.class, name = "MOLUSCO"),
        @JsonSubTypes.Type(value = Larvas.class, name = "LARVA")
})
public abstract class Amostra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(unique = true)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmostra status;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private TipoAmostra tipoAmostra;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    @JsonIgnore
    private Lote lote;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] laudo;

    @Column
    private String baseLegal; // Novo campo para LGPD
}