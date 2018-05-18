package com.cenpro.siscu.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.mapper.IUsuarioMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.seguridad.Usuario;
import com.cenpro.siscu.service.IUsuarioService;
import com.cenpro.siscu.service.impl.MantenibleService;
import com.cenpro.siscu.utilitario.Verbo;

@Service
public class UsuarioService extends MantenibleService<Usuario> implements IUsuarioService
{
    @SuppressWarnings("unused")
    private IUsuarioMapper usuarioMapper;
    private @Autowired PasswordEncoder passwordEnconder;

    private static final String LOGIN = "LOGIN";

    public UsuarioService(@Qualifier("IUsuarioMapper") IMantenibleMapper<Usuario> mapper)
    {
        super(mapper);
        this.usuarioMapper = (IUsuarioMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Usuario> buscarTodos()
    {
        return this.buscar(new Usuario(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> buscarPorCodigoUsuario(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        return this.buscar(usuario, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarUsuario(Usuario usuario)
    {
        usuario.setContrasenia(passwordEnconder.encode(usuario.getContrasenia()));
        this.registrar(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarUsuario(Usuario usuario)
    {
        this.actualizar(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarUsuario(Usuario usuario)
    {
        this.eliminar(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeUsuario(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        return this.existe(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Usuario buscarPorIdUsuarioParaInicioSesion(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        List<Usuario> usuarios = this.buscar(usuario, LOGIN);
        return usuarios.stream().findFirst().orElse(null);
    }
}