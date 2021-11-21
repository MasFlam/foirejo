package com.masflam.monerobarter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Documented
@Produces(MediaType.APPLICATION_JSON)
@Retention(RetentionPolicy.RUNTIME)
@Stereotype
@Target(ElementType.TYPE)
public @interface RestResource {
}
