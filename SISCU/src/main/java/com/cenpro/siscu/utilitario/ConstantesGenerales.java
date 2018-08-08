package com.cenpro.siscu.utilitario;

public class ConstantesGenerales
{
    public static final String REGISTRO_EXITOSO = "Se registró correctamente";
    public static final String ACTUALIZACION_EXITOSA = "Se actualizó correctamente";
    public static final String ELIMINACION_EXITOSA = "Se eliminó correctamente";
    public static final String CARGA_EXITOSA = "Se cargó correctamente";

    /* ESTADO_EXAMEN_MEDICO */
    public static final String ESTADO_NUMERO_REGISTRO_GENERADO = "N";
    public static final String ESTADO_CONCLUIDO = "C";
    public static final String ESTADO_TOMA_RADIOLOGICA = "T";
    public static final String ESTADO_INCOMPLETO = "I";

    /* RPR */
    public static final String RPR_REACTIVO = "R";
    public static final String RPR_NO_REACTIVO = "N";

    /* TIPO REPORTE */
    public static final String REPORTE_GENERAL = "GENERAL";
    public static final String REPORTE_DETALLE = "DETALLE";

    /* DESTINO ENVIO */
    public static final String SUM = "SUM";
    public static final String FAC = "FAC";
    public static final String ASREG = "ASREG";

    /* REPORTE YARG */
    public static final String RUTA_REPORTE_XLSX = "xlsx/";
    public static final String PREFIX_REPORT_BAND = "rb_";

    /* REPORTE DOCX */
    public static final String RUTA_REPORTE_DOCX = "docx/";

    /* PARAM REPORT */
    public static final String PARAM_NOMBRE_REPORTE = "nombreReporte";
    public static final String PARAM_TEMPLATE = "template";
    public static final String PARAM_REPORTE_PARAMETERS = "reporteParametros";

    public static final int ESTADO_EXITO = 1;
    public static final int ESTADO_ERROR = 0;
    public static final String UTF_8 = "UTF-8";

    /* PARAM MODEL */
    public static final String P_TIPOS_ALUMNO = "tiposAlumno";
    public static final String P_ESTADOS_EXAMEN_MEDICO = "estadosExamenMedico";
    public static final String P_CAMPANIAS = "campanias";
    public static final String P_FACULTADES = "facultades";
    public static final String P_CONSULTA = "consulta";
    public static final String P_MANTENIMIENTO = "mantenimiento";
    public static final String P_REGISTRO_EN_ATENCION = "esRegistroEnAtencion";

    /* RUTA A PAGINA HTML */
    public static final String PAGINA_MANTENIMIENTO = "seguras/mantenimiento/mantenimiento";
    public static final String PAGINA_CONSULTA_ADMINISTRATIVA_EXAMEN_MEDICO = "seguras/consulta/consultaAdministrativaExamenMedico";
    public static final String PAGINA_CONSULTA_ATENCION_INICIAL = "seguras/consulta/consultaAtencionInicial";
    public static final String PAGINA_CONSULTA_ADMISION = "seguras/admision/consultaAdmision";
    public static final String PAGINA_DIGITADOR_REGISTRO_RESULTADO = "seguras/digitador/registroResultado";
    
    private ConstantesGenerales()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
}