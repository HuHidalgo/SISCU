package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.mantenimiento.Campania;

public interface ICampaniaService extends IMantenibleService<Campania>
{
    public List<Campania> buscarTodos();
    
    public List<Campania> buscarPorId(int idCampania);
    
    public boolean existeCampania(int idCampania);
    
    public List<Campania> buscarPorEstadoActivo(boolean estadoActivo);
    
    public int registrarCampania(Campania campania);
    
    public void actualizarCampania(Campania campania);
    
    public void eliminarCampania(Campania campania);
}