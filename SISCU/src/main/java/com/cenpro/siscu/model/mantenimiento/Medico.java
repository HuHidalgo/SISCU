package com.cenpro.siscu.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medico
{
    private String idTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String colegioMedico;
    private String registroNacionalEspecialidad;
    private boolean director;
    
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String idSexo;
    private String descripcionSexo;
}