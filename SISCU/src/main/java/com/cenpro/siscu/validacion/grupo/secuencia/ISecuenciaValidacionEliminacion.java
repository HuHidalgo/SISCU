package com.cenpro.siscu.validacion.grupo.secuencia;

import javax.validation.GroupSequence;

import com.cenpro.siscu.validacion.grupo.ILlave;
import com.cenpro.siscu.validacion.grupo.accion.IActualizacion;

@GroupSequence({ ILlave.class, IActualizacion.class })
public interface ISecuenciaValidacionEliminacion
{

}