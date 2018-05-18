package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.movimiento.ExamenMedicoRadiologico;

public interface IExamenMedicoRadiologicoService extends IMantenibleService<ExamenMedicoRadiologico>
{
    public int generarNumeroRegistro(ExamenMedicoRadiologico examenMedicoRadiologico);

    public List<ExamenMedicoRadiologico> buscarPorEstadoExamenMedico(String idEstadoExamenMedico);

    public void actualizarTomaRadiologica(ExamenMedicoRadiologico examenMedicoRadiologico);

    public void registrarExamenMedicoRegularRadiologico(
            ExamenMedicoRadiologico examenMedicoRadiologico);

    public List<ExamenMedicoRadiologico> buscarResultadoRegularPorNumeroRegistroAnio(
            Integer numeroRegistro, String anio);
    
    public boolean existeExamenMedicoRadiologico(Integer numeroRegistro, String anio);
}