package readability;


public class Regex {
    static String characters = "\s";

    static String syllables = "\\b[^aeyuioAEYUIO\\s]+[aeyuioAEYUIO]\\b" + "|" +
            "[aeyuioAEYUIO]*[eE](?!\\b)[aeyuioAEYUIO]*" + "|" +
            "[ayuioAYUIO]+" + "|" +
            "\\d+[,.]\\d*";

    static String words = "\\s*(?<!\\d)[-?.!,:;\\s]+|[-?.!,:;\\s]+(?!\\d)\\s*";

    static String sentences = "[?.!]+\\s*";

    static String commaToDotInDigits = "(?<=\\d),(?=\\d)";
}
