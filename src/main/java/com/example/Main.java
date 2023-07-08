package com.example;

import com.example.para.*;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

public class Main {

    @Data
    @TaskParameterConverter(converter = TaskDemoParameterConverterEnum.class)
    public static class TaskDemoParameter {
        @TaskParameter(paraType = String.class)
        LocalDate date;
    }

    @Getter
    public enum TaskDemoParameterConverterEnum implements TaskParameterConverterInterface {
        date(LocalDate::parse),
        ;

        private final Function<String, ?> converter;

        TaskDemoParameterConverterEnum(Function<String, ?> converter) {
            this.converter = converter;
        }
    }

    public static void main(String[] args) {
        Map<Field, TaskParameterEntityInterface> map = TaskParameterParser.parse(TaskDemoParameter.class);
        System.out.println(map);
    }
}
