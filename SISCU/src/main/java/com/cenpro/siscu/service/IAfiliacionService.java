package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.admision.Afiliacion;

public interface IAfiliacionService extends IMantenibleService<Afiliacion>
{
    public List<Afiliacion> buscarTodos();
    
    public List<Afiliacion> buscarPorId();
    
    public void registrarAfiliacion(Afiliacion afiliacion);
    
    public void actualizarAfiliacion(Afiliacion afiliacion);
}