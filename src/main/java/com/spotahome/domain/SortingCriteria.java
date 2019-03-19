package com.spotahome.domain;

import java.util.Arrays;

public final class SortingCriteria {

    public final String fieldName;

    private SortingCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public static SortingCriteria create(String fieldName) {
        assertFieldIsValid(fieldName);

        return new SortingCriteria(fieldName);
    }

    private static void assertFieldIsValid(String fieldName) {
        Arrays.stream(Home.class.getDeclaredFields())
                .map(field -> field.getName())
                .filter(currentFieldName -> currentFieldName.equals(fieldName)).findAny()
                .orElseThrow(() -> new SortingFieldIsInvalid(fieldName));
    }
}
