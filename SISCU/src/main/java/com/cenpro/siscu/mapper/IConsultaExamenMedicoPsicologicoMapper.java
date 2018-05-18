package com.cenpro.siscu.mapper;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaPsicologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoPsicologico;

public interface IConsultaExamenMedicoPsicologicoMapper
{
    public List<ConsultaPsicologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoPsicologico criterioBusquedaAdministrativaExamenMedicoPsicologico);
}