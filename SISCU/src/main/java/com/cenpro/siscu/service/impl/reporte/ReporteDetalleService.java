package com.cenpro.siscu.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IReporteDetalleMapper;
import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteDetalleResultadoAlumno;
import com.cenpro.siscu.model.reporte.ReporteDetalleResultadoAlumnoRegular;
import com.cenpro.siscu.service.IReporteDetalleService;

@Service
public class ReporteDetalleService implements IReporteDetalleService
{
    private @Autowired IReporteDetalleMapper reporteDetalleMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteDetalleResultadoAlumnoRegular> buscarResultadoExamenMedicoRegular(
            CriterioBusquedaReporteDetalleResultadoAlumno criterioBusquedaReporteDetalleResultadoAlumno)
    {
        return reporteDetalleMapper
                .buscarResultadoExamenMedicoRegular(criterioBusquedaReporteDetalleResultadoAlumno);
    }
}