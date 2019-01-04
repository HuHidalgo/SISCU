package com.cenpro.siscu.aspecto.enumeracion;

public enum Dato
{
    /*Mantenimientos*/
    Alumno("Código_de_Alumno = #alumno.codigoAlumno, Tipo_de_Alumno = #alumno.tipoAlumno, Tipo_de_Documento = #alumno.idTipoDocumento, "
    		+ "Número_de_Documento = #alumno.numeroDocumento"),
    Afiliacion("Tipo_de_Documento = #afiliacion.idTipoDocumento, Número_de_Documento = #afiliacion.numeroDocumento"),
    Persona("Tipo_de_Documento = #persona.idTipoDocumento, Número_de_Documento = #persona.numeroDocumento, Apellido_Paterno = #persona.apellidoPaterno"),
    Medico("Tipo_de_Documento = #medico.idTipoDocumento, Número_de_Documento = #medico.numeroDocumento"),
    MultiTabCab("Id_de_Tabla = #multiTabCab.idTabla, Descripción = #multiTabCab.descripcion"),
    MultiTabDet("Id_Detalle = #multiTabDet.idItem,Id_Tabla = #multiTabDet.idTabla, Descripción = #multiTabDet.descripcionItem"),
    
    /*Seguridad*/
    Usuario("Codigo_de_Usuario = #usuario.idUsuario"),
    
    Ninguno("");

	private final String nombre;

    private Dato(String nombre)
    {
        this.nombre = nombre;
    }

    public String toString()
    {
        return this.nombre;
    }
}