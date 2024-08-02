import java.util.*;

public class AppleDistributor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> appleWeights = new ArrayList<>();
        System.out.println("Enter apple weight in grams (-1 to stop):");
        while (true) {
            int weight = scanner.nextInt();
            if (weight == -1) break;
            appleWeights.add(weight);
        }

        int totalWeight = appleWeights.stream().mapToInt(Integer::intValue).sum();
        int ramShare = totalWeight * 50 / 100;
        int shamShare = totalWeight * 30 / 100;
        int rahimShare = totalWeight * 20 / 100;

        System.out.println("Total Weight: " + totalWeight + "g");
        System.out.println("Ram: " + ramShare + "g");
        System.out.println("Sham: " + shamShare + "g");
        System.out.println("Rahim: " + rahimShare + "g");

        distributeApples(appleWeights, ramShare, shamShare, rahimShare);
    }

    private static void distributeApples(List<Integer> appleWeights, int ramShare, int shamShare, int rahimShare) {
        appleWeights.sort(Collections.reverseOrder());

        List<Integer> ramApples = new ArrayList<>();
        List<Integer> shamApples = new ArrayList<>();
        List<Integer> rahimApples = new ArrayList<>();

        int ramTotal = 0, shamTotal = 0, rahimTotal = 0;

        for (int weight : appleWeights) {
            if (ramTotal + weight <= ramShare) {
                ramApples.add(weight);
                ramTotal += weight;
            } else if (shamTotal + weight <= shamShare) {
                shamApples.add(weight);
                shamTotal += weight;
            } else if (rahimTotal + weight <= rahimShare) {
                rahimApples.add(weight);
                rahimTotal += weight;
            }
        }

        System.out.println("Distribution Result:");
        System.out.println("Ram: " + ramApples);
        System.out.println("Sham: " + shamApples);
        System.out.println("Rahim: " + rahimApples);
    }
}
