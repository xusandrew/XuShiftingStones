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

	int row = 9;
	int col = 9;
	int selected = -1;
	int level = 0;
	int lives = 3;
	int screen = 1;

	JLabel level_label, lives_label;
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
			color = new Color(229, 241, 207);
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
		game_card.setBackground(new Color(229, 241, 207));

		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.VERTICAL;

		JLabel title = new JLabel("Shifting Stones");
		title.setFont(new Font("Arial", Font.BOLD, 40));

		gbc.gridx = 0;
		gbc.gridy = 0;

		// gbc.gridy++;
		// card3.add(get_p1(), gbc);

		// gbc.gridy++;
		// card3.add(get_grid(), gbc);

		// gbc.gridy++;
		// card3.add(get_space(1, 40, true), gbc);

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



		return new JPanel();
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
		if (lives <= 0)
			return true;
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
