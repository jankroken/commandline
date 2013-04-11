package com.zerolegacy.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Specifies the long switch for this option
 * (All options might be indicated by two switches, one long and one short). An example is "--verbose" and "-v"
 * 
 * @Param The switch string
 */
public @interface LongSwitch {
	String value();
}
