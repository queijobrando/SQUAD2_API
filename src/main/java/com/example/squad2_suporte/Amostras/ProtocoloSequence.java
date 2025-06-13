package com.example.squad2_suporte.Amostras;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "protocolo_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocoloSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String sequenceKey;

    @Column(nullable = false)
    private Long sequence;
}