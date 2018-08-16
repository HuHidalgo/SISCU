package com.cenpro.siscu.utilitario;

public class Verbo
{
    // Verbo de Mantenimiento
    public static final String GET = "GET";
    public static final String GET_ITEM = "GET_ITEM";
    public static final String GET_FECHA = "GET_FECHA";
    public static final String GET_PASS = "GET_PASS";
    public static final String GET_USUARIO_S = "GET_USUARIO_S";
    public static final String GET_TIPO_AUDITORIA = "GET_TIPO_AUDITORIA";
    public static final String GETS = "GETS";
    public static final String GET_DATE = "GET_DATE";
    public static final String GET_SIMP_BUS = "SIMP_BUS";
    public static final String GET_EMP = "GET_EMP";
    public static final String GET_C = "GET_C";
    public static final String GETS_T_MEM = "GETS_T_MEM";
    public static final String GETS_T = "GETS_T";
    public static final String GETS_BIN = "GETS_BIN";
    public static final String GET_T_SBC = "GET_T_SBC";
    public static final String GETS_SBC = "GETS_SBC";
    public static final String GET_SBC = "GET_SBC";
    public static final String GETS_IMS = "GETS_IMS";
    public static final String GET_INST = "GET_INST";
    public static final String GET_PACIENTE = "GET_PACIENTE";
    public static final String INSERT = "INSERT";
    public static final String INSERT_SBC = "INSERT_SBC";
    public static final String INSERT_COMISION = "INSERT_COMISION";
    public static final String INSERT_FONDO = "INSERT_FONDO";
    public static final String REMOVE_SBC = "REMOVE_SBC";
    public static final String UPDATE = "UPDATE";
    public static final String REMOVE = "REMOVE";
    public static final String EXIST = "EXIST";

    // Verbo de Reporte
    public static final String COMISION_BANCO_ADMINISTRADOR = "COMISION_BANCO_ADMINISTRADOR";
    public static final String AUTORIZACION_POR_MES = "MES";
    public static final String AUTORIZACION_POR_DIA = "DIA";

    public static final String EMISOR_RESUMEN = "EMISOR_RESUMEN";
    public static final String EMISOR_DETALLE = "EMISOR_DETALLE";
    public static final String ADQUIRENTE_RESUMEN = "ADQUIRENTE_RESUMEN";
    public static final String ADQUIRENTE_DETALLE = "ADQUIRENTE_DETALLE";
    public static final String SURCHARGE_DETALLE = "SURCHARGE_DETALLE";
    
    private Verbo()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
}