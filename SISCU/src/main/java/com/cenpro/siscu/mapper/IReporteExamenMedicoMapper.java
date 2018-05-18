package com.cenpro.siscu.mapper;

import java.util.List;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteExamenMedico;
import com.cenpro.siscu.model.reporte.ReporteExamenMedicoRegular;

public interface IReporteExamenMedicoMapper
{
    public List<ReporteExamenMedicoRegular> buscarReporteExamenMedicoRegular(
            CriterioBusquedaReporteExamenMedico criterioBusquedaReporteExamenMedico);
}