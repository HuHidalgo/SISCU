package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.Interconsulta;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IInterconsultaMapper extends IMantenibleMapper<Interconsulta>
{
    @Select(value = { "{call MANT_INTERCONSULTA ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.secuencia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEspecialidad, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.motivo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Interconsulta> mantener(Parametro parametro);
}