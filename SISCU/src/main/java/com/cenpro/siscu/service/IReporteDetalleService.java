package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteDetalleResultadoAlumno;
import com.cenpro.siscu.model.reporte.ReporteDetalleResultadoAlumnoRegular;

public interface IReporteDetalleService
{
    public List<ReporteDetalleResultadoAlumnoRegular> buscarResultadoExamenMedicoRegular(
            CriterioBusquedaReporteDetalleResultadoAlumno criterioBusquedaReporteDetalleResultadoAlumno);
}