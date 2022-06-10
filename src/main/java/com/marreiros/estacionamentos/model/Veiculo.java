package com.marreiros.estacionamentos.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "modelo")
    private String modelo;
}
