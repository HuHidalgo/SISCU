package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteAtencionDiaria;
import com.cenpro.siscu.model.reporte.ReporteAtencionDiaria;

public interface IReporteAtencionDiariaService
{
    public List<ReporteAtencionDiaria> buscarAtencionesDiarias(
            CriterioBusquedaReporteAtencionDiaria criterioBusquedaReporteAtencionDiaria);
}