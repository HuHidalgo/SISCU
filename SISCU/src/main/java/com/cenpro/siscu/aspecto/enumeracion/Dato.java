package com.cenpro.siscu.aspecto.enumeracion;

public enum Dato
{
    /*Mantenimientos*/
    Alumno("Código_de_Alumno = #alumno.codigoAlumno, Tipo_de_Alumno = #alumno.tipoAlumno, Tipo_de_Documento = #alumno.idTipoDocumento, Número_de_Documento = #alumno.numeroDocumento"),
    Persona("Tipo_de_Documento = #persona.idTipoDocumento, Número_de_Documento = #persona.numeroDocumento, Apellido_Paterno = #persona.apellidoPaterno"),
    Medico("Tipo_de_Documento = #medico.idTipoDocumento, Número_de_Documento = #medico.numeroDocumento"),
    Campania("Descripción = #campania.descripcion, Tipo_Certificado = #campania.idTipoCertificado, Activo = #campania.activo"),
    MultiTabCab("Id_de_Tabla = #multiTabCab.idTabla, Descripción = #multiTabCab.descripcion"),
    MultiTabDet("Id_Detalle = #multiTabDet.idItem,Id_Tabla = #multiTabDet.idTabla, Descripción = #multiTabDet.descripcionItem"),
    ParametroGeneral("Fecha_Proceso = #parametroGeneral.fechaProceso, Plantillas = #parametroGeneral.plantillas, Config = #parametroGeneral.config, Bin_de_Ruteo = #parametroGeneral.binRuteoSwitch, Institución = #parametroGeneral.codigoInstitucion, Repo_Prepago = #parametroGeneral.repositorioPrepago, Repo_Compensación = #parametroGeneral.repositorioCompensacion, Surcharge_Soles = #parametroGeneral.surchargeSoles, Surcharge_Dolares = #parametroGeneral.surchargeDolares,Empresa = #parametroGeneral.idEmpresa, Ruta_SimpBus = #parametroGeneral.rutaContextoSimpBus, Enmascarar_Tarjeta = #parametroGeneral.enmascararTarjeta, IGV = #parametroGeneral.porcentajeIgv"),
    
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
