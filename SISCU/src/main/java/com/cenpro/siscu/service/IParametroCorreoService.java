package com.cenpro.siscu.service;

import com.cenpro.siscu.model.mantenimiento.ParametroCorreo;

public interface IParametroCorreoService extends IMantenibleService<ParametroCorreo>
{
    public ParametroCorreo buscarParametroCorreo();
}