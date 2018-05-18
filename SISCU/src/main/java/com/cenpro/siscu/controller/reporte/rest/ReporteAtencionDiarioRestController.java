package com.cenpro.siscu.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteAtencionDiaria;
import com.cenpro.siscu.model.reporte.ReporteAtencionDiaria;
import com.cenpro.siscu.service.IReporteAtencionDiariaService;

@RequestMapping("/reporte/atencion/diaria")
public @RestController class ReporteAtencionDiarioRestController
{
    private @Autowired IReporteAtencionDiariaService reporteAtencionDiariaService;

    @GetMapping(params = "accion=buscar")
    public List<ReporteAtencionDiaria> buscarAtencionesDiarias(
            CriterioBusquedaReporteAtencionDiaria criterioBusquedaReporteAtencionDiaria)
    {
        return reporteAtencionDiariaService.buscarAtencionesDiarias(criterioBusquedaReporteAtencionDiaria);
    }
}