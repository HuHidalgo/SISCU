package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.consulta.ConsultaAtencionInicial;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAtencionInicial;

public interface IConsultaAtencionInicialService
{
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialLaboratorioPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);

    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialPsicologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);

    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialRadiologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);

    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialTriajePorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);

    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialOdontologiaPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);
    
    public List<ConsultaAtencionInicial> buscarConsultaAtencionInicialMedicinaGeneralPorCriterioBusqueda(
            CriterioBusquedaAtencionInicial criterioBusqueda);
}