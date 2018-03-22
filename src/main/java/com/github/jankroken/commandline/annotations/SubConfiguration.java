package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Specifies a complex argument type, represented by a full annotation option specification
 * 
 * @param value The class that should be instantiated and used to parse the option value
 */
public @interface SubConfiguration {
    Class<?> value();
}
