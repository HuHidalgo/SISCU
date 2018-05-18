package com.cenpro.siscu.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IConsultaTriajeMapper;
import com.cenpro.siscu.model.consulta.administrativa.ConsultaTriajeAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaTriaje;
import com.cenpro.siscu.service.IConsultaTriajeService;

@Service
public class ConsultaTriajeService implements IConsultaTriajeService
{
    private @Autowired IConsultaTriajeMapper consultaTriajeMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaTriajeAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaTriaje criterioBusquedaAdministrativaTriaje)
    {
        return consultaTriajeMapper
                .buscarPorCriterioBusquedaAdministrativa(criterioBusquedaAdministrativaTriaje);
    }
}