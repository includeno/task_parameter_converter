package com.example.para;

import lombok.Data;

import java.util.Collection;
import java.util.function.Function;

/**
 * 用于描述任务参数的实体类 拥有全部的参数信息
 */
@Data
public class TaskParameterEntityEntity implements TaskParameterEntityInterface {
    String paraName;
    Class<?> paraType;
    Class<? extends Collection> collectionType = Collection.class;
    boolean isRequired = true;
    String defaultValue = "";
    Function<String, ?> converter;
}
