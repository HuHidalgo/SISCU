package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoOdontologico;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IExamenMedicoOdontologiaMapper extends IMantenibleMapper<ExamenMedicoOdontologico>
{
    @Select(value = { "{call MANT_EXAMEN_MEDICO_ODONTOLOGIA ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.indiceMasticacion, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.idMaloclusion, jdbcType = CHAR, mode = IN},"
            + "#{objeto.idHigiene, jdbcType = CHAR, mode = IN},"
            + "#{objeto.idProtesis, jdbcType = CHAR, mode = IN},"
            + "#{objeto.piezasFaltantes, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.piezasObturadas, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.piezasPorObturar, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.piezasPorExtraer, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.observacion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCampania, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idEstadoExamenMedico, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ExamenMedicoOdontologico> mantener(Parametro parametro);
}