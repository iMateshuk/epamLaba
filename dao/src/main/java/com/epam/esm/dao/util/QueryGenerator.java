package com.epam.esm.dao.util;

import java.util.Map;

public class QueryGenerator {

    private final static String WHERE_RE = ".*WHERE.*";
    private final static String ORDER_RE = ".*ORDER.*";
    private final static String SORT_RE = "^SORT_.*";
    private final static String SORT = "DESC";

    public static String sqlSearchWithParameters(Map<RequestedParameter, String> requestedParameters) {

        StringBuilder sqlBuilder = new StringBuilder(GiftCertificateTagSQL.SELECT_MAIN_SEARCH.getSQL());

        sqlBuilder.append(requestedParameters.get(RequestedParameter.SEARCH_TAG_NAME) != null
                ? appendSearch(sqlBuilder, GiftCertificateTagSQL.APPEND_W_TAG_NAME, GiftCertificateTagSQL.APPEND_AND_TAG_NAME) : "");

        sqlBuilder.append(requestedParameters.get(RequestedParameter.SEARCH_NAME) != null
                ? appendSearch(sqlBuilder, GiftCertificateTagSQL.APPEND_W_SEARCH_NAME, GiftCertificateTagSQL.APPEND_AND_SEARCH_NAME) : "");

        sqlBuilder.append(requestedParameters.get(RequestedParameter.SEARCH_DESCRIPTION) != null
                ? appendSearch(sqlBuilder, GiftCertificateTagSQL.APPEND_W_SEARCH_DESCRIPTION, GiftCertificateTagSQL.APPEND_AND_SEARCH_DESCRIPTION) : "");

        sqlBuilder.append(GiftCertificateTagSQL.APPEND_GROUP.getSQL());

        sqlBuilder.append(requestedParameters.get(RequestedParameter.SORT_DATE) != null
                ? appendOrder(sqlBuilder, GiftCertificateTagSQL.APPEND_ORDER_DATE, requestedParameters.get(RequestedParameter.SORT_DATE)) : "");

        sqlBuilder.append(requestedParameters.get(RequestedParameter.SORT_NAME) != null
                ? appendOrder(sqlBuilder, GiftCertificateTagSQL.APPEND_ORDER_NAME, requestedParameters.get(RequestedParameter.SORT_NAME)) : "");

        return sqlBuilder.toString();
    }

    public static void cleanMap(Map<RequestedParameter, String> requestedParameters) {

        requestedParameters.entrySet().removeIf(current -> current.getValue() == null || current.getKey().toString().matches(SORT_RE));
    }

    private static String appendSearch(StringBuilder builder, GiftCertificateTagSQL whereSql, GiftCertificateTagSQL andSql) {

        return builder.toString().matches(WHERE_RE) ? andSql.getSQL() : whereSql.getSQL();
    }

    private static String appendOrder(StringBuilder builder, GiftCertificateTagSQL orderSql, String sortParam) {

        return builder.toString().matches(ORDER_RE) ? "" : sortParam.compareToIgnoreCase(SORT) == 0
                ? (orderSql.getSQL() +" " + sortParam) : orderSql.getSQL();
    }
}