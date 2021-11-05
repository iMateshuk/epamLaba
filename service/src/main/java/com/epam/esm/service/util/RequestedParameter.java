package com.epam.esm.service.util;

public enum RequestedParameter {

    SEARCH_TAG_NAME("tagName"),
    SEARCH_NAME("searchName"),
    SEARCH_DESCRIPTION("searchDescription"),
    SORT_NAME("sortName"),
    SORT_DATE("sortDate"),

    ;

    private String string;

    RequestedParameter(String string) {
        this.string = string;
    }

    public String getParameterKey() {
        return string;
    }

}
