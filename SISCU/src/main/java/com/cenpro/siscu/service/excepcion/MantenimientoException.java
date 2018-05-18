package com.cenpro.siscu.service.excepcion;

public class MantenimientoException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public MantenimientoException(String mensaje)
    {
        super(mensaje);
    }
}