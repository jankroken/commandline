package commandline.annotations;

//~--- JDK imports ------------------------------------------------------------

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Default {
    boolean value();
}


//~ Formatted by Jindent --- http://www.jindent.com
