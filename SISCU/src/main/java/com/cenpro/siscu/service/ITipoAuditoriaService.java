package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.seguridad.TipoAuditoria;

public interface ITipoAuditoriaService extends IMantenibleService<TipoAuditoria>
{
    public List<TipoAuditoria> buscarTodos();
}
