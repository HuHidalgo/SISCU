package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.seguridad.Usuario;

public interface IUsuarioService extends IMantenibleService<Usuario>
{
    public List<Usuario> buscarTodos();

    public void registrarUsuario(Usuario usuario);

    public List<Usuario> buscarPorCodigoUsuario(String idUsuario);

    public void actualizarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

	public boolean existeUsuario(String idUsusario);
	
	public Usuario buscarPorIdUsuarioParaInicioSesion(String idUsuario);
}