package com.cenpro.siscu.controller.consulta.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.consulta.ConsultaAtencionInicial;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAtencionInicial;
import com.cenpro.siscu.service.IConsultaAtencionInicialService;

@RequestMapping("/consulta/atencion")
public @RestController class ConsultaAtencionInicialRestController
{
    private @Autowired IConsultaAtencionInicialService consultaAtencionInicialService;

    @GetMapping(value = "/laboratorio", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialLaboratorioPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialLaboratorioPorCriterioBusqueda(criterioBusqueda);
    }

    @GetMapping(value = "/psicologia", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialPsicologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialPsicologiaPorCriterioBusqueda(criterioBusqueda);
    }

    @GetMapping(value = "/radiologia", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialRadiologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialRadiologiaPorCriterioBusqueda(criterioBusqueda);
    }

    @GetMapping(value = "/triaje", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialTriajePorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialTriajePorCriterioBusqueda(criterioBusqueda);
    }

    @GetMapping(value = "/odontologia", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialOdontologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialOdontologiaPorCriterioBusqueda(criterioBusqueda);
    }

    @GetMapping(value = "/medicinaGeneral", params = "accion=buscar")
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialMedicinaGeneralPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda)
    {
        return this.consultaAtencionInicialService
                .buscarConsultaAtencionInicialMedicinaGeneralPorCriterioBusqueda(criterioBusqueda);
    }
}