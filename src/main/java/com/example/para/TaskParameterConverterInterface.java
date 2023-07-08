package com.example.para;

import java.util.function.Function;

public interface TaskParameterConverterInterface {
    Function<String, ?> getConverter();
}
