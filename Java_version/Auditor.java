// This code was generated using Claude

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Runs many random scenarios through Decide and prints, live, the fraction of
 * times each kind of character was saved when it appeared in a scenario.
 *
 * Usage: java Auditor [numberOfRuns]
 */
public class Auditor {

    private static final int DEFAULT_RUNS = 1000;

    /** Pause between scenarios so the live updates are readable. */
    private static final int DELAY_MS = 25;

    /** ANSI codes: move cursor to top-left and clear the screen. */
    private static final String CLEAR_SCREEN = "\033[H\033[2J";

    private final Map<String, Integer> appeared = new HashMap<>();
    private final Map<String, Integer> saved = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int runs = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_RUNS;
        Auditor auditor = new Auditor();

        for (int i = 1; i <= runs; i++) {
            Scenario scenario = new Scenario(true);
            String decision = Decide.decide(scenario);

            auditor.record(scenario.passengers, decision.equals("passengers"));
            auditor.record(scenario.pedestrians, decision.equals("pedestrians"));

            auditor.print(i);
            Thread.sleep(DELAY_MS);
        }
    }

    /** Records every trait of every person in a group, and whether the group was saved. */
    private void record(List<Person> group, boolean wasSaved) {
        for (Person p : group) {
            for (String trait : traitsOf(p)) {
                appeared.merge(trait, 1, Integer::sum);
                if (wasSaved) {
                    saved.merge(trait, 1, Integer::sum);
                }
            }
        }
    }

    private static List<String> traitsOf(Person p) {
        List<String> traits = new ArrayList<>(List.of(
                p.ageGroup, p.biologicalSex, p.profession, p.charType));
        if (p.isPregnant) {
            traits.add("pregnant");
        }
        return traits;
    }

    /** Redraws the table, sorted from most to least often saved. */
    private void print(int runs) {
        List<String> traits = new ArrayList<>(appeared.keySet());
        traits.sort((a, b) -> Double.compare(ratio(b), ratio(a)));

        StringBuilder sb = new StringBuilder(CLEAR_SCREEN);
        sb.append("- % SAVED AFTER ").append(runs).append(" RUNS -\n");
        for (String trait : traits) {
            sb.append(String.format(Locale.US, "%-15s %.2f%n", trait, ratio(trait)));
        }
        System.out.print(sb);
        System.out.flush();
    }

    private double ratio(String trait) {
        return saved.getOrDefault(trait, 0) / (double) appeared.get(trait);
    }
}