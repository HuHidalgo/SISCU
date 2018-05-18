package com.cenpro.siscu.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IParametroGeneralMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.ParametroGeneral;
import com.cenpro.siscu.service.IParametroGeneralService;
import com.cenpro.siscu.service.excepcion.FormatoException;
import com.cenpro.siscu.service.excepcion.ListaVaciaException;
import com.cenpro.siscu.service.excepcion.ValorNoEncontradoException;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.StringsUtils;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class ParametroGeneralService extends MantenibleService<ParametroGeneral>
        implements IParametroGeneralService
{
    @SuppressWarnings("unused")
    private IParametroGeneralMapper parametroGeneralMapper;

    private static final String GET_SUM = "GET_SUM";
    private static final String GET_CLINICA = "GET_CLINICA";
    private static final String GET_ANIO_INI = "GET_ANIO_INI";

    public ParametroGeneralService(
            @Qualifier("IParametroGeneralMapper") IMantenibleMapper<ParametroGeneral> mapper)
    {
        super(mapper);
        this.parametroGeneralMapper = (IParametroGeneralMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ParametroGeneral> buscarTodos()
    {
        return this.buscar(new ParametroGeneral(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarParametroGeneral(ParametroGeneral parametroGeneral)
    {
        this.actualizar(parametroGeneral);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer buscarAnioInicio()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(),
                GET_ANIO_INI);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getAnioInicial() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.ANIO_INICIAL_NO_ENCONTRADO);
        }
        if (!StringsUtils.esNumero(parametroGeneral.getAnioInicial()))
        {
            throw new FormatoException(ConstantesExcepciones.ANIO_INICIAL_MAL_FORMATO);
        }
        return Integer.parseInt(parametroGeneral.getAnioInicial());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String buscarCorreoSUM()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), GET_SUM);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getCorreoSUM() == null || parametroGeneral.getCorreoSUM().isEmpty())
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CORREO_SUM_NO_ENCONTRADO);
        }
        return parametroGeneral.getCorreoSUM();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String buscarCorreoClinica()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(),
                GET_CLINICA);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getCorreoClinica() == null
                || parametroGeneral.getCorreoClinica().isEmpty())
        {
            throw new ValorNoEncontradoException(
                    ConstantesExcepciones.CORREO_CLINICA_NO_ENCONTRADO);
        }
        return parametroGeneral.getCorreoClinica();
    }
}