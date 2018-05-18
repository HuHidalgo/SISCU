package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.Campania;
import com.cenpro.siscu.model.parametro.Parametro;

public interface ICampaniaMapper extends IMantenibleMapper<Campania>
{
    @Select(value = { "{call MANT_CAMPANIAS ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCampania, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcionCorta, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.activo, jdbcType = BIT, mode = IN},"
            + "#{objeto.idTipoCertificado, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fechaEjecucion, jdbcType = DATE, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Campania> mantener(Parametro parametro);
}