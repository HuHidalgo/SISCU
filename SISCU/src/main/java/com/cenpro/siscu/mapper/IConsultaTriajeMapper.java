package com.cenpro.siscu.mapper;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaTriajeAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaTriaje;

public interface IConsultaTriajeMapper
{
    public List<ConsultaTriajeAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaTriaje criterioBusquedaAdministrativaTriaje);
}