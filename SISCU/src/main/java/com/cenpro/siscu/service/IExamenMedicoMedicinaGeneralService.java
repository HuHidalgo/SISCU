package com.cenpro.siscu.service;

import com.cenpro.siscu.model.movimiento.ExamenMedicoMedicinaGeneral;

public interface IExamenMedicoMedicinaGeneralService
        extends IMantenibleService<ExamenMedicoMedicinaGeneral>
{
    public void registrarExamenMedicoMedicinaGeneral(
            ExamenMedicoMedicinaGeneral examenMedicoMedicinaGeneral);

    public boolean existeExamenMedicoMedicinaGeneral(Integer numeroRegistro, String anio);
}