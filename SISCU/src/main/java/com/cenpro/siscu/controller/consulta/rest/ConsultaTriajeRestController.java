package com.cenpro.siscu.controller.consulta.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaTriajeAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaTriaje;
import com.cenpro.siscu.service.IConsultaTriajeService;

@RequestMapping("/consulta/triaje")
public @RestController class ConsultaTriajeRestController
{
    private @Autowired IConsultaTriajeService consultaTriajeService;

    @GetMapping(value = "/administrativa", params = "accion=buscarPorCriterioBusquedaAdministrativa")
    public List<ConsultaTriajeAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaTriaje criterioBusquedaAdministrativaTriaje)
    {
        return consultaTriajeService.buscarPorCriterioBusquedaAdministrativa(criterioBusquedaAdministrativaTriaje);
    }

}