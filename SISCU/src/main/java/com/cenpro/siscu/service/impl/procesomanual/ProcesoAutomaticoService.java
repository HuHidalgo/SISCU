package com.cenpro.siscu.service.impl.procesomanual;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IProcesoAutomaticoMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.parametro.MensajeValidacion;
import com.cenpro.siscu.model.procesoautomatico.ProcesoAutomatico;
import com.cenpro.siscu.service.IProcesoAutomaticoService;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class ProcesoAutomaticoService extends MantenibleService<ProcesoAutomatico>
        implements IProcesoAutomaticoService
{
    @SuppressWarnings("unused")
    private IProcesoAutomaticoMapper procesoAutomaticoMapper;

    private static final String GET_HORA = "GET_HORA";
    private static final String GET_ORDEN = "GET_ORDEN";
    private static final String GETS_ESTADO = "GETS_ESTADO";

    public ProcesoAutomaticoService(
            @Qualifier("IProcesoAutomaticoMapper") IMantenibleMapper<ProcesoAutomatico> mapper)
    {
        super(mapper);
        this.procesoAutomaticoMapper = (IProcesoAutomaticoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ProcesoAutomatico> buscarTodos()
    {
        return this.buscar(new ProcesoAutomatico(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ProcesoAutomatico> buscarPorIdProcesoAutomatico(String idProcesoAutomatico)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .idProcesoAutomatico(idProcesoAutomatico).build();
        return this.buscar(procesoAutomatico, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ProcesoAutomatico> buscarPorEstado(boolean activo)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder().activo(activo).build();
        return this.buscar(procesoAutomatico, GETS_ESTADO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoAutomatico> buscarPorHoraProgramada(String horaProgramada)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .horaProgramada(horaProgramada).build();
        return this.buscar(procesoAutomatico, GET_HORA);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoAutomatico> buscarPorOrdenEjecucion(int ordenEjecucion)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .ordenEjecucion(ordenEjecucion).build();
        return this.buscar(procesoAutomatico, GET_ORDEN);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeProcesoAutomatico(String idProcesoAutomatico)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .idProcesoAutomatico(idProcesoAutomatico).build();
        return this.existe(procesoAutomatico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.validarHoraPogramadaOrdenEjecucionNoRepetido(
                procesoAutomatico.getIdProcesoAutomatico(), procesoAutomatico.getHoraProgramada(),
                procesoAutomatico.getOrdenEjecucion());
        this.registrar(procesoAutomatico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.validarHoraPogramadaOrdenEjecucionNoRepetido(
                procesoAutomatico.getIdProcesoAutomatico(), procesoAutomatico.getHoraProgramada(),
                procesoAutomatico.getOrdenEjecucion());
        this.actualizar(procesoAutomatico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.eliminar(procesoAutomatico);
    }

    public void validarHoraPogramadaOrdenEjecucionNoRepetido(String idProcesoAutomatico,
            String horaProgramada, Integer ordenEjecucion)
    {
        List<MensajeValidacion> errores = new ArrayList<>();
        if (this.existeProcesoAutomaticoPorHoraProgramadaRepetido(idProcesoAutomatico,
                horaProgramada))
        {
            errores.add(new MensajeValidacion("horaProgramada",
                    String.format(ConstantesExcepciones.HORA_PROGRAMADA_REPETIDO, horaProgramada)));
        }
        if (this.existeProcesoAutomaticoPorOrdenEjecucionRepetido(idProcesoAutomatico,
                ordenEjecucion))
        {
            errores.add(new MensajeValidacion("ordenEjecucion",
                    String.format(ConstantesExcepciones.ORDEN_EJEC_REPETIDO, ordenEjecucion)));
        }
        if (!errores.isEmpty())
        {
            throw new BadRequestException(errores);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeProcesoAutomaticoPorHoraProgramadaRepetido(String codigoGrupo,
            String horaProgramada)
    {
        boolean existeProcesoAutomaticoPorHoraProgramadaRepetido = false;
        List<ProcesoAutomatico> procesosAutomaticos = this.buscarPorHoraProgramada(horaProgramada);
        boolean existeProcesoAutomaticoPorHoraProgramada = !procesosAutomaticos.isEmpty();
        if (existeProcesoAutomaticoPorHoraProgramada)
        {
            String idProcesoAutomatico = procesosAutomaticos.get(0).getIdProcesoAutomatico();
            existeProcesoAutomaticoPorHoraProgramadaRepetido = !idProcesoAutomatico
                    .equals(codigoGrupo);
        }
        return existeProcesoAutomaticoPorHoraProgramadaRepetido;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido(String codigoGrupo,
            int ordenEjecucion)
    {
        boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido = false;
        List<ProcesoAutomatico> procesosAutomaticos = this.buscarPorOrdenEjecucion(ordenEjecucion);
        boolean existeProcesoAutomaticoPorOrdenEjecucion = !procesosAutomaticos.isEmpty();
        if (existeProcesoAutomaticoPorOrdenEjecucion)
        {
            String idProcesoAutomatico = procesosAutomaticos.get(0).getIdProcesoAutomatico();
            existeProcesoAutomaticoPorOrdenEjecucionRepetido = !idProcesoAutomatico
                    .equals(codigoGrupo);
        }
        return existeProcesoAutomaticoPorOrdenEjecucionRepetido;
    }
}