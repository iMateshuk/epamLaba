package com.epam.esm.dao.util;

import java.util.Map;

public class QueryCreator {

    private final static String WHERE_RE = ".*WHERE.*";
    private final static String WHERE = "WHERE ";

    private final static String ORDER_RE = ".*ORDER.*";
    private final static String ORDER_BY = "ORDER BY ";
    private final static String SORT = "DESC";

    private final static String SORT_RE = "^SORT_.*";
    private final static String SEARCH_RE = "^SEARCH_.*";

    private final static String OR = "OR ";

    private final static String EMPTY = "";

    public static String buildSql(Map<String, String> requestedParameters) {

        StringBuilder sqlBuilder = new StringBuilder(GiftCertificateTagSQL.SELECT_MAIN_SEARCH.getSQL());
        StringBuilder searchBuilder = new StringBuilder();
        StringBuilder orderBuilder = new StringBuilder();

        requestedParameters.entrySet().stream().filter((parameter) -> parameter.getKey().matches(SORT_RE))
                .forEach((parameter) -> orderBuilder.append(appendOrder(orderBuilder, parameter.getKey(), parameter.getValue())));

        requestedParameters.entrySet().stream().filter((parameter) -> parameter.getKey().matches(SEARCH_RE))
                .forEach((parameter) -> searchBuilder.append(appendSearch(searchBuilder, parameter.getKey())));

        return sqlBuilder
                .append(searchBuilder)
                .append(GiftCertificateTagSQL.APPEND_GROUP.getSQL())
                .append(orderBuilder)
                .toString();
    }

    public static void removeKeyMatchSort(Map<String, String> requestedParameters) {

        requestedParameters.entrySet().removeIf(current -> current.getValue() == null || current.getKey().matches(SORT_RE));
    }


    private static String appendSearch(StringBuilder builder, String key) {

        String match = builder.toString().matches(WHERE_RE) ? OR : WHERE;

        return match + GiftCertificateTagSQL.valueOf(key).getSQL();
    }

    private static String appendOrder(StringBuilder builder, String key, String value) {

        String sql = ORDER_BY + GiftCertificateTagSQL.valueOf(key).getSQL();

        return builder.toString().matches(ORDER_RE) ? EMPTY : value.compareToIgnoreCase(SORT) == 0 ? sql + value : sql;
    }
}