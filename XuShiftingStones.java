// Name: Andrew Xu
// Date: 6/07/22
// Purpose: Shifting Stones Card Game

import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class XuShiftingStones extends Applet implements ActionListener {
	Panel p_card;
	Panel[] cards = new Panel[8];
	CardLayout cdLayout = new CardLayout();
	Panel game_card;
	Color background_color = new Color(229, 241, 207);

	int row = 9;
	int col = 9;
	int selected = -1;
	int strikes = 0;
	int screen = 1;
	int cards_in_hand = 0;

	JLabel strikes_label;
	JLabel game_label;
	JButton pile_button;
	JButton[] hand_buttons = new JButton[4];

	PatternCard[] hand = new PatternCard[4];
	CardStack pile;

	BlockCard[][] board = {
			{},
			{},
			{}
	};

	public void init() {
		p_card = new Panel();
		p_card.setLayout(cdLayout);
		generateScreens();
		resize(350, 500);
		setLayout(new BorderLayout());
		add("Center", p_card);
	}

	public void generateScreens() {
		for (int i = 0; i <= 6; i++) {
			cards[i] = instructionsScreen(i);
			p_card.add("" + i, cards[i]);
		}

		gameScreen();
	}

	public Panel instructionsScreen(int num) {
		Panel output = new Panel();

		ImageIcon image;
		Color color;
		if (num == 0) {
			image = createImageIcon("pics/background.jpg");
			color = new Color(48, 53, 52);
		} else {
			image = createImageIcon("pics/instructions" + num + ".png");
			color = background_color;
		}

		JButton bkg = new JButton(image);
		bkg.setBackground(color);
		bkg.setPreferredSize(new Dimension(350, 500));
		bkg.setMargin(new Insets(0, 0, 0, 0));
		bkg.setBorderPainted(false);
		bkg.setActionCommand("screen" + (num + 1));
		bkg.addActionListener(this);
		output.add(bkg);

		return output;
	}

	public void gameScreen() {
		game_card = new Panel();
		game_card.setBackground(background_color);

		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.VERTICAL;

		JLabel title = new JLabel("Shifting Stones");
		title.setFont(new Font("Arial", Font.BOLD, 40));

		gbc.gridx = 0;
		gbc.gridy = 0;
		game_card.add(title, gbc);

		gbc.gridy++;
		game_card.add(getP1(), gbc);

		gbc.gridy++;
		game_card.add(getGrid(), gbc);

		gbc.gridy++;
		game_card.add(getP2(), gbc);

		gbc.gridy++;
		game_card.add(getP3(), gbc);

		// gbc.gridy++;
		// card3.add(get_space(1, 40, true), gbc);

		// gbc.gridy++;
		// card3.add(get_p4(), gbc);

		p_card.add("7", game_card);
	}

	public JPanel getP1() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 25));

		strikes_label = new JLabel("Strikes: " + strikes + "/4");
		panel.add(strikes_label);

		return panel;
	}

	public JPanel getGrid() {
		JPanel grid = new JPanel(new GridLayout(3, 3));
		grid.setBackground(background_color);
		grid.setPreferredSize(new Dimension(225, 225));

		board = generateNewGrid();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton block = new JButton(board[i][j].getImage());
				block.setPreferredSize(new Dimension(75, 75));
				block.setMargin(new Insets(0, 0, 0, 0));
				block.setBorderPainted(false);

				grid.add(block);
			}
		}

		return grid;
	}

	public BlockCard[][] generateNewGrid() {

		BlockCard[][] g = defaultGrid();
		return shuffleGrid(g);
	}

	public BlockCard[][] defaultGrid() {
		String[] colors = { "blue", "blue", "blue", "green", "green", "green", "red", "red", "yellow" };

		BlockCard[][] answer = new BlockCard[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				answer[i][j] = new BlockCard(colors[i * 3 + j]);
			}
		}

		return answer;
	}

	public BlockCard[][] shuffleGrid(BlockCard[][] gd) {
		for (int i = 0; i < 100; i++) {
			int a1 = (int) (Math.random() * 3);
			int a2 = (int) (Math.random() * 3);

			int b1 = (int) (Math.random() * 3);
			int b2 = (int) (Math.random() * 3);

			BlockCard temp = gd[a1][a2];
			gd[a1][a2] = gd[b1][b2];
			gd[b1][b2] = temp;

			gd[a1][a2].switchColor();
		}

		return gd;
	}

	public JPanel getP2() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 50));

		game_label = new JLabel("Welcome to Shifting Stones!");
		panel.add(game_label);

		return panel;
	}

	public JPanel getP3() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(350, 100));

		pile = new CardStack();
		panel.add(getStackButton());

		generateHand();
		for (int i = 0; i < 4; i++) {
			panel.add(hand_buttons[i]);
		}

		panel.add(getInstructionsButton());

		return panel;
	}

	public JButton getStackButton() {
		pile_button = new JButton(pile.getImage());
		pile_button.setPreferredSize(new Dimension(50, 100));
		pile_button.setMargin(new Insets(0, 0, 0, 0));
		pile_button.setBorderPainted(false);

		return pile_button;
	}

	public void generateHand() {
		int cards_to_draw = 4 - cards_in_hand;

		for (int i = 0; i < cards_to_draw; i++) {
			hand[findEmptyIndexInHand()] = pile.pop();
		}

		generateHandButtons();
	}

	public int findEmptyIndexInHand() {
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] == null)
				return i;
		}
		return -1;
	}

	public void generateHandButtons() {
		for (int i = 0; i < hand.length; i++) {
			hand_buttons[i] = new JButton(hand[i].getImage());
			hand_buttons[i].setPreferredSize(new Dimension(50, 100));
			hand_buttons[i].setMargin(new Insets(0, 0, 0, 0));
			hand_buttons[i].setBorderPainted(false);
		}
	}

	public JButton getInstructionsButton() {
		JButton instructions_button = new JButton(createImageIcon("pics/instrB2.png"));
		instructions_button.setPreferredSize(new Dimension(50, 100));
		instructions_button.setMargin(new Insets(0, 0, 0, 0));
		instructions_button.setBorderPainted(false);

		return instructions_button;
	}

	public void redraw() {

	}

	public void reset() {

	}

	public boolean win() {
		return false;
	}

	public void gameWon() {
		selected = -1;
		JOptionPane.showMessageDialog(null, "Completed Sodoku!", "Completed",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean lost() {
		// if (lives <= 0)
		// return true;
		return false;
	}

	public void gameLost() {
		selected = -1;
		JOptionPane.showMessageDialog(null, "Ran out of lives!", "Not Completed",
				JOptionPane.INFORMATION_MESSAGE);
		reset();
	}

	public void actionPerformed(ActionEvent e) {
		String action_command = e.getActionCommand();

		if (action_command.startsWith("screen")) {
			char last_char = action_command.charAt(action_command.length() - 1);
			cdLayout.show(p_card, "" + last_char);
		}

	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}
}
