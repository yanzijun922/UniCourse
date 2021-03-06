package tick5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUILife extends JFrame implements ListSelectionListener {

	private World mWorld;
	private PatternStore mStore;
	private ArrayList<World> mCachedWorlds;
	private boolean mPlaying;
	private JButton mPlayButton;
	private Timer mTimer;

	public GUILife(PatternStore ps) throws PatternFormatException, PatternNotFound {
		super("Game of Life");
		mStore = ps;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);
		add(createPatternsPanel(), BorderLayout.WEST);
		add(createControlPanel(), BorderLayout.SOUTH);
		add(createGamePanel(), BorderLayout.CENTER);

	}

	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch, title);
		component.setBorder(tb);
	}

	private JPanel createGamePanel() throws PatternFormatException, PatternNotFound {
		GamePanel gamePanel = new GamePanel();
		return gamePanel;
	}

	private JPanel createPatternsPanel() {
		JPanel patt = new JPanel();
		patt.setLayout(new BorderLayout());
		List<Pattern> patternList = (List<Pattern>) mStore.getPatternsNameSorted();
		JList<Pattern> patternJList = new JList<Pattern>(
				((List<Pattern>) patternList).toArray(new Pattern[patternList.size()]));
		patternJList.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(patternJList);
		scrollPane.setVerticalScrollBar(new JScrollBar());
		scrollPane.setHorizontalScrollBar(new JScrollBar());

		addBorder(patt, "Patterns");
		patt.add(scrollPane, BorderLayout.CENTER);
		return patt;
	}

	private JPanel createControlPanel() {
		JPanel ctrl = new JPanel();
		addBorder(ctrl, "Controls");
		GridLayout controlPanelLayout = new GridLayout(1, 0);
		ctrl.setLayout(controlPanelLayout);
		mPlayButton = new JButton("play");
		JButton backButton = new JButton("back");
		JButton forwardButton = new JButton("forward");

		mPlayButton.addActionListener(e -> runOrPause());
		backButton.addActionListener(e -> moveBack());
		forwardButton.addActionListener(e -> moveForward());
		ctrl.add(forwardButton);
		ctrl.add(mPlayButton);
		ctrl.add(backButton);

		return ctrl;
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

	private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
		if (!useCloning) {
			if (mWorld instanceof ArrayWorld) {
				return new ArrayWorld((ArrayWorld) mWorld);
			} else if (mWorld instanceof PackedWorld) {
				return new PackedWorld((PackedWorld) mWorld);
			}
		} else {
			return mWorld.clone();
		}
		return null;
	}

	private void moveBack() {
		if (mWorld == null) {
			return;
		} else {
			if (mWorld.getGenerationCount() != 0) {
				mWorld = mCachedWorlds.get(mWorld.getGenerationCount() - 1);
			}
			((GamePanel) getContentPane().getComponent(2)).display(mWorld);
		}
	}

	private void moveForward() {
		if (mWorld == null) {
			return;
		} else {
			if (mWorld.getGenerationCount() < mCachedWorlds.size() - 1) {
				mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
			} else {
				try {
					mWorld = copyWorld(true);
					mWorld.nextGeneration();
					mCachedWorlds.add(mWorld.getGenerationCount(), mWorld);
				} catch (CloneNotSupportedException e) {
					System.out.println("clone of World is not supported.");
				}
			}
			((GamePanel) getContentPane().getComponent(2)).display(mWorld);
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Pattern p = ((JList<Pattern>) e.getSource()).getSelectedValue();
		mCachedWorlds = new ArrayList<>();
		try {
			if (p.getHeight() * p.getWidth() <= 64) {
				mWorld = new PackedWorld(p);
			} else {
				mWorld = new ArrayWorld(p);
			}
		} catch (PatternFormatException exp) {
			System.out.println(p.toString() + "is in wrong format");
		}
		mCachedWorlds.add(mWorld);
		if (mPlaying) {
			mTimer.cancel();
			mPlaying = false;
			mPlayButton.setText("Play");
		}
		((GamePanel) getContentPane().getComponent(2)).display(mWorld);

	}

	private void runOrPause() {
		if (mPlaying) {
			mTimer.cancel();
			mPlaying = false;
			mPlayButton.setText("Play");
		} else {
			mPlaying = true;
			mPlayButton.setText("Stop");
			mTimer = new Timer(true);
			mTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					moveForward();
				}
			}, 0, 500);
		}
	}

	public static void main(String[] args) throws PatternFormatException, PatternNotFound {
		PatternStore ps;
		try {
			ps = new PatternStore("http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt");
			GUILife gui = new GUILife(ps);
			gui.setVisible(true);
		} catch (IOException e) {
			System.out.println("Error happens when loading the PatternStore data.");
			e.printStackTrace();
		}

	}
}
