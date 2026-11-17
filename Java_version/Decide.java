// This code was generated using Claude

import java.util.List;
import java.util.Map;

public class Decide {

    /**
     * Editable scoring chart. Each trait is worth some number of points; the
     * points of everyone on a side are added up to give that side's score.
     * Change these numbers to change how the car values each trait.
     */
    private static final Map<String, Integer> SCORING = Map.ofEntries(
            // CharType
            Map.entry("cat", 0), Map.entry("dog", 0), Map.entry("human", 1),
            // Age
            Map.entry("child", 0), Map.entry("elderly", 0), Map.entry("adult", 0),
            // Professions
            Map.entry("student", 0), Map.entry("doctor", 0), Map.entry("employed", 0),
            Map.entry("unemployed", 0), Map.entry("criminal", 0), Map.entry("ceo",0 ),
            // Biological sex
            Map.entry("male", 0), Map.entry("female", 0), Map.entry("intersex", 0),
            // Other characteristics
            Map.entry("pregnant", 0)
    );

    /**
     * Decides whether your car will save the passengers or the pedestrians.
     *
     * @param scenario a Scenario object containing all of the information
     *                 about the scenario.
     * @return a string indicating whether you are saving "passengers" or
     *         "pedestrians". Note that this method MUST make a decision for
     *         ANY potential scenario.
     */
    public static String decide(Scenario scenario) {
        int passengerScore = 0;
        int pedestrianScore = 0;

        // Example of using the scoring chart: score each side by charType only.
        // To include another trait, add a line like
        //   score += SCORING.get(p.ageGroup);
        // inside scoreGroup.
        passengerScore += scoreGroup(scenario.passengers);
        pedestrianScore += scoreGroup(scenario.pedestrians);

        // Save the passengers only if their side scores strictly higher.
        // Ties (and every other case) save the pedestrians, so a decision is
        // always made.
        if (passengerScore > pedestrianScore) {
            return "passengers";
        } else {
            return "pedestrians";
        }
    }

    /** Adds up the score of everyone in a group. */
    private static int scoreGroup(List<Person> group) {
        int score = 0;
        for (Person p : group) {
            score += SCORING.get(p.charType);
        }
        return score;
    }
}