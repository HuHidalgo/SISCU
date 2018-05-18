package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.Especialidad;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IEspecialidadMapper extends IMantenibleMapper<Especialidad>
{
    @Select(value = { "{call MANT_ESPECIALIDAD ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEspecialidad, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcionCorta, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Especialidad> mantener(Parametro parametro);
}