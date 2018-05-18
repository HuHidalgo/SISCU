package com.cenpro.siscu.service;

import java.util.concurrent.Future;

import javax.mail.MessagingException;

import com.cenpro.siscu.model.procesoautomatico.Correo;

public interface IEnvioMailAsyncExecutor
{
    public Future<Void> enviarCorreo(Correo correo) throws MessagingException;
}