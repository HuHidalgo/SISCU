package com.cenpro.siscu.utilitario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.cenpro.siscu.service.excepcion.FormatoException;

public class DatesUtils
{
    public static final String FORMATO_YYYYMMDD = "YYYY-MM-DD";
    public static final String FORMATO_HHMMSS = "HH:mm:ss";

    private DatesUtils()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static Date obtenerFechaDeMilisegundos(long tiempoMilisegundos)
    {
        DateTime fecha = new DateTime(tiempoMilisegundos);
        return fecha.toDate();
    }

    public static String obtenerFechaEnFormato(Date date, String formato)
    {
        DateTime fecha = new DateTime(date);
        return DateTimeFormat.forPattern(formato).print(fecha);
    }

    public static String obtenerFechaDeMilisegundosEnFormato(long tiempoMilisegundos,
            String formato)
    {
        DateTime fecha = new DateTime(tiempoMilisegundos);
        return fecha.toString(formato);
    }

    public static String obtenerDuracionDesdeMilisegundos(long tiempoMilisegundos)
    {
        Period periodo = new Duration(tiempoMilisegundos).toPeriod();
        PeriodFormatter hm = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2)
                .appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
                .appendSeconds().toFormatter();
        return hm.print(periodo);
    }

    public static List<Integer> obtenerAnios(Integer anioInicio)
    {
        LocalDateTime fechaActual = LocalDateTime.now();
        Integer anioActual = fechaActual.getYear();
        if (anioInicio > anioActual)
        {
            throw new FormatoException(ConstantesExcepciones.ANIO_INICIAL_MAYOR_ANIO_ACTUAL);
        }
        List<Integer> anios = new ArrayList<>();
        for (int i = anioActual; i >= anioInicio; i--)
        {
            anios.add(i);
        }
        return anios;
    }
}