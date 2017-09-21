package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Indicates that the setter method handles arguments that are not arguments to a switch.
 * If this option is specified, LongSwitch and ShortSwitch can not be specified 
 */
public @interface LooseArguments {
}
