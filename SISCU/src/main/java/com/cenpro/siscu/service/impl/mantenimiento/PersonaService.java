package com.cenpro.siscu.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IPersonaMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.Persona;
import com.cenpro.siscu.service.IPersonaService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class PersonaService extends MantenibleService<Persona> implements IPersonaService
{
    @SuppressWarnings("unused")
    private IPersonaMapper personaMapper;

    public PersonaService(@Qualifier("IPersonaMapper") IMantenibleMapper<Persona> mapper)
    {
        super(mapper);
        this.personaMapper = (IPersonaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Persona> buscarTodos()
    {
        return this.buscar(new Persona(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Persona> buscarPorTipoDocumentoNumeroDocumento(String idTipoDocumento,
            String numeroDocumento)
    {
        Persona persona = Persona.builder().idTipoDocumento(idTipoDocumento)
                .numeroDocumento(numeroDocumento).build();
        return this.buscar(persona, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existePersona(String idTipoDocumento, String numeroDocumento)
    {
        Persona persona = Persona.builder().idTipoDocumento(idTipoDocumento)
                .numeroDocumento(numeroDocumento).build();
        return this.existe(persona);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarPersona(Persona persona)
    {
        this.registrar(persona);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarPersona(Persona persona)
    {
        this.actualizar(persona);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarPersona(Persona persona)
    {
        this.eliminar(persona);
    }
}