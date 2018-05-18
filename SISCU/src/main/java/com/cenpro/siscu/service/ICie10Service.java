package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.mantenimiento.Cie10;

public interface ICie10Service extends IMantenibleService<Cie10>
{
    public List<Cie10> buscarPorDescripcion(String descripcion);
    
    public boolean existeCie10(String idCie10);
}