package com.marreiros.estacionamentos.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultMessageResponseDto {
    private String message;
    private int status;

    public DefaultMessageResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }

}
