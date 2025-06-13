package com.example.squad2_suporte.Usuario;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String action;

    @Column
    private String resourceType; // Novo campo para identificar o tipo de recurso (ex.: "LOTE")

    @Column
    private String resourceId;

    @Column
    private String details;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}