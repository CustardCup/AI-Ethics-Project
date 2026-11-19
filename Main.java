public class Main {
    public static void main(String[] args) {
        System.out.println("Before:");
        Decide.viewScore();

        Decide.assignScore("human", 3);
        Decide.assignScore("dog", 1);

        System.out.println("\nAfter:");
        Decide.viewScore();
    }
}