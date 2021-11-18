package com.epam.esm.util;

import com.epam.esm.exception.ControllerException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ControllerValidator {

    private static final int MIN_VALUE = 0;
    private static final int MIN_LEN_NAME = 3;

    public void validateValueOfName(String value) throws ControllerException {

        List<String> exceptions = new ArrayList<>();
        try {
            isStringNullOrEmpty(exceptions, value);
        } catch (ControllerException e) {
            throw new ControllerException(
                    MessageFormat.format("Value of {1} not null or length less {0} {2}",
                            MIN_LEN_NAME, exceptions, "(id:101)"));
        }
    }

    public void validateValueOfId(int value) throws ControllerException {

        List<String> exceptions = new ArrayList<>();
        try {
            isPositiveInteger(exceptions, value);
        } catch (ControllerException e) {
            throw new ControllerException(
                    MessageFormat.format("Id:{1}, not less or equal {0} {2}",
                            MIN_VALUE, exceptions, "(id:102)"));
        }
    }

    private void isPositiveInteger(List<String> exceptions, Integer... ints) throws ControllerException {

        Stream.of(ints).forEach((item) -> {
            if (item <= MIN_VALUE) {
                exceptions.add(item.toString());
            }
        });
        checkListOfExceptions(exceptions);
    }

    private void isStringNullOrEmpty(List<String> exceptions, String... strings) throws ControllerException {

        Stream.of(strings).forEach(string -> {
            if (string == null || string.isBlank() || (string.length() < MIN_LEN_NAME)) {
                exceptions.add(string);
            }
        });
        checkListOfExceptions(exceptions);
    }


    private void checkListOfExceptions(List<String> exceptions) throws ControllerException {

        if (!exceptions.isEmpty()) {
            throw new ControllerException();
        }
    }

}
