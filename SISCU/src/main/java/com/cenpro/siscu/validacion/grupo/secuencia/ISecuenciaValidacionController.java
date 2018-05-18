package com.cenpro.siscu.validacion.grupo.secuencia;

import javax.validation.GroupSequence;

import com.cenpro.siscu.validacion.grupo.IMetodo;
import com.cenpro.siscu.validacion.grupo.IParametro;

@GroupSequence({ IParametro.class, IMetodo.class })
public interface ISecuenciaValidacionController
{

}
