package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;

public interface IAfiliacionService extends IMantenibleService<Afiliacion>
{
    public List<Afiliacion> buscarTodos();
    
    public List<Afiliacion> buscarPorNroDocumento(CriterioBusquedaEstamento criterioBusquedaEstamento);
    
    public List<Afiliacion> buscarPorId(String nroDocumento, String tipoDocumento);
    
    public List<Afiliacion> registrarAfiliacion(Afiliacion afiliacion);
    
    public void actualizarAfiliacion(Afiliacion afiliacion);
    
    //public List<Afiliacion> buscarPorNroDocumento( CriterioBusquedaEstamento criterioBusquedaEstamento);
}