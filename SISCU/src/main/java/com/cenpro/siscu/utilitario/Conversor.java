package com.cenpro.siscu.utilitario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Conversor
{
    private Conversor()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static Integer integerValueOf(Object o)
    {
        return Integer.valueOf(String.valueOf(o));
    }

    public static String stringValueOf(Object o)
    {
        return String.valueOf(o);
    }

    public static Date dateValueOf(String date) throws ParseException
    {
        return (date == null || date.isEmpty()) ? new Date()
                : new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public static Double doubleValueOf(Object o)
    {
        return Double.valueOf(String.valueOf(o));
    }

    public static Short shortValueOf(Object o)
    {
        return Short.valueOf(String.valueOf(o));
    }

    public static Byte byteValueOf(Object o)
    {
        return Byte.valueOf(String.valueOf(o));
    }

    public static Boolean booleanValueOf(Object o)
    {
        return Boolean.parseBoolean(String.valueOf(o));
    }

    public static Map<String, Object> toMapJSON(Map<String, Object> mapOriginal,
            Map<String, Object> mapJSON) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map.Entry<String, Object> entry : mapOriginal.entrySet())
        {
            mapJSON.put(entry.getKey(), objectMapper.writeValueAsString(entry.getValue()));
        }
        return mapJSON;
    }
}