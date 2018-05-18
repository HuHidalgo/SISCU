package com.cenpro.siscu.model.procesoautomatico;

import com.cenpro.siscu.utilitario.MimeTypeUtil;
import com.cenpro.siscu.utilitario.StringsUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Correo
{
    private Integer codigoDependencia;
    private Integer idCampania;
    private String idProcesoAutomatico;
    private String textoEncabezado;
    private String textoCuerpo;
    private String correoDestino;
    private String correoDestinoConCopia;
    private String correoEmisor;
    private String nombreDocumento;
    private byte[] documento;
    private String contentType;

    public String getNombreConExtension()
    {
        return StringsUtils.concatenarCadena(this.nombreDocumento, getExtension());
    }
    
    public String getExtension()
    {
        return MimeTypeUtil.obtenerExtensionPorMymetype(contentType);
    }
}