package com.cenpro.siscu.service.impl.admision;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IAfiliacionMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.service.IAfiliacionService;
import com.cenpro.siscu.service.excepcion.MantenimientoException;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class AfiliacionService extends MantenibleService<Afiliacion> implements IAfiliacionService
{
    //@SuppressWarnings("unused")
    private @Autowired IAfiliacionMapper afiliacionMapper;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> buscarPorNroDocumento(CriterioBusquedaEstamento criterioBusquedaEstamento)
    {
    	return this.afiliacionMapper.buscarPorNroDocumento(criterioBusquedaEstamento);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> buscarPorNroDocumentoNoAfiliado(CriterioBusquedaEstamento criterioBusquedaEstamento)
    {
    	return this.afiliacionMapper.buscarPorNroDocumentoNoAfiliado(criterioBusquedaEstamento);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> buscarPorId(String nroDocumento, String tipoDocumento)
    {
    	Afiliacion afiliacion = Afiliacion.builder().numeroDocumento(nroDocumento).idTipoDocumento(tipoDocumento).build();
        return this.buscar(afiliacion, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Afiliacion> registrarAfiliacion(Afiliacion afiliacion)
    {
    	List<Afiliacion> afiliaciones = this.registrarAutoIncrementable(afiliacion);
        if (!afiliaciones.isEmpty() && afiliaciones.get(0).getIdAfiliacion() != null)
        {
            return afiliaciones;
        } else
        {
            throw new MantenimientoException(ConstantesExcepciones.ERROR_REGISTRO);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarAfiliacion(Afiliacion afiliacion)
    {
        this.actualizar(afiliacion);
    }
}