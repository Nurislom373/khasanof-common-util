package org.khasanof.pluralizer;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 7/20/2024 3:26 PM
 */
public abstract class Pluralizer {

    /**
     *
     * @param singular
     * @return
     */
    public static String pluralize(String singular) {
        if (singular == null || singular.isEmpty()) return singular;

        int len = singular.length();
        char lastChar = singular.charAt(len - 1);
        char secondLastChar = len > 1 ? singular.charAt(len - 2) : ' ';

        if (lastChar == 'y' && !"AEIOUaeiou".contains("" + secondLastChar)) {
            return singular.substring(0, len - 1) + "ies";
        }
        if ("sxz".indexOf(lastChar) != -1 || singular.endsWith("sh") || singular.endsWith("ch")) {
            return singular + "es";
        }
        return singular + "s";
    }
}
