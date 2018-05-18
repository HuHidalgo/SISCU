package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaPsicologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoPsicologico;

public interface IConsultaExamenMedicoPsicologicoService
{
    public List<ConsultaPsicologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoPsicologico criterioBusquedaAdministrativaExamenMedicoPsicologico);
}