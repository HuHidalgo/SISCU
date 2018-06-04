package com.cenpro.siscu.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IMultiTabDetMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.MultiTabDet;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class MultiTabDetService extends MantenibleService<MultiTabDet>
        implements IMultiTabDetService
{
    @SuppressWarnings("unused")
    private IMultiTabDetMapper multiTabDetMapper;

    public MultiTabDetService(
            @Qualifier("IMultiTabDetMapper") IMantenibleMapper<MultiTabDet> mapper)
    {
        super(mapper);
        this.multiTabDetMapper = (IMultiTabDetMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MultiTabDet> buscarTodos()
    {
        return this.buscar(new MultiTabDet(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MultiTabDet> buscarPorIdTabla(int idTabla)
    {
        MultiTabDet multiTabDet = MultiTabDet.builder().idTabla(idTabla).build();
        return this.buscar(multiTabDet, Verbo.GET);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MultiTabDet> buscarPorIdItem(String idItem)
    {
        MultiTabDet multiTabDet = MultiTabDet.builder().idItem(idItem).build();
        return this.buscar(multiTabDet, Verbo.GET_ITEM);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MultiTabDet> buscarPorIdTablaIdItem(int idTabla, String idItem)
    {
        MultiTabDet multiTabDet = MultiTabDet.builder().idTabla(idTabla).idItem(idItem).build();
        return this.buscar(multiTabDet, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeMultiTabDet(int idTabla, String idItem)
    {
        return !this.buscarPorIdTablaIdItem(idTabla, idItem).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMultiTabDet(MultiTabDet multiTabDet)
    {
        this.registrar(multiTabDet);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMultiTabDet(MultiTabDet multiTabDet)
    {
        this.actualizar(multiTabDet);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMultiTabDet(MultiTabDet multiTabDet)
    {
        this.eliminar(multiTabDet);
    }
}