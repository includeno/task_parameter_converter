package com.example.para;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class TaskParameterParser {
    public static Map<Field, TaskParameterEntityInterface> parse(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz can not be null");
        }
        //获取自定义converter
        TaskParameterConverter customConverter = clazz.getAnnotation(TaskParameterConverter.class);

        //获取clazz的全部field，子类field覆盖父类field
        List<Field> fields = getAllFileds(clazz);
        Map<Field, TaskParameterEntityInterface> map = new HashMap<>();
        for (Field field : fields) {
            //获取field的注解
            TaskParameter taskParameter = field.getAnnotation(TaskParameter.class);
            if (taskParameter == null) {
                continue;
            }
            //解析field的注解
            TaskParameterEntityEntity taskParameterEntity = parseField(field, taskParameter, customConverter);
            map.put(field, taskParameterEntity);
        }

        return map;
    }

    private static List<Field> getAllFileds(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static TaskParameterEntityEntity parseField(Field field, TaskParameter taskParameter, TaskParameterConverter converter) {
        TaskParameterEntityEntity taskParameterEntity = new TaskParameterEntityEntity();
        taskParameterEntity.setParaName(field.getName());
        taskParameterEntity.setParaType(field.getType());
        taskParameterEntity.setCollectionType(taskParameter.collectionType());
        taskParameterEntity.setRequired(taskParameter.isRequired());
        taskParameterEntity.setDefaultValue(taskParameter.defaultValue());
        taskParameterEntity.setConverter(getConverter(field.getType(), field.getName(), converter));
        return taskParameterEntity;
    }

    private static Function<String, ?> getConverter(Class<?> clazz, String paraName, TaskParameterConverter converter) {
        //基础类型转换
        switch (clazz.getName()) {
            case "java.lang.String":
                return Function.identity();
            case "java.lang.Integer":
                return Integer::parseInt;
            case "java.lang.Long":
                return Long::parseLong;
            case "java.lang.Double":
                return Double::parseDouble;
            case "java.lang.Float":
                return Float::parseFloat;
            case "java.lang.Boolean":
                return Boolean::parseBoolean;
            default:
                break;
        }
        //自定义转换
        if (converter != null) {
            Class<?> enumClass = converter.converter();
            if (isEnum(enumClass) && TaskParameterConverterInterface.class.isAssignableFrom(enumClass)) {
                try {
                    Object[] enumConstants = enumClass.getEnumConstants();
                    for (Object enumConstant : enumConstants) {
                        if (enumConstant instanceof TaskParameterConverterInterface) {
                            TaskParameterConverterInterface taskParameterConverterInterface = (TaskParameterConverterInterface) enumConstant;
                            Enum<?> enumConstant1 = (Enum<?>) enumConstant;
                            if (enumConstant1.name().equals(paraName)) {
                                return taskParameterConverterInterface.getConverter();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalArgumentException("converter must be enum and implements TaskParameterConverterInterface");
            }
        }
        return null;
    }

    private static boolean isEnum(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isEnum();
    }
}
