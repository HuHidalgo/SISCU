package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.seguridad.Perfil;

public interface IPerfilService extends IMantenibleService<Perfil>
{
    public List<Perfil> buscarTodos();
}