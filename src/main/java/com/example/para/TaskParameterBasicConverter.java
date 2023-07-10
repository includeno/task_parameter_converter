package com.example.para;

import java.time.LocalDate;
import java.util.function.Function;

//task基础参数 类型转换器
public enum TaskParameterBasicConverter implements TaskParameterConverterInterface {
    date(LocalDate::parse, LocalDate.class),
    ;

    private final Function<String, ?> converter;
    private final Class<?> clazz;

    <T> TaskParameterBasicConverter(Function<String, T> converter, Class<T> clazz) {
        this.converter = converter;
        this.clazz = clazz;
    }

    @Override
    public Function<String, ?> getConverter() {
        return converter;
    }
}
