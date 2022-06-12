package com.marreiros.estacionamentos.exception;

public class ExistingVeiculePlacaException extends RuntimeException {

    public ExistingVeiculePlacaException(){ super("Veiculo ja cadastrado");}

}
