package com.KarpeDiemHabits.ChallengesServer.entities;

import javax.persistence.AttributeConverter;
import java.util.stream.*;

public class ChallengeCategoryAttributeConverter implements AttributeConverter< ChallengeCategory, String > {
    @Override
    public String convertToDatabaseColumn( ChallengeCategory category ) {
        if (category == null)
            return null;

        return category.getCode();
    }

    @Override
    public ChallengeCategory convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return Stream.of(ChallengeCategory.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
