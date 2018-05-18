package com.cenpro.siscu.service.impl.admision;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IAfiliacionMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.service.IAfiliacionService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class AfiliacionService extends MantenibleService<Afiliacion> implements IAfiliacionService
{
    @SuppressWarnings("unused")
    private IAfiliacionMapper afiliacionMapper;

    public AfiliacionService(@Qualifier("IAfiliacionMapper") IMantenibleMapper<Afiliacion> mapper)
    {
        super(mapper);
        this.afiliacionMapper = (IAfiliacionMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> buscarTodos()
    {
        return this.buscar(new Afiliacion(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> buscarPorId()
    {
        return this.buscar(new Afiliacion(), Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAfiliacion(Afiliacion afiliacion)
    {
        this.registrar(afiliacion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarAfiliacion(Afiliacion afiliacion)
    {
        this.actualizar(afiliacion);
    }
}