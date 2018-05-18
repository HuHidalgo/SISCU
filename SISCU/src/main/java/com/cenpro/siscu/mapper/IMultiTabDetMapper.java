package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.MultiTabDet;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IMultiTabDetMapper extends IMantenibleMapper<MultiTabDet>
{
    @Select(value = { "{call MANT_MULTI_TAB_DET ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTabla, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idItem, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcionItem, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.abreviatura, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<MultiTabDet> mantener(Parametro parametro);
}