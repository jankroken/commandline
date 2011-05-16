package commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Indicates that the setter method handles arguments that are not arguments to a switch.
 * If this option is specified, LongSwitch and ShortSwitch can not be specified 
 */
public @interface LooseArguments {
}
