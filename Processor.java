package readability;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import static readability.AgesTable.getAgeByScore;


public class Processor {
    private final String text;

    private final int characters;

    private final int words;
    private final int sentences;

    private final int syllables;
    private final int polySyllables;
    private final int polySyllablesMin = 3;

    private final ARI ari;
    private final FK fk;
    private final SMOG smog;
    private final CL cl;

    Processor(String text, int scorePrecision) {
        this.text = text;

        this.characters = text.replace(Regex.characters, "").length();

        this.words = text.split(Regex.words).length;
        this.sentences = text.split(Regex.sentences).length;

        this.syllables = (int) Pattern.compile(Regex.syllables).matcher(text).results().count();
        this.polySyllables = (int) Arrays.stream(text.split(Regex.words)).filter(word
                -> Pattern.compile(Regex.syllables).matcher(word).results().count() >= polySyllablesMin).count();

        this.ari = new ARI(characters, syllables, polySyllables, words, sentences, scorePrecision);
        this.fk = new FK(characters, syllables, polySyllables, words, sentences, scorePrecision);
        this.smog = new SMOG(characters, syllables, polySyllables, words, sentences, scorePrecision);
        this.cl = new CL(characters, syllables, polySyllables, words, sentences, scorePrecision);
    }

    public String getSummary() {
        return String.join("\n", "The text is:", text, "",
                String.join("\s","Words:", String.valueOf(words)),
                String.join("\s", "Sentences:", String.valueOf(sentences)),
                String.join("\s","Characters:", String.valueOf(characters)),
                String.join("\s","Syllables:", String.valueOf(syllables)),
                String.join("\s","Polysyllables:", String.valueOf(polySyllables)));
    }

    public String getIndex() {
        String dot = ".";
        Scanner scanner = new Scanner(System.in);

        double averageAge = (
                    getAgeByScore(ari.getScore())
                    + getAgeByScore(fk.getScore())
                    + getAgeByScore(smog.getScore())
                    + getAgeByScore(cl.getScore())) / (double) Index.values().length;

        System.out.printf("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):%s", "\s");

        return switch (scanner.nextLine().toLowerCase()) {

            case "ari" -> String.join(
                    "", "\n", ari.getFullDescription(Index.ARI.getFullName()), dot);
            case "fk" -> String.join(
                    "", "\n", fk.getFullDescription(Index.FK.getFullName()), dot);
            case "smog" -> String.join(
                    "",  "\n", smog.getFullDescription(Index.SMOG.getFullName()), dot);
            case "cl" -> String.join(
                    "", "\n", cl.getFullDescription(Index.CL.getFullName()), dot);
            case "all" -> String.join("", "\n",
                    ari.getFullDescription(Index.ARI.getFullName()), dot, "\n",
                    fk.getFullDescription(Index.FK.getFullName()), dot, "\n",
                    smog.getFullDescription(Index.SMOG.getFullName()), dot, "\n",
                    cl.getFullDescription(Index.CL.getFullName()), dot, "\n",
                    "\n",
                    String.format(averageAge % 1 == 0
                                    ? "This text should be understood in average by %.0f-year-olds."
                                    : "This text should be understood in average by %.2f-year-olds.",
                            averageAge).replaceAll(Regex.commaToDotInDigits, dot));
            default ->  "invalid entry";
        };

    }

    public static double truncate(double n, int decimals) {
        int whole = (int) (n * Math.pow(10, decimals));
        return whole / Math.pow(10, decimals);
    }
}
