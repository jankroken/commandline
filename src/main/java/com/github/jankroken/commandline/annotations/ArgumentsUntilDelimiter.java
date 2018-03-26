package com.github.jankroken.commandline.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)

/**
 * Indicates that this option takes any number of arguments, which is terminated either by the end of the
 * argument list, or by a delimiter. All values encountered until the delimiter will be taken as arguments,
 * even if they starts with a hyphen. The delimiter is not kept as part of the argument list.
 *
 * @Param the delimiter used to end the argument list
 */
public @interface ArgumentsUntilDelimiter {
    String value();
}
