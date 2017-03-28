package com.demo.writer_pipeline.core;

import com.demo.writer_pipeline.exceptions.ParsingException;
import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.removeAll;

@Log
@Component
class WikiCodeParser {

    private static final String START_OF_MULTIPLE_CODES_SUBSTRING = "|+";
    private static final String END_OF_MULTIPLE_CODES_SUBSTRING = "]";

    private static final String CODE_PARTS_SEPARATORS = "[\\s/]";
    private static final String PARSING_ERROR = "unable to parse country token with multiple codes";
    private static final String CHARACTERS_TO_REMOVE = "[x/\\s\\]\\[\\+]";

    private static final String SINGLE_CODE_REGEX_SIMPLE = "^\\[\\[\\+([0-9x\\s]{1,})\\]\\]";
    private static final String SINGLE_CODE_REGEX_COMPLEX = "\\[\\[(.*\\|)\\+([0-9x\\s]{1,})\\]\\]";
    private static final String MULTIPLE_CODES_REGEX = "^\\[\\[.*/\\s.*\\]\\].*";

    private static final Pattern SINGLE_CODE_SIMPLE = Pattern.compile(SINGLE_CODE_REGEX_SIMPLE);
    private static final Pattern SINGLE_CODE_COMPLEX = Pattern.compile(SINGLE_CODE_REGEX_COMPLEX);
    private static final Pattern MULTIPLE_CODES = Pattern.compile(MULTIPLE_CODES_REGEX);

    List<String> apply(String token) {
        List<String> parsedCodes = new ArrayList<>();
        String singleCode = parseSingleCode(token);
        if (!singleCode.isEmpty()) {
            parsedCodes.add(singleCode);
        } else {
            parsedCodes = parseMultipleCodes(token);
        }
        return ImmutableList.copyOf(parsedCodes);
    }

    private List<String> parseMultipleCodes(String token) {
        Matcher codes = MULTIPLE_CODES.matcher(token);
        if (!codes.matches()) {
            throw new ParsingException(PARSING_ERROR);
        }
        List<String> codeParts = extractCodeParts(token);
        return buildCodes(codeParts);
    }

    private List<String> extractCodeParts(String token) {
        int start = token.indexOf(START_OF_MULTIPLE_CODES_SUBSTRING);
        int finish = token.indexOf(END_OF_MULTIPLE_CODES_SUBSTRING);
        String codeString = token.substring(start + START_OF_MULTIPLE_CODES_SUBSTRING.length(), finish);
        List<String> codeParts = asList(codeString.split(CODE_PARTS_SEPARATORS));
        if (codeParts.isEmpty()) {
            throw new ParsingException(PARSING_ERROR);
        }
        return codeParts.stream()
                .filter(part -> !part.isEmpty())
                .map(part -> part.replaceAll(CHARACTERS_TO_REMOVE, EMPTY))
                .collect(toList());
    }

    private List<String> buildCodes(List<String> codeParts) {
        String head = codeParts.get(0);
        List<String> tail = codeParts.subList(1, codeParts.size());
        return tail.stream()
                .map(head::concat)
                .collect(toList());
    }

    private String parseSingleCode(String token) {
        Matcher simpleCode = SINGLE_CODE_SIMPLE.matcher(token);
        Matcher complexCode = SINGLE_CODE_COMPLEX.matcher(token);
        String parsedCode = simpleCode.find() ? simpleCode.group() : complexCode.find() ? complexCode.group(2) : EMPTY;
        return removeAll(parsedCode, CHARACTERS_TO_REMOVE);
    }
}
