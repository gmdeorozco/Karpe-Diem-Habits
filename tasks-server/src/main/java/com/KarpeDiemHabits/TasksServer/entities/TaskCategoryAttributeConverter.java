package com.KarpeDiemHabits.TasksServer.entities;

import javax.persistence.AttributeConverter;
import java.util.stream.*;

public class TaskCategoryAttributeConverter implements AttributeConverter< TaskCategory, String > {
    @Override
    public String convertToDatabaseColumn( TaskCategory category ) {
        if (category == null)
            return null;

        return category.getCode();
    }

    @Override
    public TaskCategory convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return Stream.of(TaskCategory.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
