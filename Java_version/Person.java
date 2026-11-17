// This code was generated using Claude

import java.util.Random;

public class Person {

    /** Anything with a cumulative upper bound on a 1-100 roll. */
    public interface Weighted {
        int upperBound();
    }

    public enum AgeGroup implements Weighted {
        CHILD(20),     // 1-20   -> 20%
        ELDERLY(35),   // 21-35  -> 15%
        ADULT(100);    // 36-100 -> 65%

        private final int upperBound;
        AgeGroup(int upperBound) { this.upperBound = upperBound; }
        @Override public int upperBound() { return upperBound; }
    }

    public enum BiologicalSex implements Weighted {
        INTERSEX(1),   // 1      -> 1%
        FEMALE(50),    // 2-50   -> 49%
        MALE(100);     // 51-100 -> 50%

        private final int upperBound;
        BiologicalSex(int upperBound) { this.upperBound = upperBound; }
        @Override public int upperBound() { return upperBound; }
    }

    public enum CharType implements Weighted {
        DOG(15),       // 1-15   -> 15%
        CAT(30),       // 16-30  -> 15%
        HUMAN(100);    // 31-100 -> 70%

        private final int upperBound;
        CharType(int upperBound) { this.upperBound = upperBound; }
        @Override public int upperBound() { return upperBound; }
    }

    public enum Profession implements Weighted {
        STUDENT(20),     // 1-20   -> 20%
        CRIMINAL(21),    // 21     -> 1%
        UNEMPLOYED(52),  // 22-52  -> 31%
        DOCTOR(53),      // 53     -> 1%
        EMPLOYED(99),   // 54-99 -> 47%
        CEO(100);                   // 100  -> 1%

        private final int upperBound;
        Profession(int upperBound) { this.upperBound = upperBound; }
        @Override public int upperBound() { return upperBound; }
    }

    /** Pregnancy odds for anyone who is eligible (adult female or intersex). */
    public enum PregnancyStatus implements Weighted {
        PREGNANT(10),       // 1-10   -> 10%
        NOT_PREGNANT(100);  // 11-100 -> 90%

        private final int upperBound;
        PregnancyStatus(int upperBound) { this.upperBound = upperBound; }
        @Override public int upperBound() { return upperBound; }
    }

    private static final Random RNG = new Random();

    public final String ageGroup;
    public final String biologicalSex;
    public final String profession;
    public final String charType;
    public final boolean isPregnant;

    public Person() {
        AgeGroup age = pick(AgeGroup.values());
        BiologicalSex sex = pick(BiologicalSex.values());
        CharType type = pick(CharType.values());

        // Profession: dogs/cats are always unemployed (this takes precedence over
        // the age rule), then children are students, elderly are unemployed,
        // and only adult humans roll on the profession distribution.
        Profession job;
        if (type == CharType.DOG || type == CharType.CAT) {
            job = Profession.UNEMPLOYED;
        } else if (age == AgeGroup.CHILD) {
            job = Profession.STUDENT;
        } else if (age == AgeGroup.ELDERLY) {
            job = Profession.UNEMPLOYED;
        } else {
            job = pick(Profession.values());
        }

        // Pregnancy: only adult female/intersex characters (any charType) are eligible.
        boolean eligible = age == AgeGroup.ADULT
                && (sex == BiologicalSex.FEMALE || sex == BiologicalSex.INTERSEX);

        this.ageGroup      = age.name().toLowerCase();
        this.biologicalSex = sex.name().toLowerCase();
        this.charType      = type.name().toLowerCase();
        this.profession    = job.name().toLowerCase();
        this.isPregnant    = eligible && pick(PregnancyStatus.values()) == PregnancyStatus.PREGNANT;
    }

    /** Rolls 1-100 and returns the value with the smallest upper bound >= the roll. */
    private static <T extends Enum<T> & Weighted> T pick(T[] values) {
        int roll = RNG.nextInt(100) + 1;
        T best = null;
        for (T v : values) {
            if (roll <= v.upperBound()
                    && (best == null || v.upperBound() < best.upperBound())) {
                best = v;
            }
        }
        return best;
    }

    @Override
    public String toString() {
        return "Person{ageGroup=" + ageGroup
                + ", biologicalSex=" + biologicalSex
                + ", profession=" + profession
                + ", charType=" + charType
                + ", isPregnant=" + isPregnant + "}";
    }
}