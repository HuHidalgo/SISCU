package com.cenpro.siscu.aspecto.enumeracion;

public enum Accion
{
    Registro("Reg"),
    Visita("Vis"),
    Actualizacion("Act"), 
    Eliminacion("Eli"),
    Consulta("Con"),
    Reporte("Rep"),
    Acceso("Acc"),
    Ninguna("");
    
    private final String nombre;       

    private Accion(String s) {
        nombre = s;
    }

    @Override
    public String toString() {
       return this.nombre;
    }   
}