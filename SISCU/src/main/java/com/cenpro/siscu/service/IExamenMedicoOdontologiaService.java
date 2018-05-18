package com.cenpro.siscu.service;

import com.cenpro.siscu.model.movimiento.ExamenMedicoOdontologico;

public interface IExamenMedicoOdontologiaService extends IMantenibleService<ExamenMedicoOdontologico>
{
	public void registrarExamenMedicoOdontologico (ExamenMedicoOdontologico examenMedicoOdontologico);
}
