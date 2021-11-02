package com.epam.esm.service.util;

import com.epam.esm.dao.util.RequestedParameter;
import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CheckData {

    private static final int MIN_VALUE = 0;
    private static final int MIN_LEN = 3;

    public static void giftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {

        isPositiveInteger(requestGiftCertificateDTO.getDuration());

        isPositiveFloat(requestGiftCertificateDTO.getPrice());

        stringValidator(requestGiftCertificateDTO.getName(), requestGiftCertificateDTO.getDescription());
    }

    public static void giftCertificatePartialField(GiftCertificateDTO requestGiftCertificateDTO) {

        int id = requestGiftCertificateDTO.getId();

        isZeroInteger(id);

        isPositiveInteger(id, requestGiftCertificateDTO.getDuration());

        isPositiveFloat(requestGiftCertificateDTO.getPrice());

        String name = requestGiftCertificateDTO.getName();

        if (stringNullOrEmpty(name)) {

            stringValidator(name);
        }

        String description = requestGiftCertificateDTO.getDescription();

        if (stringNullOrEmpty(description)) {

            stringValidator(description);
        }

    }

    public static void listEmpty(List<?> list) {

        if (list.isEmpty()) {

            throw new NoSuchElementException(list.getClass().getName() + " is empty");
        }
    }

    public static void mapEmpty(Map<?, ?> map) {

        if (map.isEmpty()) {

            throw new NoSuchElementException(map.getClass().getName() + " is empty");
        }
    }

    public static void isPositiveInteger(Integer... ints) {

        for (Integer theInt : ints) {

            if (theInt < MIN_VALUE) {

                throw new IllegalArgumentException(theInt + " less then zero");
            }
        }
    }

    public static void isZeroInteger(Integer... ints) {

        for (Integer theInt : ints) {

            if (theInt == MIN_VALUE) {

                throw new IllegalArgumentException(theInt + " less then zero");
            }
        }
    }

    public static void isPositiveFloat(Float... floats) {

        for (Float theFloat : floats) {

            if (theFloat < MIN_VALUE) {

                throw new IllegalArgumentException(theFloat + " null, less or eq zero");
            }
        }
    }

    public static void stringValidator(String... strings) {

        for (String string : strings) {

            if (string.length() < MIN_LEN) {

                throw new IllegalArgumentException(string + "null or less the 0");
            }
        }
    }

    public static Map<RequestedParameter, String> createMapParameter(Map<String, String> allRequestParams) {

        Map<RequestedParameter, String> allParameters = new LinkedHashMap<>();

        for (RequestedParameter parameter : RequestedParameter.class.getEnumConstants()) {

            allParameters.put(parameter, allRequestParams.get(parameter.getParameterKey()));
        }

        return allParameters;
    }

    private static boolean stringNullOrEmpty(String string) {

        return string != null && !string.isBlank() && !string.isEmpty();
    }
}
