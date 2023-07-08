package com.example.para;

import java.util.Collection;
import java.util.function.Function;

public interface TaskParameterEntityInterface {
    String getParaName();

    Class<?> getParaType();

    Class<? extends Collection> getCollectionType();

    boolean isRequired();

    String getDefaultValue();

    Function<String, ?> getConverter();

}
