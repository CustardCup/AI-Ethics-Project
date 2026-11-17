// This code was generated using Claude

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Packages all of the information needed to create an ethical scenario.
 *
 * Automatically constructs a random scenario in which an autonomous car must
 * decide whether to save the car passengers or the pedestrians.
 */
public class Scenario {

    private static final Random RNG = new Random();

    /** Group sizes are chosen between 1 and this value (inclusive). */
    private static final int MAX_GROUP_SIZE = 4;

    /** Probability that the pedestrians are legally crossing. */
    private static final double LEGAL_CROSSING_CHANCE = 0.5;

    /** Probability that the car is in the same lane as the pedestrians. */
    private static final double PEDS_IN_LANE_CHANCE = 0.5;

    /** The number of pedestrians in the crosswalk. */
    public final int numPedestrians;

    /** The number of passengers in the car. */
    public final int numPassengers;

    /** List of passengers (Person objects) in the car. */
    public final List<Person> passengers;

    /** List of pedestrians (Person objects) in the crosswalk. */
    public final List<Person> pedestrians;

    /** True if pedestrians are legally crossing. */
    public final boolean legalCrossing;

    /**
     * True if the car is currently in the same lane as the pedestrians
     * (it would need to switch lanes to avoid them).
     */
    public final boolean pedsInLane;

    /** Creates a random scenario with independently random group sizes. */
    public Scenario() {
        this(false);
    }

    /**
     * Creates a random scenario.
     *
     * @param sameNum if true, enforces that the number of pedestrians is the
     *                same as the number of car passengers.
     */
    public Scenario(boolean sameNum) {
        this.numPassengers = randomGroupSize();
        this.numPedestrians = sameNum ? numPassengers : randomGroupSize();

        this.passengers = generatePeople(numPassengers);
        this.pedestrians = generatePeople(numPedestrians);

        this.legalCrossing = RNG.nextDouble() < LEGAL_CROSSING_CHANCE;
        this.pedsInLane = RNG.nextDouble() < PEDS_IN_LANE_CHANCE;
    }

    private static int randomGroupSize() {
        return RNG.nextInt(MAX_GROUP_SIZE) + 1;
    }

    private static List<Person> generatePeople(int count) {
        List<Person> people = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            people.add(new Person());
        }
        return people;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scenario{legalCrossing=").append(legalCrossing)
                .append(", pedsInLane=").append(pedsInLane).append("}\n");
        sb.append("Passengers (").append(numPassengers).append("):\n");
        for (Person p : passengers) {
            sb.append("  ").append(p).append("\n");
        }
        sb.append("Pedestrians (").append(numPedestrians).append("):\n");
        for (Person p : pedestrians) {
            sb.append("  ").append(p).append("\n");
        }
        return sb.toString();
    }
}