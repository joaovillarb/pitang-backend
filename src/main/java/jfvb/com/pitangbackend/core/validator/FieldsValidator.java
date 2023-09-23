package jfvb.com.pitangbackend.core.validator;

import jfvb.com.pitangbackend.core.annotations.DtoNotBlank;
import jfvb.com.pitangbackend.core.annotations.DtoNotNull;
import jfvb.com.pitangbackend.core.annotations.DtoPattern;
import jfvb.com.pitangbackend.core.exception.FieldAccessException;
import jfvb.com.pitangbackend.core.exception.InvalidFieldsException;
import jfvb.com.pitangbackend.core.exception.MissingFieldsException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Stream;

/**
 * Classe utilitária para validar campos de objetos com anotações @NotNull, @NotBlank e @Pattern.
 */
public final class FieldsValidator {

    private FieldsValidator() {

    }

    public static <T> void validate(T objectToValidate) {
        List<String> missingFields = getFieldAnnotations(objectToValidate, DtoNotNull.class)
                .filter(field -> Objects.isNull(getFieldValue(field, objectToValidate)))
                .map(Field::getName)
                .toList();

        if (!missingFields.isEmpty()) {
            String fields = String.join(", ", missingFields);
            String finalMessage = String.format("Missing Field(s): [%s]", fields);
            throw new MissingFieldsException(finalMessage);
        }

        List<String> invalidNotBlankFields = getFieldAnnotations(objectToValidate, DtoNotBlank.class)
                .filter(field -> isBlankString(getFieldValue(field, objectToValidate)))
                .map(Field::getName)
                .toList();

        List<String> invalidPatternFields = getFieldAnnotations(objectToValidate, DtoPattern.class)
                .filter(field -> {
                    Object fieldValue = getFieldValue(field, objectToValidate);
                    if (fieldValue instanceof String string) {
                        String patternRegex = field.getAnnotation(DtoPattern.class).value();
                        return !isValidPattern(string, patternRegex);
                    }
                    return false;
                })
                .map(Field::getName)
                .toList();

        List<String> invalidFields = removeDuplicates(invalidNotBlankFields, invalidPatternFields);

        if (!invalidFields.isEmpty()) {
            String fields = String.join(", ", invalidFields);
            String finalMessage = String.format("Invalid Field(s): [%s]", fields);
            throw new InvalidFieldsException(finalMessage);
        }
    }

    private static List<String> removeDuplicates(List<String> invalidFields, List<String> invalidPatternFields) {
        List<String> tempInvalidFields = new ArrayList<>(invalidFields);
        tempInvalidFields.removeAll(invalidPatternFields);
        tempInvalidFields.addAll(invalidPatternFields);
        return tempInvalidFields;
    }

    private static <T> Stream<Field> getFieldAnnotations(T object, Class<? extends Annotation> annotationClass) {
        return Stream.of(object.getClass().getDeclaredFields())
                .filter(field -> {
                    if (field.isAnnotationPresent(annotationClass)) {
                        field.trySetAccessible();
                        return true;
                    }
                    return false;
                });
    }

    private static <T> Object getFieldValue(Field field, T object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException(field.getName(), e);
        }
    }

    private static boolean isBlankString(Object value) {
        return value instanceof String string && string.isBlank();
    }

    private static boolean isValidPattern(String value, String patternRegex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}

