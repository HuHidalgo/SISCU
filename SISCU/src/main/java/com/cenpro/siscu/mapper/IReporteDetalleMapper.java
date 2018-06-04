package com.cenpro.siscu.mapper;

import java.util.List;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteDetalleResultadoAlumno;
import com.cenpro.siscu.model.reporte.ReporteDetalleResultadoAlumnoRegular;

public interface IReporteDetalleMapper
{
    public List<ReporteDetalleResultadoAlumnoRegular> buscarResultadoExamenMedicoRegular(
            CriterioBusquedaReporteDetalleResultadoAlumno criterioBusquedaReporteDetalleResultadoAlumno);
}