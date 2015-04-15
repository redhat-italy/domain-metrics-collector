/**
 * 
 */
package com.redhat.it.customers.dmc.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;

/**
 * @author Andrea Battaglia
 *
 */
public final class CSVFunctions {

    /** The Constant INITIAL_STRING_SIZE. */
    public static final int INITIAL_STRING_SIZE = CSVWriter.INITIAL_STRING_SIZE;
    /**
     * The character used for escaping quotes.
     */
    public static final char DEFAULT_ESCAPE_CHARACTER = CSVWriter.DEFAULT_ESCAPE_CHARACTER;
    /**
     * The default separator to use if none is supplied to the constructor.
     */
    public static final char DEFAULT_SEPARATOR = CSVWriter.DEFAULT_SEPARATOR;
    /**
     * The default quote character to use if none is supplied to the
     * constructor.
     */
    public static final char DEFAULT_QUOTE_CHARACTER = CSVWriter.DEFAULT_QUOTE_CHARACTER;
    /**
     * The quote constant to use when you wish to suppress all quoting.
     */
    public static final char NO_QUOTE_CHARACTER = CSVWriter.NO_QUOTE_CHARACTER;
    /**
     * The escape constant to use when you wish to suppress all escaping.
     */
    public static final char NO_ESCAPE_CHARACTER = CSVWriter.NO_ESCAPE_CHARACTER;
    /**
     * Default line terminator uses platform encoding.
     */
    public static final String DEFAULT_LINE_END = CSVWriter.DEFAULT_LINE_END;

    private char separator;
    private char quotechar;
    private char escapechar;
    private String lineEnd;

    public static final CSVFunctions getInstance() {
        return getInstance(DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
    }

    public static final CSVFunctions getInstance(char separator,
            char quotechar, char escapechar, String lineEnd) {
        return new CSVFunctions(separator, quotechar, escapechar, lineEnd);
    }

    public CSVFunctions(char separator, char quotechar, char escapechar,
            String lineEnd) {
        super();
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
    }

//    public String prepareCSVLine(String[] data) {
//        StringWriter stringWriter = new StringWriter();
//        CSVWriter csvWriter = new CSVWriter(stringWriter,
//                CSVFunctions.DEFAULT_SEPARATOR,
//                CSVFunctions.DEFAULT_QUOTE_CHARACTER,
//                CSVFunctions.DEFAULT_ESCAPE_CHARACTER);
//        return stringWriter.toString();
//    }

    public String prepareCSVLine(String[] data) {
        boolean applyQuotesToAll = true;

        if (data == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        for (int i = 0; i < data.length; i++) {

            if (i != 0) {
                sb.append(separator);
            }

            String nextElement = data[i];

            if (nextElement == null) {
                continue;
            }

            Boolean stringContainsSpecialCharacters = stringContainsSpecialCharacters(nextElement);

            if ((applyQuotesToAll || stringContainsSpecialCharacters)
                    && quotechar != NO_QUOTE_CHARACTER) {
                sb.append(quotechar);
            }

            if (stringContainsSpecialCharacters) {
                sb.append(processLine(nextElement));
            } else {
                sb.append(nextElement);
            }

            if ((applyQuotesToAll || stringContainsSpecialCharacters)
                    && quotechar != NO_QUOTE_CHARACTER) {
                sb.append(quotechar);
            }
        }

        sb.append(lineEnd);

        return sb.toString();
    }

    /**
     * checks to see if the line contains special characters.
     * 
     * @param line
     *            - element of data to check for special characters.
     * @return true if the line contains the quote, escape, separator, newline
     *         or return.
     */
    private boolean stringContainsSpecialCharacters(String line) {
        return line.indexOf(quotechar) != -1 || line.indexOf(escapechar) != -1
                || line.indexOf(separator) != -1
                || line.contains(DEFAULT_LINE_END) || line.contains("\r");
    }

    /**
     * Processes all the characters in a line.
     * 
     * @param nextElement
     *            - element to process.
     * @return a StringBuilder with the elements data.
     */
    protected StringBuilder processLine(String nextElement) {
        StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
        for (int j = 0; j < nextElement.length(); j++) {
            char nextChar = nextElement.charAt(j);
            processCharacter(sb, nextChar);
        }

        return sb;
    }

    /**
     * Appends the character to the StringBuilder adding the escape character if
     * needed.
     * 
     * @param sb
     *            - StringBuffer holding the processed character.
     * @param nextChar
     *            - character to process
     */
    private void processCharacter(StringBuilder sb, char nextChar) {
        if (escapechar != NO_ESCAPE_CHARACTER
                && (nextChar == quotechar || nextChar == escapechar)) {
            sb.append(escapechar).append(nextChar);
        } else {
            sb.append(nextChar);
        }
    }

    public static <T> String[] beanToCsv(T bean) {
        String[] result = null;
        BeanToCsv<T> btc = null;
        MappingStrategy<T> beanStrategy = null;
        // Writer writer = new StringWriter();

        btc = new BeanToCsv<>();
        beanStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
        // writer = new StringWriter();
        try (Writer writer = new StringWriter()) {
            btc.write(beanStrategy, writer, Arrays.asList(bean));
            result = writer.toString().split(",");
        } catch (IOException e) {
        } finally {
            btc = null;
            beanStrategy = null;
        }
        return result;
    }
}
