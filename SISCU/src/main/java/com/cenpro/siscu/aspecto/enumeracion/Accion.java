package com.cenpro.siscu.aspecto.enumeracion;

public enum Accion
{
    Registro("I"),
    Visita("V"),
    Actualizacion("U"), 
    Eliminacion("D"),
    Consulta("S"),
    Reporte("R"),
    Acceso("A"),
    Ninguna("");
    
    private final String nombre;       

    private Accion(String s) {
        nombre = s;
    }

    public String toString() {
       return this.nombre;
    }   
}