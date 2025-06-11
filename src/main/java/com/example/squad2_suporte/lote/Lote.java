package com.example.squad2_suporte.lote;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.enuns.TipoLote;
import com.example.squad2_suporte.enuns.StatusLote;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLote tipo; // Referencia o enum separado

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<Amostra> amostras = new ArrayList<>();

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    private List<Lamina> laminas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLote statusLote;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

}