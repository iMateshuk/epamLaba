package com.epam.esm.service.util;

import com.epam.esm.service.dto.GiftCertificateDTO;

import java.util.List;
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

        isPositiveInteger(requestGiftCertificateDTO.getId(), requestGiftCertificateDTO.getDuration());

        isPositiveFloat(requestGiftCertificateDTO.getPrice());

        stringValidator(requestGiftCertificateDTO.getName(), requestGiftCertificateDTO.getDescription());
    }

    public static void listEmpty(List<?> list) {

        if (list.isEmpty()) {

            throw new NoSuchElementException(list + " is empty");
        }
    }

    public static void isPositiveInteger(Integer... ints) {

        for (Integer theInt : ints) {

            if (theInt == null || theInt < MIN_VALUE) {

                throw new IllegalArgumentException(theInt + " null or less then 0");
            }
        }
    }

    public static void isPositiveFloat(Float... floats) {

        for (Float theFloat : floats) {

            if (theFloat == null || theFloat < MIN_VALUE) {

                throw new IllegalArgumentException(theFloat + " null or less then 0");
            }
        }
    }

    public static void stringValidator(String... strings) {

        for (String string : strings) {

            if (stringNullOrEmpty(string) || string.length() < MIN_LEN) {

                throw new IllegalArgumentException(string + "null or less the 0");
            }
        }
    }

    public static boolean stringNullOrEmpty(String string) {

        return string == null || string.isBlank() || string.isEmpty();
    }
}
