package com.example.para;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TaskParameter {
    String paraName() default "";

    Class<?> paraType();

    Class<? extends Collection> collectionType() default Collection.class;

    boolean isRequired() default true;

    String defaultValue() default "";
}
