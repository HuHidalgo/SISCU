package com.cenpro.siscu.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IConsultaExamenMedicoPsicologicoMapper;
import com.cenpro.siscu.model.consulta.administrativa.ConsultaPsicologicoAdministrativo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAdministrativaExamenMedicoPsicologico;
import com.cenpro.siscu.service.IConsultaExamenMedicoPsicologicoService;

@Service
public class ConsultaExamenMedicoPsicologicoService
        implements IConsultaExamenMedicoPsicologicoService
{
    private @Autowired IConsultaExamenMedicoPsicologicoMapper consultaExamenMedicoPsicologicoMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaPsicologicoAdministrativo> buscarPorCriterioBusquedaAdministrativa(
            CriterioBusquedaAdministrativaExamenMedicoPsicologico criterioBusquedaAdministrativaExamenMedicoPsicologico)
    {
        return consultaExamenMedicoPsicologicoMapper.buscarPorCriterioBusquedaAdministrativa(
                criterioBusquedaAdministrativaExamenMedicoPsicologico);
    }
}