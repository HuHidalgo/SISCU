package com.cenpro.siscu.validacion.grupo.secuencia;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import com.cenpro.siscu.validacion.grupo.ILlave;
import com.cenpro.siscu.validacion.grupo.accion.IActualizacion;

@GroupSequence({ Default.class, ILlave.class, IActualizacion.class })
public interface ISecuenciaValidacionActualizacion
{

}