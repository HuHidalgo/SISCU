package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.Antecedente;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IAntecedenteMapper extends IMantenibleMapper<Antecedente>
{
    @Select(value = { "{call MANT_ANTECEDENTE ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.secuencia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTipoDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTipoAntecedente, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCie10, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Antecedente> mantener(Parametro parametro);
}