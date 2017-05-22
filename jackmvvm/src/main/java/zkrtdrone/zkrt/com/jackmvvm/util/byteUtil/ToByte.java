package zkrtdrone.zkrt.com.jackmvvm.util.byteUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jack_xie on 17-5-17.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToByte {
    int order() default -1;
    String description() default "null";
}
