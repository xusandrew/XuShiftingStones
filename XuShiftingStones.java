// Name: Andrew Xu
// Date: 6/07/22
// Purpose: Shifting Stones Card Game

import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class XuShiftingStones extends Applet implements ActionListener {
	Panel p_card;
	Panel[] cards = new Panel[7];
	CardLayout cdLayout = new CardLayout();
	Panel game_card;
	Color background_color = new Color(229, 241, 207);
	Color button_color = new Color(166, 126, 92);

	int strikes = 0;
	int screen = 1;
	int cards_in_hand = 0;
	int cards_remaining = 16;
	int selected = -1;
	int points = 0;
	boolean grid_locked = false;
	boolean scored_this_turn = false;

	JLabel strikes_label;
	JLabel points_label;
	JLabel game_label;

	PatternCard[] hand = new PatternCard[4];
	JButton[] hand_buttons = new JButton[4];

	CardStack pile;
	JButton pile_button;

	BlockCard[][] board = new BlockCard[3][3];
	JButton[][] board_buttons = new JButton[3][3];

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
			image = createImageIcon("pics/background.png");
			color = new Color(48, 53, 52);
		} else {
			image = createImageIcon("pics/small_instructions" + num + ".png");
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

		gbc.gridy++;
		game_card.add(getP4(), gbc);

		p_card.add("7", game_card);
	}

	public JPanel getP1() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 25));

		strikes_label = new JLabel("Strikes: " + strikes + "/4");
		panel.add(strikes_label, BorderLayout.WEST);

		points_label = new JLabel("Points: " + points + "  ");
		panel.add(points_label, BorderLayout.EAST);

		return panel;
	}

	public JPanel getGrid() {
		JPanel grid = new JPanel(new GridLayout(3, 3));
		grid.setBackground(background_color);
		grid.setPreferredSize(new Dimension(225, 225));

		board = generateNewGrid();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board_buttons[i][j] = new JButton();
				board_buttons[i][j].setPreferredSize(new Dimension(75, 75));
				board_buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
				board_buttons[i][j].setBorderPainted(false);
				board_buttons[i][j].addActionListener(this);
				board_buttons[i][j].setActionCommand("block" + (i * 3 + j));

				grid.add(board_buttons[i][j]);
			}
		}

		updateBlockButtons();
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

	public void updateBlockButtons() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				board_buttons[i][j].setIcon(board[i][j].getImage());
		}
	}

	public void handleClickBlock(char block_clicked) {
		if (grid_locked)
			return;

		int n = Character.getNumericValue(block_clicked);
		int a = (int) n / 3;
		int b = n % 3;

		if (selected == -1) {

			select(n, a, b);
			return;
		}

		if (selected == n) {
			flipBlock(a, b);
			deselect(a, b);
			return;
		}

		if (isAdjacent(selected, a, b)) {
			swapBlocks(selected, a, b);
			deselect(a, b);
			return;
		}

		deselect((int) selected / 3, selected % 3);
	}

	public void flipBlock(int a, int b) {
		board[a][b].switchColor();
		forceDiscard();
	}

	public void select(int n, int a, int b) {
		selected = n;
		board[a][b].setSelected(true);
	}

	public void deselect(int a, int b) {
		selected = -1;
		board[a][b].setSelected(false);
	}

	public boolean isAdjacent(int s, int x2, int y2) {
		int x1 = (int) s / 3;
		int y1 = s % 3;

		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1)
			return true;
		return false;
	}

	public void swapBlocks(int s, int a, int b) {
		int x1 = (int) s / 3;
		int y1 = s % 3;

		int x2 = a;
		int y2 = b;

		BlockCard temp1 = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = temp1;

		forceDiscard();
	}

	public void forceDiscard() {
		grid_locked = true;
		game_label.setText("                Discard a card to continue.");
		return;
	}

	public JPanel getP2() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(350, 50));

		game_label = new JLabel("                Welcome to Shifting Stones!                ");
		game_label.setFont(new Font("Arial", Font.BOLD, 15));
		panel.add(game_label);

		return panel;
	}

	public JPanel getP3() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(350, 80));

		pile = new CardStack();
		panel.add(getStackButton());

		generateHandButtons();
		generateHand();
		updateHandButtons();

		for (int i = 0; i < 4; i++) {
			panel.add(hand_buttons[i]);
		}

		panel.add(getInstructionsButton());

		return panel;
	}

	public JButton getStackButton() {
		pile_button = new JButton(pile.getImage());
		pile_button.setPreferredSize(new Dimension(50, 67));
		pile_button.setMargin(new Insets(0, 0, 0, 0));
		pile_button.setBorderPainted(false);
		pile_button.setBackground(button_color);
		pile_button.addActionListener(this);
		pile_button.setActionCommand("pile");

		return pile_button;
	}

	public void generateHand() {
		if (grid_locked)
			return;

		if (cards_in_hand == 4)
			return;

		int cards_to_draw = 4 - cards_in_hand;

		for (int i = 0; i < cards_to_draw; i++) {
			if (pile.isEmpty())
				return;
			if (findEmptyIndexInHand() == -1)
				return;

			hand[findEmptyIndexInHand()] = pile.pop();
			cards_in_hand++;
		}
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
			hand_buttons[i] = new JButton();
			hand_buttons[i].setPreferredSize(new Dimension(50, 68));
			hand_buttons[i].setMargin(new Insets(0, 0, 0, 0));
			hand_buttons[i].setBackground(button_color);
			hand_buttons[i].setBorderPainted(false);
			hand_buttons[i].addActionListener(this);
			hand_buttons[i].setActionCommand("hand_card" + i);
		}
	}

	public void updateHandButtons() {
		for (int i = 0; i < hand.length; i++) {
			if (hand[i] != null)
				hand_buttons[i].setIcon(hand[i].getImage());
		}
	}

	public void handleDraw() {
		if (scored_this_turn)
			game_label.setText("             New Turn. Cards Remaining: " + cards_remaining);
		else {
			strikes++;
			game_label.setText("            +1 Strike. Cards Remaining: " + cards_remaining);
		}

		if (selected != -1)
			deselect((int) selected / 3, selected % 3);

		generateHand();
		updateHandButtons();
		scored_this_turn = false;
	}

	public void handleClickHandCard(char card_clicked) {
		int n = Character.getNumericValue(card_clicked);

		if (hand[n] == null)
			return;

		if (grid_locked) {
			removeHandCard(n);
			grid_locked = false;
			game_label.setText("         Card Discarded. Cards Remaining: " + cards_remaining);
			return;
		}

		if (hand[n].patternMatching(board)) {
			points += hand[n].getPoints();
			removeHandCard(n);
			game_label.setText("           Card Scored. Cards Remaining: " + cards_remaining);
			scored_this_turn = true;
		}
	}

	public void removeHandCard(int n) {
		hand[n] = null;
		hand_buttons[n].setIcon(createImageIcon(""));
		cards_in_hand--;
		cards_remaining--;
	}

	public JPanel getP4() {
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 50));

		JButton reset_button = new JButton("Reset");
		reset_button.setPreferredSize(new Dimension(50, 25));
		reset_button.setBackground(button_color);
		reset_button.setForeground(Color.white);
		reset_button.setMargin(new Insets(0, 0, 0, 0));
		reset_button.setBorderPainted(false);
		reset_button.addActionListener(this);
		reset_button.setActionCommand("reset");

		panel.add(reset_button);

		return panel;
	}

	public JButton getInstructionsButton() {
		JButton instructions_button = new JButton(createImageIcon("pics/instrB2.png"));
		instructions_button.setPreferredSize(new Dimension(50, 70));
		instructions_button.setMargin(new Insets(0, 0, 0, 0));
		instructions_button.setBorderPainted(false);
		instructions_button.addActionListener(this);
		instructions_button.setActionCommand("instructions");

		return instructions_button;
	}

	public void redraw() {
		updateBlockButtons();
		points_label.setText("Points: " + points);
		strikes_label.setText("Strikes: " + strikes + "/4");
	}

	public void reset() {
		strikes = 0;
		cards_in_hand = 0;
		cards_remaining = 16;
		selected = -1;
		points = 0;
		grid_locked = false;
		scored_this_turn = false;

		board = generateNewGrid();
		updateBlockButtons();

		game_label.setText("                Welcome to Shifting Stones!                ");

		pile = new CardStack();
		hand = new PatternCard[4];
		generateHand();
		updateHandButtons();

		redraw();
	}

	public boolean won() {
		if (cards_remaining == 0)
			return true;
		return false;
	}

	public void gameWon() {
		JOptionPane.showMessageDialog(null, "Completed game with " + points + " points!", "Completed",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean lost() {
		if (strikes == 4)
			return true;
		return false;
	}

	public void gameLost() {
		JOptionPane.showMessageDialog(null, "Reached 4 Strikes! Restart.", "Not Completed",
				JOptionPane.INFORMATION_MESSAGE);
		reset();
	}

	public void actionPerformed(ActionEvent e) {
		String action_command = e.getActionCommand();

		if (action_command.startsWith("screen")) {
			char last_char = action_command.charAt(action_command.length() - 1);
			cdLayout.show(p_card, "" + last_char);
		} else if (action_command.equals("instructions")) {
			cdLayout.show(p_card, "1");
		} else if (action_command.equals("reset")) {
			reset();
		} else if (action_command.startsWith("block")) {
			handleClickBlock(action_command.charAt(5));
		} else if (action_command.equals("pile")) {
			handleDraw();
		} else if (action_command.startsWith("hand_card")) {
			handleClickHandCard(action_command.charAt(9));
		}

		redraw();

		if (lost())
			gameLost();
		else if (won())
			gameWon();
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}
}
