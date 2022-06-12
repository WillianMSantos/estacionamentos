package com.marreiros.estacionamentos.dto.request;

import lombok.Data;

@Data
public class VeiculoRequestDto {

    private String placa;
    private String modelo;

    public boolean isNullOrEmpty() {
        return this.getPlaca() == null || this.getModelo() == null  ||
                this.getPlaca().isEmpty() || this.getModelo().isEmpty();
    }
}
