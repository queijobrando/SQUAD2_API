package com.example.squad2_suporte.lote;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.StatusLote;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long protocolo;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<Amostra> amostras = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLote statusLote;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void gerarProtocolo() {
        //gerar número com base no timestamp
        this.protocolo = System.currentTimeMillis();
        this.statusLote = StatusLote.PENDENTE;
        this.dataCriacao = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
