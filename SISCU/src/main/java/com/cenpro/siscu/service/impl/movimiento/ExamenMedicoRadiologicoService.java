package com.cenpro.siscu.service.impl.movimiento;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IExamenMedicoRadiologicoMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoRadiologico;
import com.cenpro.siscu.service.IExamenMedicoRadiologicoService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;

@Service
public class ExamenMedicoRadiologicoService extends MantenibleService<ExamenMedicoRadiologico>
        implements IExamenMedicoRadiologicoService
{
    private static final String INSERT_REG = "INSERT_REG";
    private static final String GET_UPDATE_REG = "GET_UPDATE_REG";
    private static final String UPDATE_TOMA_RAD = "UPDATE_TOMA_RAD";
    private static final String GET_ESTADO_EXA_MED = "GET_ESTADO_EXA_MED";

    @SuppressWarnings("unused")
    private @Autowired IExamenMedicoRadiologicoMapper examenMedicoRadiologicoMapper;

    public ExamenMedicoRadiologicoService(
            @Qualifier("IExamenMedicoRadiologicoMapper") IMantenibleMapper<ExamenMedicoRadiologico> mapper)
    {
        super(mapper);
        this.examenMedicoRadiologicoMapper = (IExamenMedicoRadiologicoMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int generarNumeroRegistro(ExamenMedicoRadiologico examenMedicoRadiologico)
    {
        List<ExamenMedicoRadiologico> examenes = this
                .registrarAutoIncrementable(examenMedicoRadiologico);
        Validate.notEmpty(examenes, ConstantesExcepciones.ERROR_REGISTRO);
        Validate.notNull(examenes.get(0).getNumeroRegistro(), ConstantesExcepciones.ERROR_REGISTRO);
        return examenes.get(0).getNumeroRegistro();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ExamenMedicoRadiologico> buscarPorEstadoExamenMedico(String idEstadoExamenMedico)
    {
        ExamenMedicoRadiologico examenMedicoRadiologico = ExamenMedicoRadiologico.builder()
                .idEstadoExamenMedico(idEstadoExamenMedico).build();
        return this.buscar(examenMedicoRadiologico, GET_ESTADO_EXA_MED);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarTomaRadiologica(ExamenMedicoRadiologico examenMedicoRadiologico)
    {
        this.actualizar(examenMedicoRadiologico, UPDATE_TOMA_RAD);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarExamenMedicoRegularRadiologico(
            ExamenMedicoRadiologico examenMedicoRadiologico)
    {
        this.registrar(examenMedicoRadiologico, INSERT_REG);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ExamenMedicoRadiologico> buscarResultadoRegularPorNumeroRegistroAnio(
            Integer numeroRegistro, String anio)
    {
        ExamenMedicoRadiologico examenMedicoRadiologico = ExamenMedicoRadiologico.builder()
                .numeroRegistro(numeroRegistro).anio(anio).build();
        return this.buscar(examenMedicoRadiologico, GET_UPDATE_REG);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeExamenMedicoRadiologico(Integer numeroRegistro, String anio)
    {
        ExamenMedicoRadiologico examenMedicoRadiologico = ExamenMedicoRadiologico.builder()
                .numeroRegistro(numeroRegistro).anio(anio).build();
        return this.existe(examenMedicoRadiologico);
    }
}