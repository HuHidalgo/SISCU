package com.cenpro.siscu.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IMultiTabCabMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.MultiTabCab;
import com.cenpro.siscu.service.IMultiTabCabService;
import com.cenpro.siscu.service.excepcion.MantenimientoException;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class MultiTabCabService extends MantenibleService<MultiTabCab>
        implements IMultiTabCabService
{
    @SuppressWarnings("unused")
    private IMultiTabCabMapper multiTabCabMapper;

    public MultiTabCabService(
            @Qualifier("IMultiTabCabMapper") IMantenibleMapper<MultiTabCab> mapper)
    {
        super(mapper);
        this.multiTabCabMapper = (IMultiTabCabMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MultiTabCab> buscarTodos()
    {
        return this.buscar(new MultiTabCab(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MultiTabCab> buscarPorIdTabla(int idTabla)
    {
        MultiTabCab multiTabCab = MultiTabCab.builder().idTabla(idTabla).build();
        return this.buscar(multiTabCab, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeIdTabla(Integer idTabla)
    {
        return !this.buscarPorIdTabla(idTabla).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int registrarMultiTabCab(MultiTabCab multiTabCab)
    {
        List<MultiTabCab> multitablas = this.registrarAutoIncrementable(multiTabCab);
        if (!multitablas.isEmpty() && multitablas.get(0).getIdTabla() > 0)
        {
            return multitablas.get(0).getIdTabla();
        } else
        {
            throw new MantenimientoException(ConstantesExcepciones.ERROR_REGISTRO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMultiTabCab(MultiTabCab multiTabCab)
    {
        this.actualizar(multiTabCab);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMultiTabCab(MultiTabCab multiTabCab)
    {
        this.eliminar(multiTabCab);
    }
}