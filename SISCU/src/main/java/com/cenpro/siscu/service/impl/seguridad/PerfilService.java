package com.cenpro.siscu.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IPerfilMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.seguridad.Perfil;
import com.cenpro.siscu.service.IPerfilService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class PerfilService extends MantenibleService<Perfil> implements IPerfilService
{
    @SuppressWarnings("unused")
    private IPerfilMapper perfilMapper;

    public PerfilService(@Qualifier("IPerfilMapper") IMantenibleMapper<Perfil> mapper)
    {
        super(mapper);
        this.perfilMapper = (IPerfilMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Perfil> buscarTodos()
    {
        return this.buscar(new Perfil(), Verbo.GETS);
    }
}