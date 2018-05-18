package com.cenpro.siscu.utilitario;

import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.impl.BandBuilder;

public class ReporteUtilYarg
{
    private ReporteUtilYarg()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
    
    public static ReportBand buildReportBand(String name, String queryName, String script,
            String loaderType)
    {
        BandBuilder bandBuilder = new BandBuilder();
        return bandBuilder.name(name).query(queryName, script, loaderType).build();
    }

    public static ReportBand buildReportBand(String name)
    {
        BandBuilder bandBuilder = new BandBuilder();
        return bandBuilder.name(name).build();
    }
}