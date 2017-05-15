package zkrtdrone.zkrt.com.jackmvvm.util.byteUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jack_xie on 17-5-15.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface JByte {
    /**
     * @return
     * 成员变量的顺序 递增
     */
    int index() default 0;

    /**
     * @return
     * list/array 的总长度
     * String 的字节数
     */
    int lenght() default 0;
}
