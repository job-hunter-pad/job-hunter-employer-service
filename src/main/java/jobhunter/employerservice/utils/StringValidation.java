package jobhunter.employerservice.utils;

public class StringValidation {
    public static boolean IsStringEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    public static boolean IsStringNotEmpty(String str) {
        return !IsStringEmpty(str);
    }
}
