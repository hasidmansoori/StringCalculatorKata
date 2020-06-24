package com.hasid.tddkata

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator
{
    private static final String BETWEEN_SQUARE_BRACES = "\\[(.*?)\\]";
    private static final String DEFAULT_SEPARATOR = "[,\n]";

    public int add(final String input) {
        if (!input.trim().isEmpty()) {
            final String separator = getSeparator(input);
            final String numbers = preProcessInput(input);

            final List<Integer> parsedNumbers = parseToIntegers(numbers, separator);

            validateNumbers(parsedNumbers);

            return addNumbers(parsedNumbers);
        }
        return 0;
    }

    private String preProcessInput(String input) {
        // remove separator config line if present
        return input.startsWith("//") ? 
            input.substring(input.indexOf('\n')) : input;
    }

    private String getSeparator(String input) {
        if (input.startsWith("//")) {
            // custom separator is passed as first line
            final String separatorConfig = input.substring(2, input.indexOf('\n'));

            final List<String> separatorsInBraces = getAllSeparatorsInBraces(separatorConfig);
            if (separatorsInBraces.isEmpty()) {
                // separator is a single character without braces
                return Pattern.quote(separatorConfig);
            }

            // join all separators with OR condition
            return String.join("|", separatorsInBraces);
        }
        return DEFAULT_SEPARATOR;
    }

    private List<String> getAllSeparatorsInBraces(final String separatorConfig) {
        final Matcher matcher = Pattern.compile(BETWEEN_SQUARE_BRACES).matcher(separatorConfig);
        final List<String> separators = new ArrayList<>();

        // put each separator in its own group
        while (matcher.find()) {
            separators.add("(" + Pattern.quote(matcher.group(1)) + ")");
        }
        return separators;
    }

    private int addNumbers(final List<Integer> parsedNumbers) {
        return parsedNumbers
            .stream()
            .filter(i -> i <= 1000)
            .collect(Collectors.summingInt(Integer::valueOf));
    }

    private void validateNumbers(final List<Integer> parsedNumbers) {
        final List<Integer> negativeNumbers =
            parsedNumbers.stream().filter(i -> i < 0).collect(Collectors.toList());
        
        if (negativeNumbers.size() > 0) {
            throw new IllegalArgumentException("negatives not allowed, " + negativeNumbers);
        }
    }

    private List<Integer> parseToIntegers(String input, String separator) {
        final String[] separatedNums = input.split(separator, -1);
        final List<Integer> allNumbers =
         Arrays.stream(separatedNums)
                .map(n -> n.trim())
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        return allNumbers;
    }

}