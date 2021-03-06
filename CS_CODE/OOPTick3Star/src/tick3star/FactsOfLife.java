package tick3star;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FactsOfLife {

	public static void print(World w) {
		System.out.println("- " + w.getGenerationCount());
		for (int i = 0; i != w.getHeight(); ++i) {
			for (int j = 0; j != w.getWidth(); ++j) {
				System.out.print(w.getCell(j, i) ? "#" : "_");
			}
			System.out.println("");
		}
	}

	public static int findLastDuplicate(ArrayList<ArrayWorld> list) {
		int ith = 0;
		int size = list.size();
		for (int i = 0; i < size - 1; ++i) {
			if (list.get(size - 1).worldEqual(list.get(i)))
				return ith;
			++ith;
		}
		return -1;
	}

	public static float growthRate(float previous, float current) {
		return (current / previous) - 1;
	}

	public static float deathRate(float previous, float current) {
		return 1 - (current / previous);
	}

	public static ArrayList<Float> factsDetect(Pattern p) throws PatternFormatException {
		ArrayWorld world = new ArrayWorld(p);
		ArrayList<Float> facts = new ArrayList<>();
		ArrayList<ArrayWorld> worldsList = new ArrayList<>();
		int previousNumberOfLives;
		int currentNumberOfLives = world.numberOfLives();
		float currentGrowthRate;
		float currentDeathRate;
		int duplicateIndex = -1;
		worldsList.add(new ArrayWorld(world));
		facts.add((float) 0);
		facts.add((float) 0);
		facts.add(0f);
		facts.add(0f);
		facts.add((float) currentNumberOfLives);

		while (duplicateIndex == -1) {
			world.nextGeneration();
			worldsList.add(new ArrayWorld(world));

			previousNumberOfLives = currentNumberOfLives;
			currentNumberOfLives = world.numberOfLives();

			currentGrowthRate = growthRate(previousNumberOfLives, currentNumberOfLives);
			currentDeathRate = deathRate(previousNumberOfLives, currentNumberOfLives);

			if (currentNumberOfLives > facts.get(4))
				facts.set(4, (float) currentNumberOfLives);
			if (currentGrowthRate > facts.get(2))
				facts.set(2, currentGrowthRate);
			if (currentDeathRate > facts.get(3))
				facts.set(3, currentDeathRate);
			duplicateIndex = findLastDuplicate(worldsList);
		}

		facts.set(0, (float) duplicateIndex);
		facts.set(1, (float) (world.getGenerationCount() - duplicateIndex - 1));
		return facts;

	}

	public static String toOutputString(LinkedList<String> list) {
		String str = "|";
		for (String s : list) {
			str += s + "|";
		}
		return str;
	}

	public static void main(String args[]) throws IOException, PatternFormatException, PatternNotFound {

		if (args.length != 1) {
			System.out.println("Usage: java GameOfLife <path/url to store>");
			return;
		}

		try {
			PatternStore ps = new PatternStore(args[0]);
			LinkedList<String> patternNamesList = (LinkedList<String>) ps.getPatternNames();
			File file = new File("D:/Java_Workspace/OOPTick3Star/stats.txt");
			Writer txtWriter = new FileWriter(file);
			BufferedWriter txtBufferedWriter = new BufferedWriter(txtWriter);

			Map<String, ArrayList<Float>> factsMap = new HashMap<>();

			ArrayList<Float> factsStore;
			String formatStr = "%-35s %-15s %-15s %-15s %-15s %-15s%n";
			for (String name : patternNamesList) {
				System.out.println(name);
				factsStore = factsDetect(ps.getPatternByName(name));
				factsMap.put(name, factsStore);
				txtBufferedWriter.write(String.format(formatStr, name, factsStore.get(0), factsStore.get(1),
						factsStore.get(2), factsStore.get(3), factsStore.get(4)));
			}

			ArrayList<Pair<LinkedList<String>, Float>> maxStats = new ArrayList<>();
			// why I cannot use ArrayList here?
			for (int i = 0; i < 5; ++i) {
				LinkedList<String> strings = new LinkedList<String>();
				strings.add(patternNamesList.get(0));
				maxStats.add(
						new Pair<LinkedList<String>, Float>(strings, factsMap.get(patternNamesList.get(0)).get(i)));
			}

			for (String name : factsMap.keySet()) {
				for (int i = 0; i < 5; ++i) {
					if (factsMap.get(name).get(i) > maxStats.get(i).second()) {
						LinkedList<String> strings = new LinkedList<String>();
						strings.add(name);
						maxStats.set(i, new Pair<LinkedList<String>, Float>(strings, factsMap.get(name).get(i)));
					} else if (factsMap.get(name).get(i).equals(maxStats.get(i).second())) {
						maxStats.get(i).first().add(name);
					}
				}
			}
			txtBufferedWriter.write("Next is the required output, can have duplicate");
			txtBufferedWriter.newLine();

			for (Pair<LinkedList<String>, Float> e : maxStats) {
				txtBufferedWriter.write(toOutputString(e.first()));
				txtBufferedWriter.newLine();
			}

			txtBufferedWriter.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

}