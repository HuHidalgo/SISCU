package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoMedicinaGeneral;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IExamenMedicoMedicinaGeneralMapper
        extends IMantenibleMapper<ExamenMedicoMedicinaGeneral>
{
    @Select(value = { "{call MANT_EXAMEN_MEDICO_MEDICINA_GENERAL ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.embarazo, jdbcType = BIT, mode = IN},"
            + "#{objeto.comentario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCampania, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.idEstadoExamenMedico, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ExamenMedicoMedicinaGeneral> mantener(Parametro parametro);
}