package commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Indicates that the given value is a human readable description of the option. This is currently not used,
 * but might be used in the future, for instance for automatically generated help texts.
 * 
 * @Param A textual descripton of the option
 */
public @interface Description {
    String value();
}
