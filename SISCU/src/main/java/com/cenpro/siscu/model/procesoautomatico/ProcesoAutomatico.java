package com.cenpro.siscu.model.procesoautomatico;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.utilitario.Regex;
import com.cenpro.siscu.validacion.IdCampania;
import com.cenpro.siscu.validacion.IdProcesoAutomatico;
import com.cenpro.siscu.validacion.MultitabDet;
import com.cenpro.siscu.validacion.grupo.accion.IActualizacion;
import com.cenpro.siscu.validacion.grupo.accion.IRegistro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoAutomatico
{
    @IdProcesoAutomatico(existe = true, groups = IActualizacion.class)
    @IdProcesoAutomatico(existe = false, message = "{Existe.ProcesoAutomatico.idProcesoAutomatico}", groups = IRegistro.class)
    private String idProcesoAutomatico;

    @NotNull(message = "{NotNull.ProcesoAutomatico.descripcion}")
    @NotBlank(message = "{NotBlank.ProcesoAutomatico.descripcion}")
    @Length(min = 3, max = 250, message = "{Length.ProcesoAutomatico.descripcion}")
    private String descripcion;

    @NotNull(message = "{NotNull.ProcesoAutomatico.ordenEjecucion}")
    @Range(min = 1, max = 99, message = "{Range.ProcesoAutomatico.ordenEjecucion}")
    private Integer ordenEjecucion;

    @NotNull(message = "{NotNull.ProcesoAutomatico.horaProgramada}")
    @Pattern(regexp = Regex.FORMATO_HHMM, message = "{Regex.ProcesoAutomatico.horaProgramada}")
    private String horaProgramada;

    @IdCampania(existe = true)
    private Integer idCampaniaEnvio;

    @MultitabDet(min = 1, max = 6, campoIdItem = "idDestinoEnvio", idTabla = MultiTablaUtil.TABLA_DESTINO_ENVIO)
    private String idDestinoEnvio;

    @NotNull(message = "{NotNull.ProcesoAutomatico.nombrePlantilla}")
    @NotBlank(message = "{NotBlank.ProcesoAutomatico.nombrePlantilla}")
    @Length(min = 3, max = 50, message = "{Length.ProcesoAutomatico.nombrePlantilla}")
    private String nombrePlantilla;

    @NotNull(message = "{NotNull.ProcesoAutomatico.fechaInicio}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaInicio;

    @NotNull(message = "{NotNull.ProcesoAutomatico.fechaFin}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaFin;

    @Length(min = 3, max = 250, message = "{Length.ProcesoAutomatico.textoEncabezado}")
    private String textoEncabezado;

    @Length(min = 3, max = 300, message = "{Length.ProcesoAutomatico.textoCuerpo}")
    private String textoCuerpo;

    @NotNull(message = "{NotNull.ProcesoAutomatico.codigoFacultadesEnvio}")
    @Length(min = 3, max = 300, message = "{Length.ProcesoAutomatico.codigoFacultadesEnvio}")
    private String codigoFacultadesEnvio;

    private boolean diario;
    private boolean activo;
    private int secuencia;
    private String descripcionCampania;
    private String descripcionDestinoEnvio;
    private String horaReprogramada;
}