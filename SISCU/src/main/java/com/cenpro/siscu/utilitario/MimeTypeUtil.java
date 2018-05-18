package com.cenpro.siscu.utilitario;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class MimeTypeUtil
{
    public static final String PDF = "application/pdf";
    public static final String PNG = "image/png";
    public static final String JPEG = "image/jpeg";
    public static final String TEXT = "text/plain";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPEG = ".jpeg";
    public static final String EXTENSION_XLSX = ".xlsx";
    public static final String EXTENSION_DOCX = ".docx";
    public static final Map<String, String> EXTENSIONES = ImmutableMap.of(PDF, EXTENSION_PDF, PNG,
            EXTENSION_PNG, JPEG, EXTENSION_JPEG, XLSX, EXTENSION_XLSX, DOCX, EXTENSION_DOCX);
    
    private MimeTypeUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static String obtenerExtensionPorMymetype(String mimeType)
    {
        return EXTENSIONES.get(mimeType);
    }
}