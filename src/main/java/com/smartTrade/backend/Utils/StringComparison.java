package com.smartTrade.backend.Utils;

import java.text.Normalizer;
import java.util.Arrays;

public class StringComparison {

    public static int calculate(String s1, String s2) {

        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    );
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    public static boolean areSimilar(String s1, String s2) {
        int distance = calculate(s1, s2);
        return distance <= 3;
    }

    public static String quitarAcentos(String palabraConAcentos) {
        String palabraSinAcentos = Normalizer.normalize(palabraConAcentos, Normalizer.Form.NFD);
        palabraSinAcentos = palabraSinAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return palabraSinAcentos;
    }

}
