package com.cenpro.siscu.utilitario;

import com.google.common.collect.ImmutableMap;

public class ConstantesExcepciones
{
    /* Menzaje de Error de Carga de Archivo */
    public static final String ERROR_LECTURA_ARCHIVO = "Ocurrió un error en la lectura del archivo cargado. Reintente nuevamente, ingresando un archivo válido.";

    /* Mensajes de Error de Mantenimiento */
    public static final String ERROR_REGISTRO = "No se logró completar el registro. Reintente nuevamente.";
    public static final String ESPECIALIDAD_NOT_NULL = "La especialidad que desea registrar no tiene datos asociados.";
    public static final String ALUMNO_NO_ENCONTRADO = "El Código de Alumno <b>%s</b> y el Tipo de Alumno <b>%s</b> no fue encontrado.";

    /* Mensajes de Error de Mantenimiento Medico */
    public static final String NO_EXISTE_MEDICO_DIRECTOR = "No existe algún médico registrado como director. Registre alguno por favor.";

    /* Mensajes de Error de Parametro General */
    public static final String PARAMETRO_GENERAL_NO_ENCONTRADO = "Los Parámetros Generales <b>no existen</b>.";
    public static final String ANIO_INICIAL_NO_ENCONTRADO = "El Año Inicial no fue encontrado en Parámetros Generales.";
    public static final String ANIO_INICIAL_MAL_FORMATO = "El Año Inicial de Parámetros Generales debe ser un número.";
    public static final String ANIO_INICIAL_MAYOR_ANIO_ACTUAL = "El Año Inicial de Parámetros Generales no debe superar al Año Actual.";
    public static final String CORREO_SUM_NO_ENCONTRADO = "El Correo del SUM no fue encontrado en Párametros Generales.";
    public static final String CORREO_CLINICA_NO_ENCONTRADO = "El Correo de la Clínica Universitaria no fue encontrado en Párametros Generales.";

    /* Mensajes de Error de Parametro Correo */
    public static final String PARAMETRO_CORREO_NO_ENCONTRADO = "Los Parámetros de Correo <b>no existen</b>.";

    /* Mensajes de Error en Conexion */
    public static final String ERROR_CONEXION_BASE_DATOS = "No se logró establecer la conexión a la Base de Datos. Revise los parámetros de conexión.";
    public static final String ERROR_CONEXION_SERVIDOR_CORREOS = "No se logró establecer la conexión con el servidor de correos GMAIL. Revise la conexión a internet.";

    /* Codigos de Autenticacion */
    public static final String USUARIO_NO_ENCONTRADO = "El usuario %s no fue encontrado.";
    public static final String USUARIO_NO_ACTIVO = "El usuario %s no está activo.";
    public static final String CONTRASENIA_INCORRECTA = "La contraseña ingresada es incorrecta.";

    /* Mensajes de Error REST */
    public static final String ERROR_TYPE_MISMATCH_EXCEPTION_REST = "La petición enviada por el cliente contiene un formato inválido. Reintente la operación, por favor.";
    public static final String ERROR_MISSING_PARAMETER_EXCEPTION_REST = "La petición enviada por el cliente contiene datos incompletos. Reintente la operación, por favor.";

    /* Mensajes de Error Proceso Manual */
    public static final String DESTINO_ENVIO_DESCONOCIDO = "El Destino de Envío, asociado al Proceso Automático seleccionado, no fue identificado. Por favor, revise el Destino de Envío en <b>Mantenimiento de Proceso Automático</b>";
    public static final String HORA_PROGRAMADA_REPETIDO = "La Hora Programada <b>%s</b> ya esta asociado a otro Proceso Automático.";
    public static final String ORDEN_EJEC_REPETIDO = "El Orden de Ejecución <b>%s</b> ya esta asociado a otro Proceso Automático.";
    public static final String PROC_AUTOMATICO_NO_ENCONTRADO = "El Proceso Automático con el código <b>%s</b>, no fue encontrado.";
    public static final String NINGUN_CORREO_POR_ENVIAR = "No se encontraron resultados de alumnos para enviar por correo. Pruebe seleccionando diferentes facultades u otro rango de fecha.";

    /* Otros Mensajes de Error */
    public static final String VIOLACION_CLAVE_FORANEA = "VIOLACION_CLAVE_FORANEA";
    public static final String CAMPO_NO_DEFINIDO = "campoNoDefinido";
    public static final String ERROR_BASE_DATOS = "ERROR_BASE_DATOS";
    public static final String ERROR_DESCONOCIDO = "Ocurrió un error no identificado.";

    /* Error Generacion Reporte Examen Medico */
    public static final String NO_EXISTE_REPORTE_EXAMEN_MEDICO_REGULAR = "El Alumno <b>no cuenta con todos los resultados completos</b> de Examen Médico Regular.";

    /* Error constructor estático */
    public static final String NO_INSTANCIAR_CLASE_ESTATICA = "No puede instanciar una clase estática.";

    public static final ImmutableMap<Integer, String> EXCEPCIONES_MYSQL = ImmutableMap.of(547,
            VIOLACION_CLAVE_FORANEA);

    private ConstantesExcepciones()
    {
        throw new UnsupportedOperationException(NO_INSTANCIAR_CLASE_ESTATICA);
    }
}