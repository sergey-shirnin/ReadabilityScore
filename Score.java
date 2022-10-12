package readability;

import static readability.Processor.truncate;
import static readability.AgesTable.getAgeByScore;

public abstract class Score {
    final int characters;
    final int syllables;
    final int polySyllables;
    final int words;
    final int sentences;
    final int precision;

    Score(int characters, int syllables, int polySyllables, int words, int sentences, int precision) {
        this.characters = characters;
        this.syllables = syllables;
        this.polySyllables = polySyllables;
        this.words = words;
        this.sentences = sentences;
        this.precision = precision;
    }

    public abstract double getScore();

    public String getGroup() {
        return String.join("\s",
                "(about", getAgeByScore(getScore()) + "-year-olds)");
    }

    public String getFullDescription(String name) {
        return String.join("\s",
                name, String.valueOf(getScore()), getGroup());
    }
}


class ARI extends Score{  // Automated Readability Index
    final double DOUBLE0_5 = 0.5;
    final double DOUBLE4_71 = 4.71;
    final double DOUBLE21_43 = 21.43;

    ARI(int characters, int syllables, int polySyllables, int words, int sentences, int precision) {
        super(characters, syllables, polySyllables, words, sentences, precision);
    }

    @Override
    public double getScore() {
        double score = DOUBLE4_71 * characters / words
                + DOUBLE0_5 * words / sentences
                - DOUBLE21_43;
        return truncate(score, precision);
    }
}


class FK extends Score { // Flesch–Kincaid readability tests
    final double DOUBLE0_39 = 0.39;
    final double DOUBLE11_8 = 11.8;
    final double DOUBLE15_59 = 15.59;

    FK(int characters, int syllables, int polySyllables, int words, int sentences, int precision) {
        super(characters, syllables, polySyllables, words, sentences, precision);
    }

    @Override
    public double getScore() {
        double score = DOUBLE0_39 * words / sentences
                + DOUBLE11_8 * syllables / words
                - DOUBLE15_59;
        return truncate(score, precision);
    }
}


class SMOG extends Score { // Simple Measure of Gobbledygook
    final double DOUBLE1_043 = 1.043;
    final double DOUBLE3_1291 = 3.1291;
    final double DOUBLE30 = 30;

    SMOG(int characters, int syllables, int polySyllables, int words, int sentences, int precision) {
        super(characters, syllables, polySyllables, words, sentences, precision);
    }

    @Override
    public double getScore() {
        double score = DOUBLE1_043 * Math.sqrt(polySyllables * DOUBLE30 / sentences)
                + DOUBLE3_1291;
        return truncate(score, precision);
    }
}


class CL extends Score { // Coleman–Liau Index
    final double DOUBLE0_0588 = 0.0588;
    final double DOUBLE0_296 = 0.296;
    final double DOUBLE15_8 = 15.8;

    CL(int characters, int syllables, int polySyllables, int words, int sentences, int precision) {
        super(characters, syllables, polySyllables, words, sentences, precision);
    }

    @Override
    public double getScore() {
        double score = DOUBLE0_0588 * characters / words * 100
                - DOUBLE0_296 * sentences / words * 100
                - DOUBLE15_8;
        return truncate(score, precision);
    }
}


enum Index {
    ARI("Automated Readability Index"),
    FK("Flesch-Kincaid readability tests"),
    SMOG("Simple Measure of Gobbledygook"),
    CL("Coleman-Liau Index");

    final String fullName;

    Index(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
