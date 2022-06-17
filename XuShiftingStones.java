// Name: Andrew Xu
// Date: 6/07/22
// Purpose: Shifting Stones Card Game

import javax.smartcardio.Card;
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

	JLabel strikes_label;
	JLabel game_label;

	PatternCard[] hand;

	char[][] board = {
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

		game_screen();
	}

	public Panel instructionsScreen(int num) {
		Panel output = new Panel();

		ImageIcon image;
		Color color;
		if (num == 0){
			image = createImageIcon("pics/background.jpg");
			color = new Color(48,53,52);
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

	public void game_screen() {
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
		game_card.add(get_p1(), gbc);

		gbc.gridy++;
		game_card.add(get_grid(), gbc);

		gbc.gridy++;
		game_card.add(get_p2(), gbc);

		// gbc.gridy++;
		// card3.add(get_p3(), gbc);

		// gbc.gridy++;
		// card3.add(get_space(1, 40, true), gbc);

		// gbc.gridy++;
		// card3.add(get_p4(), gbc);

		p_card.add("7", game_card);
	}

	public JPanel get_p1(){
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 25));

		strikes_label = new JLabel("Strikes: " + strikes + "/4");
		panel.add(strikes_label);

		return panel; 
	}

	public JPanel get_grid() {
		JPanel grid = new JPanel(new GridLayout(3,3));
		grid.setBackground(background_color);
		grid.setPreferredSize(new Dimension(225, 225));


		BlockCard[][]grid_board = generate_new_grid();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton block = new JButton(grid_board[i][j].getImage()); 
				block.setPreferredSize(new Dimension(75, 75));
				block.setMargin(new Insets(0, 0, 0, 0));
				block.setBorderPainted(false);

				grid.add(block);
			}
		}

		return grid;
	}

	public BlockCard[][] generate_new_grid() {

		BlockCard[][] g = default_grid();
		return shuffle_grid(g);
	}

	public BlockCard[][] default_grid(){
		String[] colors = {"blue", "blue", "blue","green","green","green","red","red","yellow"};

		BlockCard[][] answer = new BlockCard[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				answer[i][j] = new BlockCard(colors[i*3 + j]);
			}
		}

		return answer;
	}

	public BlockCard[][] shuffle_grid(BlockCard[][] gd){
		for (int i = 0; i < 100; i++){
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

	public JPanel get_p2(){
		JPanel panel = new JPanel();
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 50));

		game_label = new JLabel("Welcome to Shifting Stones!");
		panel.add(game_label);

		return panel;
	}

	public JPanel get_p3(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(background_color);
		panel.setPreferredSize(new Dimension(225, 25));

		// level_label = new JLabel("Level: " + (level + 1));
		// level_label.setPreferredSize(new Dimension(50, 25));

		// lives_label = new JLabel("Lives: " + lives + "/3");
		// lives_label.setPreferredSize(new Dimension(53, 25));

		// panel.add(level_label, BorderLayout.WEST);
		// panel.add(lives_label, BorderLayout.EAST);

		panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		return panel;
	}

	public CardStack getStack(){
		
		
		
		//return CardStack();

		return null;
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
		// 	return true;
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
