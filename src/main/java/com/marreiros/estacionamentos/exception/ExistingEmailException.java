package com.marreiros.estacionamentos.exception;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException(){ super("Email já cadastrado");}
}
