package com.github.jankroken.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Indicates that this option takes all available arguments, ended by either the end of the argument list,
 * or by another switch.
 */
public @interface AllAvailableArguments {
}
