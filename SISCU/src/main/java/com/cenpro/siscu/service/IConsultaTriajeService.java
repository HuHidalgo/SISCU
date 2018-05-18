package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.consulta.administrativa.ConsultaTriajeAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaTriaje;

public interface IConsultaTriajeService
{
    public List<ConsultaTriajeAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaTriaje criterioBusquedaAdministrativaTriaje);
}