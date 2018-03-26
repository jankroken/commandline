package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Specifies the shortcut switch for this option
 * (All options might be indicated by two switches, one long and one short). An example is "--verbose" and "-v"
 *
 * @Param The switch string
 */
public @interface ShortSwitch {
    String value();
}
