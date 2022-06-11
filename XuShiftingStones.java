// Name: Andrew Xu
// Date: 6/07/22
// Purpose: Shifting Stones Card Game

import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class XuShiftingStones extends Applet implements ActionListener {
	Panel p_card;
	Panel card1, card2, card3, card4, card5; // the two screens
	CardLayout cdLayout = new CardLayout();

	int row = 9;
	int col = 9;
	int selected = -1;
	int level = 0;
	int lives = 3;

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
		screen1();
		screen2();
		screen3();
		resize(350, 500);
		setLayout(new BorderLayout());
		add("Center", p_card);
	}

	public void screen1() {
		card1 = new Panel();
		JButton bkg = new JButton(createImageIcon("background.jpg"));
		bkg.setPreferredSize(new Dimension(350, 500));
		bkg.setActionCommand("s2");
		bkg.addActionListener(this);
		card1.add(bkg);
		p_card.add("1", card1);
	}

	public void screen2() {
		card2 = new Panel();
		JButton bkg = new JButton(createImageIcon("instructions.jpg"));
		bkg.setPreferredSize(new Dimension(350, 500));
		bkg.setActionCommand("s3");
		bkg.addActionListener(this);
		card2.add(bkg);
		p_card.add("2", card2);
	}

	public void screen3() {
		card3 = new Panel();
		card3.setBackground(new Color(205, 224, 238));

		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.VERTICAL;

		JLabel title = new JLabel("Sodoku");
		title.setFont(new Font("Arial", Font.BOLD, 40));

		// gbc.gridx = 0;
		// gbc.gridy = 0;
		// card3.add(title, gbc);

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

		// p_card.add("3", card3);
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
		JOptionPane.showMessageDialog(null, "Completed Sodoku!","Completed",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean lost() {
		if (lives <= 0)
			return true;
		return false;
	}

	public void game_lost() {
		selected = -1;
		JOptionPane.showMessageDialog(null, "Ran out of lives!","Not Completed",
				JOptionPane.INFORMATION_MESSAGE);
		reset();
	}

	public void actionPerformed(ActionEvent e) {
		String action_command = e.getActionCommand();

		
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}
}
