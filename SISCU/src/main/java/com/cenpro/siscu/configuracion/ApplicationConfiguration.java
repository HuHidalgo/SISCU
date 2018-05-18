package com.cenpro.siscu.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ComponentScan(basePackages = { "com.cenpro.siscu.configuracion"})
public class ApplicationConfiguration
{

}