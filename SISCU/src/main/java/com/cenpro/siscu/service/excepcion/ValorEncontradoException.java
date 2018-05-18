package com.cenpro.siscu.service.excepcion;

public class ValorEncontradoException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public ValorEncontradoException(String mensaje)
    {
        super(mensaje);
    }
}