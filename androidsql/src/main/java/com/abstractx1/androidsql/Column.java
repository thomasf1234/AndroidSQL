package com.abstractx1.androidsql;

/**
 * Created by tfisher on 22/01/2017.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // on class level
@Retention(RetentionPolicy.RUNTIME) // not needed at runtime
public @interface Column {
    String name();
    boolean readOnly() default false;
}
