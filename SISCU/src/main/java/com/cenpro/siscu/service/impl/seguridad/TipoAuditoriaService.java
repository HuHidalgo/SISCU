package com.cenpro.siscu.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.ITipoAuditoriaMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.seguridad.TipoAuditoria;
import com.cenpro.siscu.service.ITipoAuditoriaService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class TipoAuditoriaService extends MantenibleService<TipoAuditoria>
        implements ITipoAuditoriaService
{
    @SuppressWarnings("unused")
    private ITipoAuditoriaMapper tipoAuditoriaMapper;

    public TipoAuditoriaService(
            @Qualifier("ITipoAuditoriaMapper") IMantenibleMapper<TipoAuditoria> mapper)
    {
        super(mapper);
        this.tipoAuditoriaMapper = (ITipoAuditoriaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoAuditoria> buscarTodos()
    {
        return this.buscar(new TipoAuditoria(), Verbo.GETS);
    }
}