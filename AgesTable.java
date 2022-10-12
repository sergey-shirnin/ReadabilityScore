package readability;

import java.util.Arrays;


public enum AgesTable {
    GROUP_ONE(6, 1),
    GROUP_TWO(7, 2),
    GROUP_THR(8, 3),
    GROUP_FOU(9, 4),
    GROUP_FIV(10, 5),
    GROUP_SIX(11, 6),
    GROUP_SEV(12, 7),
    GROUP_EIG(13, 8),
    GROUP_NIN(14, 9),
    GROUP_TEN(15, 10),
    GROUP_ELE(16, 11),
    GROUP_TWE(17, 12),
    GROUP_THI(18, 13),
    GROUP_FIN(22, 14);

    private final int upperAge;
    private final int score;

    AgesTable(int upperAge, int score) {
        this.upperAge = upperAge;
        this.score = score;
    }

    private int getUpperAge() {
        return upperAge;
    }

    public static int getAgeByScore(double score) {
        int scoreCeilInt = (int) Math.ceil(score);
        return Arrays.stream(values()).filter(e -> e.score == scoreCeilInt)
                .findFirst()
                .map(AgesTable::getUpperAge)
                .orElse(-1);
    }
}
