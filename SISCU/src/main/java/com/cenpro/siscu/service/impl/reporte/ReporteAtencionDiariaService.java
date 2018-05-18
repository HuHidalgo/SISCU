package com.cenpro.siscu.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IReporteAtencionDiariaMapper;
import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteAtencionDiaria;
import com.cenpro.siscu.model.reporte.ReporteAtencionDiaria;
import com.cenpro.siscu.service.IReporteAtencionDiariaService;
import com.cenpro.siscu.utilitario.StringsUtils;

@Service
public class ReporteAtencionDiariaService implements IReporteAtencionDiariaService
{
    private @Autowired IReporteAtencionDiariaMapper reporteAtencionDiariaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteAtencionDiaria> buscarAtencionesDiarias(
            CriterioBusquedaReporteAtencionDiaria criterioBusquedaReporteAtencionDiaria)
    {
        String verbo = StringsUtils.concatenarCadena(
                criterioBusquedaReporteAtencionDiaria.getIdTipoExamenMedico(), "_",
                criterioBusquedaReporteAtencionDiaria.getTipoReporte());
        criterioBusquedaReporteAtencionDiaria.setVerbo(verbo);
        return reporteAtencionDiariaMapper.buscarAtencionesDiarias(criterioBusquedaReporteAtencionDiaria);
    }
}
