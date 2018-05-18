package com.cenpro.siscu.service.excepcion;

public class EjecucionManualException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public EjecucionManualException(String mensaje)
    {
        super(mensaje);
    }
}