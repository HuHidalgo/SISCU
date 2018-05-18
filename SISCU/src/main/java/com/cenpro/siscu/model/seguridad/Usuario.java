package com.cenpro.siscu.model.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario 
{
	private String idUsuario;
	private String idTipoDocumento;
	private String descripcionTipoDocumento;
	private String numeroDocumento;
	private String contrasenia;
	private boolean activo;
	private int idPerfil;
	private String descripcionPerfil;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
}