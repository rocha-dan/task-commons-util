package br.com.rocha.dan.task.commons.util;

import java.text.Normalizer;
import static java.util.Objects.isNull;


public final class StringUtil {

	private StringUtil() {}

    /**
     * Descrição: Recebe uma string com(ou sem) pontos/traços e devolve somente
     * numeros.
     *
     * @param input String
     * @return retorno String
     * @author cgm9275
     */
    public static String onlyNumbers(String input) {
        if (nullOrEmpty(input))
            throw new IllegalArgumentException("Input can not be null or empty");

        return input.replaceAll("\\D", "");
    }

    public static boolean nullOrEmpty(String input) {
        return isNull(input) || input.trim().isEmpty();
    }

    public static boolean nonNullAndNotEmpty(String input) {
        return !nullOrEmpty(input);
    }

    public static String removeSpecialCharacters(String input){
        String response = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return response.replaceAll("[^a-z A-Z 0-9]", "");
    }

    public static String padRight(String input, int n) {
        String fmt = "%1$-" + n + "s";
        return String.format(fmt, input);
    }

    public static String padLeft(String input, int n) {
        String fmt = "%1$" + n + "s";
        return String.format(fmt, input);
    }

    public static String padNumber(String input, int n) {
        try {
            String fmt = "%0" + n + "d";
            return (input == null) ? null : String.format(fmt, Long.parseLong(input));
        } catch (IllegalArgumentException e) {
            return "";
        }
    }
}