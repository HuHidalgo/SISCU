package com.cenpro.siscu.service;

import com.cenpro.siscu.model.movimiento.ExamenMedicoTriaje;

public interface IExamenMedicoTriajeService extends IMantenibleService<ExamenMedicoTriaje>
{ 
	public void registrarExamenMedicoTriaje(ExamenMedicoTriaje examenMedicoTriaje);
}
