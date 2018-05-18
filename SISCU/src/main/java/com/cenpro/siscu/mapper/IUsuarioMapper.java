package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.parametro.Parametro;
import com.cenpro.siscu.model.seguridad.Usuario;

public interface IUsuarioMapper extends IMantenibleMapper<Usuario>
{
	@Select(value = { "{call MANT_USUARIOS ( "
	        + "#{verbo, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idUsuario, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.contrasenia, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.activo, jdbcType = BIT, mode = IN},"
			+ "#{objeto.idTipoDocumento, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.numeroDocumento, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idPerfil, jdbcType = INTEGER, mode = IN},"
			+ "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
	@Options(statementType = StatementType.CALLABLE)
	public List<Usuario> mantener(Parametro parametro);
}