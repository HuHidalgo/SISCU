package com.cenpro.siscu.service.impl.movimiento;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IExamenMedicoOdontologiaMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoOdontologico;
import com.cenpro.siscu.service.IExamenMedicoOdontologiaService;
import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class ExamenMedicoOdontologicoService extends MantenibleService<ExamenMedicoOdontologico>
        implements IExamenMedicoOdontologiaService
{
    @SuppressWarnings("unused")
    private IExamenMedicoOdontologiaMapper examenMedicoOdontologiaMapper;

    public ExamenMedicoOdontologicoService(
            @Qualifier("IExamenMedicoOdontologiaMapper") IMantenibleMapper<ExamenMedicoOdontologico> mapper)
    {
        super(mapper);
        this.examenMedicoOdontologiaMapper = (IExamenMedicoOdontologiaMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarExamenMedicoOdontologico(ExamenMedicoOdontologico examenMedicoOdontologico)
    {
        this.registrar(examenMedicoOdontologico);
    }
}