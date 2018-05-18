package com.cenpro.siscu.service.impl.movimiento;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IExamenMedicoTriajeMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoTriaje;
import com.cenpro.siscu.service.IExamenMedicoTriajeService;
import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class ExamenMedicoTriajeService extends MantenibleService<ExamenMedicoTriaje>
        implements IExamenMedicoTriajeService
{
    @SuppressWarnings("unused")
    private IExamenMedicoTriajeMapper examenMedicoTriajeMapper;

    public ExamenMedicoTriajeService(
            @Qualifier("IExamenMedicoTriajeMapper") IMantenibleMapper<ExamenMedicoTriaje> mapper)
    {
        super(mapper);
        this.examenMedicoTriajeMapper = (IExamenMedicoTriajeMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarExamenMedicoTriaje(ExamenMedicoTriaje examenMedicoTriaje)
    {
        this.registrar(examenMedicoTriaje);
    }
}