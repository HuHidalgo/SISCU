package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.mantenimiento.Medico;

public interface IMedicoService extends IMantenibleService<Medico>
{
    public List<Medico> buscarTodos();

    public List<Medico> buscarPorTipoDocumentoNumeroDocumento(String idTipoDocumento,
            String numeroDocumento);

    public Medico buscarDirector();

    public boolean existeMedico(String idTipoDocumento, String numeroDocumento);

    public void registrarMedico(Medico medico);

    public void actualizarMedico(Medico medico);

    public void eliminarMedico(Medico medico);
}