package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Indicates that the given value is a human readable description of the option. This is currently not used,
 * but might be used in the future, for instance for automatically generated help texts.
 *
 * @Param A textual description of the option
 */
public @interface Description {
    String value();
}
