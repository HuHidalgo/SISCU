package com.cenpro.siscu.controller.consulta.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaPsicologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoPsicologico;
import com.cenpro.siscu.model.movimiento.ExamenMedicoPsicologico;
import com.cenpro.siscu.service.IConsultaExamenMedicoPsicologicoService;
import com.cenpro.siscu.service.IExamenMedicoPsicologicoService;

@RequestMapping("/consulta/psicologico")
public @RestController class ConsultaExamenMedicoPsicologicoRestController
{
    private @Autowired IExamenMedicoPsicologicoService examenMedicoPsicologicoService;
    private @Autowired IConsultaExamenMedicoPsicologicoService consultaExamenMedicoPsicologicoService;

    @GetMapping(value = "/administrativa", params = "accion=buscarPorCriterioBusquedaAdministrativa")
    public List<ConsultaPsicologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoPsicologico criterioBusqueda)
    {
        return consultaExamenMedicoPsicologicoService
                .buscarPorCriterioBusquedaAdministrativa(criterioBusqueda);
    }

    @GetMapping(params = "accion=buscarResultadoRegularPorNumeroRegistro")
    public List<ExamenMedicoPsicologico> buscarResultadoRegularPorNumeroRegistro(
            CriterioBusquedaAdministrativaExamenMedicoPsicologico criterioBusqueda)
    {
        return examenMedicoPsicologicoService
                .buscarResultadoRegularPorNumeroRegistro(criterioBusqueda.getNumeroRegistro());
    }
}