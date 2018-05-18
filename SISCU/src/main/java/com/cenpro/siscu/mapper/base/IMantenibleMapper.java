package com.cenpro.siscu.mapper.base;

import java.util.List;

import com.cenpro.siscu.model.parametro.Parametro;

public interface IMantenibleMapper<T>
{
    public List<T> mantener(Parametro parametro);
}
