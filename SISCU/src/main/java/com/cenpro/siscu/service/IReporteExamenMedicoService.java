package com.cenpro.siscu.service;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteExamenMedico;
import com.cenpro.siscu.model.reporte.ReporteExamenMedicoRegular;

public interface IReporteExamenMedicoService
{
    public ReporteExamenMedicoRegular buscarReporteExamenMedicoRegular(
            CriterioBusquedaReporteExamenMedico criterioBusquedaReporteExamenMedico);
}