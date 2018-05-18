package com.cenpro.siscu.aspecto.enumeracion;

public enum Tipo
{
    /*Mantenimiento*/
    Alumno("Alumno"),
    Medico("Médico"),
    MulTabCab("Tabla de Tablas"),
    MulTabDet("Detalle de Tabla de Tablas"),
    Persona("Persona"),
    Campania("Campaña"),
    
    /*Reporte*/
    RptAutCanal("Reporte de Autorización de Canal"),
    RptMovAut("Reporte de Movimiento de Autorización"),
    RptMovSwDmpLog("Reporte de Movimiento de SwDmpLog"),
    RptMovTransAg("Reporte de Movimiento de Transacción aprobada por agencia"),
    RptMovLgCntEmi("Reporte de Movimiento de Log Contable Emisor"),
    RptMovLgCntRec("Reporte de Movimiento de Log Contable Receptor"),
    
    /*Consultas*/
    
    /*Login*/
    Login("Login"),
    Logout("Logout"),
    
    /*Seguridad*/
    Rec("Recurso"),
    Usu("Usuario"),
    CatRec("Categoría de Recurso"),
    
    Ninguno("");

    private final String nombre;

    private Tipo(String nombre)
    {
        this.nombre = nombre;
    }

    public String toString()
    {
        return this.nombre;
    }
}