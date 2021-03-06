package tick5;

import java.awt.Color;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private World mWorld = null;

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (mWorld != null) {
			int len = Math.min(this.getHeight() / mWorld.getHeight(), this.getWidth() / mWorld.getWidth());

			g.setColor(Color.BLACK);
			for (int y = 0; y < mWorld.getHeight(); ++y) {
				for (int x = 0; x < mWorld.getWidth(); ++x) {
					if (mWorld.getCell(x, y))
						g.fillRect(len * x, len * y, len, len);
				}
			}

			g.setColor(Color.GRAY);
			for (int x = 0; x <= mWorld.getWidth(); ++x) {
				g.drawLine(len * x, 0, len * x, len * mWorld.getHeight());
			}

			for (int y = 0; y <= mWorld.getHeight(); ++y) {
				g.drawLine(0, len * y, len * mWorld.getWidth(), len * y);
			}
			g.drawString("Generation:" + Integer.toString(mWorld.getGenerationCount()), 0, getHeight() - 10);
		}
	}

	public void display(World w) {
		mWorld = w;
		repaint();
	}
}
