package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.procesoautomatico.ProcesoAutomatico;

public interface IProcesoAutomaticoService extends IMantenibleService<ProcesoAutomatico>
{
    public List<ProcesoAutomatico> buscarTodos();

    public List<ProcesoAutomatico> buscarPorIdProcesoAutomatico(String idProcesoAutomatico);

    public List<ProcesoAutomatico> buscarPorEstado(boolean estado);
    
    public boolean existeProcesoAutomatico(String idProcesoAutomatico);

    public void registrarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);

    public void actualizarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);

    public void eliminarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);
}