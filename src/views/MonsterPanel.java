// JFormDesigner - https://www.formdev.com/

package views;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.*;

import controllers.LoadSave;
import net.miginfocom.swing.*;
import utils.Constants;

public class MonsterPanel extends JPanel {
	
	public MonsterPanel(ActionListener listener, MouseListener mlisten) {
		initComponents(listener, mlisten);
	}
	
	public JButton getStartButton() {
		return this.start_button;
	}
	public JButton getLoadButton() {
		return this.load_button;
	}

	public JLabel getAvatar1Image() {
		return this.avatar1_img_label;
	}
	public JTextArea getAvatar1Text() {
		return this.avatar1_text;
	}
	public JLabel getAvatar2Image() {
		return this.avatar2_img_label;
	}
	public JTextArea getAvatar2Text() {
		return this.avatar2_text;
	}
	public JLabel getAvatar3Image() {
		return this.avatar3_img_label;
	}
	public JTextArea getAvatar3Text() {
		return this.avatar3_text;
	}
	public JLabel getAvatar4Image() {
		return this.avatar4_img_label;
	}
	public JTextArea getAvatar4Text() {
		return this.avatar4_text;
	}

	public JPanel getAvatar1Panel() {
		return this.avatar_vert1_panel;
	}
	public JPanel getAvatar2Panel() {
		return this.avatar_vert2_panel;
	}
	public JPanel getAvatar3Panel() {
		return this.avatar_vert3_panel;
	}
	public JPanel getAvatar4Panel() {
		return this.avatar_vert4_panel;
	}

	public JLabel getStatusLabel() {
		return this.status_label;
	}
    
    private void initComponents(ActionListener listener, MouseListener mlisten) {
        
		//avatars on left
        avatar_panel = new JPanel();
		avatar_horz1_panel = new JPanel();
		avatar_vert1_panel = new JPanel();
		avatar1_img_label = new JLabel();
		avatar1_text = new JTextArea();
		avatar_vert2_panel = new JPanel();
		avatar2_img_label = new JLabel();
		avatar2_text = new JTextArea();
		avatar_horz2_panel = new JPanel();
		avatar_vert3_panel = new JPanel();
		avatar3_img_label = new JLabel();
		avatar3_text = new JTextArea();
		avatar_vert4_panel = new JPanel();
		avatar4_img_label = new JLabel();
		avatar4_text = new JTextArea();

		//buttons on right
        button_panel = new JPanel();
		start_button = new JButton();
		load_button = new JButton();
		exit_button = new JButton();

		//status on bottom
		status_label = new JLabel();

		setLayout(new MigLayout(
			"align center center,gap 30 30",
			// columns
			"[fill]" +
			"[124,fill]" +
			"[fill]" +
			"[fill]",
			// rows
			"[]" +
			"[]" +
			"[]"));
        
        //======== avatar_panel ========
		{   
            avatar_panel.setBorder(new TitledBorder(null, "MONSTERS", TitledBorder.LEFT, TitledBorder.TOP,
				new Font("Fira Sans", Font.BOLD, 13)));
			avatar_panel.setLayout(new BoxLayout(avatar_panel, BoxLayout.Y_AXIS));

			//======== avatar_horz1_panel ========
			{
				avatar_horz1_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
				avatar_horz1_panel.setMinimumSize(new Dimension(256, 128));

				{
					avatar_vert1_panel.setLayout(new BoxLayout(avatar_vert1_panel, BoxLayout.Y_AXIS));
					avatar_vert1_panel.setPreferredSize(new Dimension(128, 128));
					avatar_vert1_panel.setToolTipText("Monster 1");
					avatar_vert1_panel.addMouseListener(mlisten);

					//---- avatar1_img_label ----
					avatar1_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE_RES)));
					avatar1_img_label.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar_vert1_panel.add(avatar1_img_label);

					//---- avatar1_label ----
					avatar1_text.setText("");
					avatar1_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					avatar1_text.setTabSize(2);
					avatar1_text.setEditable(false);
					//avatar1_text.setWrapStyleWord(true);
					avatar1_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar1_text.setVisible(false);
					avatar_vert1_panel.add(avatar1_text);
				}
				avatar_horz1_panel.add(avatar_vert1_panel);

				{
					avatar_vert2_panel.setLayout(new BoxLayout(avatar_vert2_panel, BoxLayout.Y_AXIS));
					avatar_vert2_panel.setPreferredSize(new Dimension(128, 128));
					avatar_vert2_panel.setToolTipText("Monster 2");
					avatar_vert2_panel.addMouseListener(mlisten);

					//---- avatar2_img_label ----
					avatar2_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE_RES)));
					avatar2_img_label.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar_vert2_panel.add(avatar2_img_label);

					//---- avatar2_label ----
					avatar2_text.setText("");
					avatar2_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					avatar2_text.setTabSize(2);
					avatar2_text.setEditable(false);
					avatar2_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar2_text.setVisible(false);
					avatar_vert2_panel.add(avatar2_text);
				}
				avatar_horz1_panel.add(avatar_vert2_panel);

			}
			avatar_panel.add(avatar_horz1_panel);

			//======== avatar_horz2_panel ========
			{
				avatar_horz2_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
				avatar_horz2_panel.setMinimumSize(new Dimension(256, 128));

				{
					avatar_vert3_panel.setLayout(new BoxLayout(avatar_vert3_panel, BoxLayout.Y_AXIS));
					avatar_vert3_panel.setPreferredSize(new Dimension(128, 128));
					avatar_vert3_panel.setToolTipText("Monster 3");
					avatar_vert3_panel.addMouseListener(mlisten);

					//---- avatar3_img_label ----
					avatar3_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE_RES)));
					avatar3_img_label.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar_vert3_panel.add(avatar3_img_label);

					//---- avatar3_label ----
					avatar3_text.setText("");
					avatar3_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					avatar3_text.setTabSize(2);
					avatar3_text.setEditable(false);
					avatar3_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar3_text.setVisible(false);
					avatar_vert3_panel.add(avatar3_text);
				}
				avatar_horz2_panel.add(avatar_vert3_panel);

				{
					avatar_vert4_panel.setLayout(new BoxLayout(avatar_vert4_panel, BoxLayout.Y_AXIS));
					avatar_vert4_panel.setPreferredSize(new Dimension(128, 128));
					avatar_vert4_panel.setToolTipText("Monster 4");
					avatar_vert4_panel.addMouseListener(null);
					avatar_vert4_panel.addMouseListener(mlisten);

					//---- avatar4_img_label ----
					avatar4_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE_RES)));
					avatar4_img_label.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar_vert4_panel.add(avatar4_img_label);

					//---- avatar4_label ----
					avatar4_text.setText("");
					avatar4_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					avatar4_text.setTabSize(2);
					avatar4_text.setEditable(false);
					avatar4_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					avatar4_text.setVisible(false);
					avatar_vert4_panel.add(avatar4_text);
				}
				avatar_horz2_panel.add(avatar_vert4_panel);
			}
			avatar_panel.add(avatar_horz2_panel);
		}
		add(avatar_panel, "cell 1 1");

		//======== button_panel ========
		{
			button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));

			//---- start_button ----
			start_button.setText("Start Game");
            start_button.addActionListener(listener);
			start_button.setPreferredSize(new Dimension(135, 23));
			start_button.setMinimumSize(new Dimension(135, 23));
			start_button.setMaximumSize(new Dimension(135, 23));
			start_button.setEnabled(false);
			button_panel.add(start_button);

			//---- load_button ----
			load_button.setText("Load Monster");
            load_button.addActionListener(listener);
			load_button.setPreferredSize(new Dimension(135, 23));
			load_button.setMinimumSize(new Dimension(135, 23));
			load_button.setMaximumSize(new Dimension(135, 23));
			button_panel.add(load_button);

			//---- exit_button ----
			exit_button.setText("Back");
            exit_button.addActionListener(listener);
			exit_button.setPreferredSize(new Dimension(135, 23));
			exit_button.setMinimumSize(new Dimension(135, 23));
			exit_button.setMaximumSize(new Dimension(135, 23));
			button_panel.add(exit_button);

            button_panel.add(Box.createVerticalGlue());
            button_panel.add(start_button);
            button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
            button_panel.add(load_button);
            button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
            button_panel.add(exit_button);
            button_panel.add(Box.createVerticalGlue());
		}
		add(button_panel, "cell 2 1");

        //---- status_label ----
		status_label.setText("Status:");
		status_label.setPreferredSize(new Dimension(290, 17));
		status_label.setHorizontalAlignment(SwingConstants.LEFT);
		add(status_label, "south,gapx 10 10,gapy 10 10");
	}
    
    //party panel on left
	private JPanel avatar_panel;
	private JPanel avatar_horz1_panel;
	private JPanel avatar_vert1_panel;
	private JLabel avatar1_img_label;
	private JTextArea avatar1_text;
	private JPanel avatar_vert2_panel;
	private JLabel avatar2_img_label;
	private JTextArea avatar2_text;
	private JPanel avatar_horz2_panel;
	private JPanel avatar_vert3_panel;
	private JLabel avatar3_img_label;
	private JTextArea avatar3_text;
	private JPanel avatar_vert4_panel;
	private JLabel avatar4_img_label;
	private JTextArea avatar4_text;

    //button panel on right
	private JPanel button_panel;
	private JButton start_button;
	private JButton load_button;
	private JButton exit_button;

	//status on bottom
	private JLabel status_label;
}

