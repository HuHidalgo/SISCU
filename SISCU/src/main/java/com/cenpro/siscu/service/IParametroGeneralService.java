package com.cenpro.siscu.service;

import java.util.List;

import com.cenpro.siscu.model.mantenimiento.ParametroGeneral;

public interface IParametroGeneralService extends IMantenibleService<ParametroGeneral>
{
    public List<ParametroGeneral> buscarTodos();
    
    public void actualizarParametroGeneral(ParametroGeneral parametroGeneral);
    
    public Integer buscarAnioInicio();
    
    public String buscarCorreoSUM();
    
    public String buscarCorreoClinica();
}