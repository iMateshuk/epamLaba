package com.epam.esm.controller;

import com.epam.esm.exception.ControllerException;
import com.epam.esm.service.dto.GiftCertificateDTO;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ControllerValidator {

    private static final int MIN_VALUE = 0;
    private static final int MIN_LEN_NAME = 3;

    private static final String ID_MESSAGE = "ID:{0} less or equal {1}. ";
    private static final String DURATION_MESSAGE = "Duration:{0} less or equal {1}. ";
    private static final String NAME_DESCRIPTION_MESSAGE = "Name or Description:{0} null or length less {1}. ";
    private static final String FLOAT_MESSAGE = "Float:{0} less or equal {1}. ";
    private static final String TAGS_MESSAGE = "Tags is empty. ";

    public void validateValueOfTagName(String tagName) throws ControllerException {
        List<String> exceptions = new ArrayList<>();
        try {
            isStringNullOrEmpty(exceptions, tagName);
            checkListOfExceptions(exceptions);
        } catch (ControllerException e) {
            throw new ControllerException(
                    MessageFormat.format("Value of {0} null or length less {1}. {2}",
                            exceptions, MIN_LEN_NAME, "(id:101)"));
        }
    }

    public void validateValueOfId(int value) throws ControllerException {
        List<String> exceptions = new ArrayList<>();
        try {
            isPositiveInteger(exceptions, value);
            checkListOfExceptions(exceptions);
        } catch (ControllerException e) {
            throw new ControllerException(
                    MessageFormat.format("Id:{0} less or equal {1}. {2}",
                            exceptions, MIN_VALUE, "(id:102)"));
        }
    }

    public void validateForCreationGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) throws ControllerException {
        StringBuilder messageBuilder = new StringBuilder();
        List<String> exceptions = new ArrayList<>();

        isPositiveInteger(exceptions, giftCertificateDTO.getDuration());
        checkExceptionsAndBuildMessage(exceptions, DURATION_MESSAGE, messageBuilder, MIN_VALUE);

        isStringNullOrEmpty(exceptions, giftCertificateDTO.getName(), giftCertificateDTO.getDescription());
        checkExceptionsAndBuildMessage(exceptions, NAME_DESCRIPTION_MESSAGE, messageBuilder, MIN_LEN_NAME);

        isPositiveFloat(exceptions, giftCertificateDTO.getPrice());
        checkExceptionsAndBuildMessage(exceptions, FLOAT_MESSAGE, messageBuilder, MIN_VALUE);

        if (giftCertificateDTO.getTags().isEmpty()) {
            messageBuilder.append(TAGS_MESSAGE);
        }

        if (!messageBuilder.toString().isEmpty()) {
            throw new ControllerException(messageBuilder.append("(id:103)").toString());
        }
    }

    public void validateForUpdateGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) throws ControllerException {
        StringBuilder messageBuilder = new StringBuilder();
        List<String> exceptions = new ArrayList<>();

        isPositiveInteger(exceptions, giftCertificateDTO.getId());
        checkExceptionsAndBuildMessage(exceptions, ID_MESSAGE, messageBuilder, MIN_VALUE);

        if (giftCertificateDTO.getDuration() < MIN_VALUE) {
            exceptions.add(String.valueOf(giftCertificateDTO.getDuration()));
            checkExceptionsAndBuildMessage(exceptions, DURATION_MESSAGE, messageBuilder, MIN_VALUE);
        }

        if (giftCertificateDTO.getPrice() < MIN_VALUE) {
            exceptions.add(String.valueOf(giftCertificateDTO.getPrice()));
            checkExceptionsAndBuildMessage(exceptions, FLOAT_MESSAGE, messageBuilder, MIN_VALUE);
        }

        if (giftCertificateDTO.getName() != null && giftCertificateDTO.getName().length() < MIN_LEN_NAME) {
            exceptions.add(giftCertificateDTO.getName());
        }

        if (giftCertificateDTO.getDescription() != null && giftCertificateDTO.getDescription().length() < MIN_LEN_NAME) {
            exceptions.add(giftCertificateDTO.getDescription());
        }

        if (!exceptions.isEmpty()) {
            checkExceptionsAndBuildMessage(exceptions, NAME_DESCRIPTION_MESSAGE, messageBuilder, MIN_LEN_NAME);
        }

        if (!messageBuilder.toString().isEmpty()) {
            throw new ControllerException(messageBuilder.append("(id:104)").toString());
        }
    }

    private void isPositiveInteger(List<String> exceptions, Integer... ints) {
        Stream.of(ints).forEach((item) -> {
            if (item <= MIN_VALUE) {
                exceptions.add(item.toString());
            }
        });
    }

    private void isStringNullOrEmpty(List<String> exceptions, String... strings) {
        Stream.of(strings).forEach(string -> {
            if (string == null || string.isBlank() || (string.length() < MIN_LEN_NAME)) {
                exceptions.add(string);
            }
        });
    }

    private void isPositiveFloat(List<String> exceptions, Float... floats) {
        Stream.of(floats).forEach((item) -> {
            if (item <= MIN_VALUE) {
                exceptions.add(item.toString());
            }
        });
    }


    private void checkListOfExceptions(List<String> exceptions) throws ControllerException {
        if (!exceptions.isEmpty()) {
            throw new ControllerException();
        }
    }

    private void checkExceptionsAndBuildMessage(List<String> exceptions, String message, StringBuilder messageBuilder, int value) {
        try {
            checkListOfExceptions(exceptions);
        } catch (ControllerException ignore) {
            messageBuilder.append(MessageFormat.format(message, exceptions, value));
            exceptions.clear();
        }
    }

}
