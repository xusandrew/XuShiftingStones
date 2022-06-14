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

	JLabel strikes_label;
	JButton pics[] = new JButton[row * col];

	char[][] board = {
			{},
			{},
			{}
	};

	public void init() {
		p_card = new Panel();
		p_card.setLayout(cdLayout);
		generate_screens();
		resize(350, 500);
		setLayout(new BorderLayout());
		add("Center", p_card);
	}

	public void generate_screens() {
		for (int i = 0; i <= 6; i++) {
			cards[i] = instructions_screen(i);
			p_card.add("" + i, cards[i]);
		}

		game_screen();
	}

	public Panel instructions_screen(int num) {
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

		// gbc.gridy++;
		// game_card.add(get_space(1, 40, true), gbc);

		// gbc.gridy++;
		// card3.add(get_p2(), gbc);

		// gbc.gridy++;
		// card3.add(get_space(1, 40, true), gbc);

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
		grid.setPreferredSize(new Dimension(210, 210));


		char[][]grid_board = generate_new_grid();

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				JButton block = new JButton(createImageIcon("path")); // CHANGE
				block.setPreferredSize(new Dimension(70, 70));
				block.setMargin(new Insets(0, 0, 0, 0));
				block.setBorderPainted(false);

				grid.add(block);
			}
		}

		return grid;
	}

	public char[][] generate_new_grid() {


		int yb = 1;
		int or = 2;
		int pb = 3;
		int gg = 3;

		return null;
	}

	public void redraw() {

	}

	public void reset() {

	}

	public boolean win() {
		return false;
	}

	public void game_won() {
		selected = -1;
		JOptionPane.showMessageDialog(null, "Completed Sodoku!", "Completed",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean lost() {
		// if (lives <= 0)
		// 	return true;
		return false;
	}

	public void game_lost() {
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
