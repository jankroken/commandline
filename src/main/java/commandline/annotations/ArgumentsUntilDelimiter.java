package commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

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
