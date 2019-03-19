package com.spotahome.domain;

public class SortingFieldIsInvalid extends RuntimeException {

    public SortingFieldIsInvalid(String fieldName) {
        super(String.format("The sorting field [%s] is not valid", fieldName));
    }
}
