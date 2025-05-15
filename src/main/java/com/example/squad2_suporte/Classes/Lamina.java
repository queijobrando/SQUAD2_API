package com.example.squad2_suporte.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "Lamina")
@Getter
@Setter

public class Lamina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lamina")
    private Integer idLamina;

    @Column(name = "num_ovos")
    private Integer numOvos;

    @Column(name = "id_usuario_lamina")
    private String idUsuarioLamina;

    public Lamina() {
}

    public Lamina(Integer idLamina, Integer numOvos, String idUsuarioLamina) {
        this.idLamina = idLamina;
        this.numOvos = numOvos;
        this.idUsuarioLamina = idUsuarioLamina;
    }

    @Override
    public String toString() {
        return "Lamina{" +
                "idLamina=" + idLamina +
                ", numOvos='" + numOvos + '\'' +
                ", idUsuarioLamina=" + idUsuarioLamina + '\'' +
                '}';
    }
}