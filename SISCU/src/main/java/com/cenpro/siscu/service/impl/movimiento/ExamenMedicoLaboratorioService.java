package com.cenpro.siscu.service.impl.movimiento;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IExamenMedicoLaboratorioMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoLaboratorio;
import com.cenpro.siscu.service.IExamenMedicoLaboratorioService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;

@Service
public class ExamenMedicoLaboratorioService extends MantenibleService<ExamenMedicoLaboratorio>
        implements IExamenMedicoLaboratorioService
{
    private static final String INSERT_REG = "INSERT_REG";
    private static final String INSERT_ING = "INSERT_ING";
    private static final String GET_UPDATE_REG = "GET_UPDATE_REG";
    private static final String GET_UPDATE_ING = "GET_UPDATE_ING";
    private static final String GET_ESTADO_EXA_MED = "GET_ESTADO_EXA_MED";

    @SuppressWarnings("unused")
    private IExamenMedicoLaboratorioMapper examenMedicoLaboratorioMapper;

    public ExamenMedicoLaboratorioService(
            @Qualifier("IExamenMedicoLaboratorioMapper") IMantenibleMapper<ExamenMedicoLaboratorio> mapper)
    {
        super(mapper);
        this.examenMedicoLaboratorioMapper = (IExamenMedicoLaboratorioMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int generarNumeroRegistro(ExamenMedicoLaboratorio examenMedicoLaboratorio)
    {
        List<ExamenMedicoLaboratorio> examenes = this
                .registrarAutoIncrementable(examenMedicoLaboratorio);
        Validate.notEmpty(examenes, ConstantesExcepciones.ERROR_REGISTRO);
        Validate.notNull(examenes.get(0).getNumeroRegistro(), ConstantesExcepciones.ERROR_REGISTRO);
        return examenes.get(0).getNumeroRegistro();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ExamenMedicoLaboratorio> buscarPorEstadoExamenMedico(String idEstadoExamenMedico)
    {
        ExamenMedicoLaboratorio examenMedicoLaboratorio = ExamenMedicoLaboratorio.builder()
                .idEstadoExamenMedico(idEstadoExamenMedico).build();
        return this.buscar(examenMedicoLaboratorio, GET_ESTADO_EXA_MED);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarExamenMedicoRegularLaboratorio(
            ExamenMedicoLaboratorio examenMedicoLaboratorio)
    {
        this.registrar(examenMedicoLaboratorio, INSERT_REG);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarExamenMedicoIngresanteLaboratorio(
            ExamenMedicoLaboratorio examenMedicoLaboratorio)
    {
        this.registrar(examenMedicoLaboratorio, INSERT_ING);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ExamenMedicoLaboratorio> buscarResultadoRegularPorNumeroRegistroAnio(
            Integer numeroRegistro, String anio)
    {
        ExamenMedicoLaboratorio examenMedicoLaboratorio = ExamenMedicoLaboratorio.builder()
                .numeroRegistro(numeroRegistro).anio(anio).build();
        return this.buscar(examenMedicoLaboratorio, GET_UPDATE_REG);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ExamenMedicoLaboratorio> buscarResultadoIngresantePorNumeroRegistroAnio(
            Integer numeroRegistro, String anio)
    {
        ExamenMedicoLaboratorio examenMedicoLaboratorio = ExamenMedicoLaboratorio.builder()
                .numeroRegistro(numeroRegistro).anio(anio).build();
        return this.buscar(examenMedicoLaboratorio, GET_UPDATE_ING);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeExamenMedicoLaboratorio(Integer numeroRegistro, String anio)
    {
        ExamenMedicoLaboratorio examenMedicoLaboratorio = ExamenMedicoLaboratorio.builder()
                .numeroRegistro(numeroRegistro).anio(anio).build();
        return this.existe(examenMedicoLaboratorio);
    }
}