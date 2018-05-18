package com.cenpro.siscu.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IConsultaExamenMedicoRadiologicoMapper;
import com.cenpro.siscu.model.consulta.administrativa.ConsultaRadiologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoRadiologico;
import com.cenpro.siscu.service.IConsultaExamenMedicoRadiologicoService;

@Service
public class ConsultaExamenMedicoRadiologicoService
        implements IConsultaExamenMedicoRadiologicoService
{
    private @Autowired IConsultaExamenMedicoRadiologicoMapper consultaExamenMedicoRadiologicoMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaRadiologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoRadiologico criterioBusquedaAdministrativaExamenMedicoRadiologico)
    {
        return consultaExamenMedicoRadiologicoMapper.buscarPorCriterioBusquedaAdministrativa(
                criterioBusquedaAdministrativaExamenMedicoRadiologico);
    }
}