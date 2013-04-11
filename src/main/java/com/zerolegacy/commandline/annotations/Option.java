package com.zerolegacy.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Indicates that the method is a setter for an option argument. Further annotations are needed to fully
 * specify an option.
 */
public @interface Option {
}
