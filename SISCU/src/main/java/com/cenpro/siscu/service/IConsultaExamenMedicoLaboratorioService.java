package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaLaboratorioAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoLaboratorio;

public interface IConsultaExamenMedicoLaboratorioService
{
    public List<ConsultaLaboratorioAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoLaboratorio criterioBusquedaAdministrativaExamenMedicoLaboratorio);
}