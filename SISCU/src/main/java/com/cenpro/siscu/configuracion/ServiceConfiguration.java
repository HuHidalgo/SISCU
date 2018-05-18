package com.cenpro.siscu.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.cenpro.siscu.service.impl", "com.cenpro.siscu.mapper" })
public class ServiceConfiguration
{

}