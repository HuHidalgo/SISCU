package com.cenpro.siscu.controller.consulta.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaRadiologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoLaboratorio;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoRadiologico;
import com.cenpro.siscu.model.movimiento.ExamenMedicoRadiologico;
import com.cenpro.siscu.service.IConsultaExamenMedicoRadiologicoService;
import com.cenpro.siscu.service.IExamenMedicoRadiologicoService;

@RequestMapping("/consulta/radiologico")
public @RestController class ConsultaExamenMedicoRadiologicoRestController
{
    private @Autowired IExamenMedicoRadiologicoService examenMedicoRadiologicoService;
    private @Autowired IConsultaExamenMedicoRadiologicoService consultaExamenMedicoRadiologicoService;

    @GetMapping(value = "/administrativa", params = "accion=buscarPorCriterioBusquedaAdministrativa")
    public List<ConsultaRadiologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoRadiologico criterioBusquedaAdministrativaExamenMedicoRadiologico)
    {
        return consultaExamenMedicoRadiologicoService.buscarPorCriterioBusquedaAdministrativa(
                criterioBusquedaAdministrativaExamenMedicoRadiologico);
    }

    @GetMapping(params = "accion=buscarResultadoRegularPorNumeroRegistroAnio")
    public List<ExamenMedicoRadiologico> buscarResultadoRegularPorNumeroRegistroAnio(
            CriterioBusquedaAdministrativaExamenMedicoLaboratorio criterioBusqueda)
    {
        return examenMedicoRadiologicoService.buscarResultadoRegularPorNumeroRegistroAnio(
                criterioBusqueda.getNumeroRegistro(), criterioBusqueda.getAnio());
    }
}