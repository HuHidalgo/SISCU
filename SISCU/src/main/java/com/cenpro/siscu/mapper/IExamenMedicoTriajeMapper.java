package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoTriaje;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IExamenMedicoTriajeMapper extends IMantenibleMapper<ExamenMedicoTriaje>
{
    @Select(value = { "{call MANT_EXAMEN_MEDICO_TRIAJE ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.peso, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.talla, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.pulso, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.presionSistolica, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.presionDiastolica, jdbcType = SMALLINT, mode = IN},"
            + "#{objeto.fum, jdbcType = DATE, mode = IN},"
            + "#{objeto.idCampania, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idEstadoExamenMedico, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ExamenMedicoTriaje> mantener(Parametro parametro);
}
