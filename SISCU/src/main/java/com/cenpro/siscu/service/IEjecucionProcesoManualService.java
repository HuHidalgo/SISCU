package com.cenpro.siscu.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import com.cenpro.siscu.model.criterio.CriterioBusquedaEnvioCorreo;

public interface IEjecucionProcesoManualService
{
    public void ejecutarProcesoManual(CriterioBusquedaEnvioCorreo criterioBusquedaEnvioCorreo)
            throws IOException, MessagingException, InterruptedException, ExecutionException;
}