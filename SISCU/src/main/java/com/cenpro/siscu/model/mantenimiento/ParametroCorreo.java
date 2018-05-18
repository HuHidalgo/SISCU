package com.cenpro.siscu.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroCorreo
{
    private Integer idParametro;

    @NotNull(message = "{NotNull.ParametroCorreo.tokenUrl}")
    @NotBlank(message = "{NotBlank.ParametroCorreo.tokenUrl}")
    @Length(min = 3, max = 250, message = "{Length.ParametroCorreo.tokenUrl}")
    private String tokenUrl;
    
    @NotNull(message = "{NotNull.ParametroCorreo.oAuthClienteId}")
    @NotBlank(message = "{NotBlank.ParametroCorreo.oAuthClienteId}")
    @Length(min = 3, max = 250, message = "{Length.ParametroCorreo.oAuthClienteId}")
    private String oAuthClienteId;
    
    @NotNull(message = "{NotNull.ParametroCorreo.oAuthSecretId}")
    @NotBlank(message = "{NotBlank.ParametroCorreo.oAuthSecretId}")
    @Length(min = 3, max = 250, message = "{Length.ParametroCorreo.oAuthSecretId}")
    private String oAuthSecretId;
    
    @NotNull(message = "{NotNull.ParametroCorreo.refreshToken}")
    @NotBlank(message = "{NotBlank.ParametroCorreo.refreshToken}")
    @Length(min = 3, max = 250, message = "{Length.ParametroCorreo.refreshToken}")
    private String refreshToken;
    
    @NotNull(message = "{NotNull.ParametroCorreo.accessToken}")
    @NotBlank(message = "{NotBlank.ParametroCorreo.accessToken}")
    @Length(min = 3, max = 250, message = "{Length.ParametroCorreo.accessToken}")
    private String accessToken;
}