package tick3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class GameOfLife {

	private World mWorld;
	private PatternStore mStore;

	public GameOfLife(PatternStore s) {
		mStore = s;
	}

	public GameOfLife(World w) {
		mWorld = w;
	}

	public void print() {
		System.out.println("- " + mWorld.getGenerationCount());
		for (int i = 0; i != mWorld.getHeight(); ++i) {
			for (int j = 0; j != mWorld.getWidth(); ++j) {
				System.out.print(mWorld.getCell(j, i) ? "#" : "_");
			}
			System.out.println("");
		}
	}

	public void play() throws IOException, PatternFormatException, PatternNotFound {

		String response = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Please select a pattern to play (l to list:");
		while (!response.equals("q")) {
			response = in.readLine();
			System.out.println(response);
			if (response.equals("f")) {
				if (mWorld == null)
					System.out.println("Please select a pattern to play (l to list):");
				else {
					mWorld.nextGeneration();
					print();
				}
			} else if (response.equals("l")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				int i = 0;
				for (Pattern p : names) {
					System.out.println(i + " " + p.getName() + "  (" + p.getAuthor() + ")");
					i++;
				}
			} else if (response.startsWith("p")) {
				List<Pattern> names = mStore.getPatternsNameSorted();
				// TODO: Extract the integer after the p in response
				// TODO: Get the associated pattern
				// TODO: Initialise mWorld using PackedWorld or ArrayWorld based
				// on pattern world size
				int i = Integer.parseInt(response.split(" ")[1]);
				Pattern p = names.get(i);

				if (p.getHeight() * p.getWidth() <= 64) {
					mWorld = new PackedWorld(p);
				} else {
					mWorld = new ArrayWorld(p);
				}

				print();
			}

		}
	}

	public static void main(String args[]) throws IOException, PatternFormatException, PatternNotFound {

		if (args.length != 1) {
			System.out.println("Usage: java GameOfLife <path/url to store>");
			return;
		}

		try {
			PatternStore ps = new PatternStore(args[0]);
			GameOfLife gol = new GameOfLife(ps);
			gol.play();
		} catch (IOException ioe) {
			System.out.println("Failed to load pattern store");
		}

	}

}