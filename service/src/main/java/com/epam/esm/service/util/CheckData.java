package com.epam.esm.service.util;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckData {

    private static final int MIN_VALUE = 0;
    private static final int MIN_LEN = 3;
    private static final String STRING_RE = "[\\w+( )?]+";

    public static void giftCertificate(GiftCertificateDTO requestGiftCertificateDTO) {

        isPositiveInteger(requestGiftCertificateDTO.getDuration());

        isPositiveFloat(requestGiftCertificateDTO.getPrice());

        tagNameLengthValidator(requestGiftCertificateDTO.getName(), requestGiftCertificateDTO.getDescription());

        tagNameValidator(requestGiftCertificateDTO.getName());
    }

    public static void giftCertificatePartialField(GiftCertificateDTO requestGiftCertificateDTO) {

        int id = requestGiftCertificateDTO.getId();

        isZeroInteger(id);

        isPositiveInteger(id, requestGiftCertificateDTO.getDuration());

        isPositiveFloat(requestGiftCertificateDTO.getPrice());

        String name = requestGiftCertificateDTO.getName();

        if (!stringNullOrEmpty(name)) {

            tagNameLengthValidator(name);
            tagNameValidator(name);
        }

        String description = requestGiftCertificateDTO.getDescription();

        if (!stringNullOrEmpty(description)) {

            tagNameLengthValidator(description);
        }

    }

    public static void listEmpty(List<?> list) {

        if (list.isEmpty()) {

            throw new IllegalArgumentException(list.getClass().getSimpleName() + " is empty");
        }
    }

    public static boolean isListEmpty(List<?> list) {

        return list.isEmpty();
    }

    public static void mapEmpty(Map<?, ?> map) {

        if (map.isEmpty()) {

            throw new IllegalArgumentException(map.getClass().getSimpleName() + " is empty");
        }
    }

    public static void isPositiveInteger(Integer... ints) {

        Stream.of(ints).forEach((item) -> {

            if (item < MIN_VALUE) {

                throw new IllegalArgumentException(item + " less then zero");
            }
        });
    }

    public static void isZeroInteger(Integer... ints) {

        Stream.of(ints).forEach((item) -> {

            if (item == MIN_VALUE) {

                throw new IllegalArgumentException("equal zero");
            }
        });
    }

    public static void isPositiveFloat(Float... floats) {

        Stream.of(floats).forEach((item) -> {

            if (item < MIN_VALUE) {

                throw new IllegalArgumentException(item + " less zero");
            }
        });
    }

    public static void tagNameLengthValidator(String... strings) {

        Stream.of(strings).forEach((string) -> {

            if (string.length() < MIN_LEN) {

                throw new IllegalArgumentException(string + " null or length less the " + MIN_LEN);
            }
        });
    }

    public static void tagNameValidator(String... strings) {

        Stream.of(strings).forEach((string) -> {

            if (!string.matches(STRING_RE)) {

                throw new IllegalArgumentException(string + " invalid in tagNameValidator");
            }
        });
    }

    public static Map<String, String> createMapParameter(Map<String, String> allRequestParams) {

        return Arrays.stream(RequestedParameter.class.getEnumConstants())
                .filter((parameter) -> allRequestParams.get(parameter.getParameterKey()) != null)
                .collect(Collectors.toMap(RequestedParameter::toString, (parameter) -> (allRequestParams.get(parameter.getParameterKey()))));
    }

    public static boolean stringNullOrEmpty(String... strings) {

        return Stream.of(strings).allMatch(string -> string == null || string.isBlank() || string.isEmpty());
    }
}
