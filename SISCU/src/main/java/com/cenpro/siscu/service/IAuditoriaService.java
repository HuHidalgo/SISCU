package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.aspecto.enumeracion.Tipo;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAuditoria;
import com.cenpro.siscu.model.seguridad.Auditoria;

public interface IAuditoriaService extends IMantenibleService<Auditoria>
{
    public List<Auditoria> buscarPistasAuditoriaPorCriterio(CriterioBusquedaAuditoria criterio);

    public void registrarAuditoria(Auditoria auditoria);

    public void registrarAuditoria(Tipo tipo, Comentario comentario, Accion accion, boolean exito,
            String nombreUsuario, String direccionIp);
}