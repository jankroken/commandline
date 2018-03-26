package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Indicate that this switch does not take any arguments, but is a toggle that is provided.
 * The toggle has a boolean value argument, which is intended to separate "on-switches" from
 * "off-switches" (for instance, --enable-logging, --disable-logging)
 *
 * @Param value A boolean argument that will be provided as argument to the associated setter
 */
public @interface Toggle {
    boolean value();
}
