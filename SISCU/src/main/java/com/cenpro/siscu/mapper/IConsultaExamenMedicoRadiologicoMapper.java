package com.cenpro.siscu.mapper;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaRadiologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoRadiologico;

public interface IConsultaExamenMedicoRadiologicoMapper
{
    public List<ConsultaRadiologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoRadiologico criterioBusquedaAdministrativaExamenMedicoRadiologico);
}