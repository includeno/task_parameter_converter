package com.example.para;

import lombok.Data;

import java.util.Collection;
import java.util.function.Function;

@Data
public class TaskParameterConverterEntity {
    private String paraName;
    private Class<?> paraType;
    private Class<? extends Collection> collectionType = Collection.class;
    private Function<String, ?> converter;
}
