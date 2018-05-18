package com.cenpro.siscu.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IMedicoMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.Medico;
import com.cenpro.siscu.service.IMedicoService;
import com.cenpro.siscu.service.excepcion.ValorNoEncontradoException;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class MedicoService extends MantenibleService<Medico> implements IMedicoService
{
    @SuppressWarnings("unused")
    private IMedicoMapper medicoMapper;

    private static final String GET_DIRECTOR = "GET_DIRECTOR";

    public MedicoService(@Qualifier("IMedicoMapper") IMantenibleMapper<Medico> mapper)
    {
        super(mapper);
        this.medicoMapper = (IMedicoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Medico> buscarTodos()
    {
        return this.buscar(new Medico(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Medico> buscarPorTipoDocumentoNumeroDocumento(String idTipoDocumento,
            String numeroDocumento)
    {
        Medico medico = Medico.builder().idTipoDocumento(idTipoDocumento)
                .numeroDocumento(numeroDocumento).build();
        return this.buscar(medico, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Medico buscarDirector()
    {
        List<Medico> medicos = this.buscar(new Medico(), GET_DIRECTOR);
        if (medicos.isEmpty())
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.NO_EXISTE_MEDICO_DIRECTOR);
        }
        return medicos.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeMedico(String idTipoDocumento, String numeroDocumento)
    {
        Medico medico = Medico.builder().idTipoDocumento(idTipoDocumento)
                .numeroDocumento(numeroDocumento).build();
        return this.existe(medico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMedico(Medico medico)
    {
        this.registrar(medico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMedico(Medico medico)
    {
        this.actualizar(medico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMedico(Medico medico)
    {
        this.eliminar(medico);
    }
}