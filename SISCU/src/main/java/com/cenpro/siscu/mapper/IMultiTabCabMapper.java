package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.MultiTabCab;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IMultiTabCabMapper extends IMantenibleMapper<MultiTabCab>
{
    @Select(value = { "{call MANT_MULTI_TAB_CAB ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTabla, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<MultiTabCab> mantener(Parametro parametro);
}