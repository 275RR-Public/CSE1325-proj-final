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

public class MainPanel extends JPanel {
	
	public MainPanel(ActionListener listener, MouseListener mlisten) {
		initComponents(listener, mlisten);
	}

	public JButton getPlayButton() {
        return this.play_button;
    }
	public JButton getPauseButton() {
        return this.pause_button;
    }
	public JComboBox<String> getVolumeCombo() {
		return this.volume_combo;
	}
	
	public JButton getStartButton() {
		return this.start_button;
	}
	public JMenuItem getStartItem() {
		return this.start_item;
	}
	public JButton getSaveButton() {
        return this.save_button;
    }
	public JMenuItem getSaveItem() {
		return this.save_item;
	}
	public JButton getLoadButton() {
		return this.load_button;
	}
	public JMenuItem getLoadItem() {
		return this.load_item;
	}
	public JButton getNewButton() {
		return this.new_button;
	}
	public JMenuItem getNewItem() {
		return this.new_item;
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
		//menu bar on top
        menu_bar = new JMenuBar();
        file_menu = new JMenu();
        start_item = new JMenuItem();
        new_item = new JMenuItem();
        load_item = new JMenuItem();
        save_item = new JMenuItem();
        exit_item = new JMenuItem();
        
		//music bar on top
        music_panel = new JPanel();
		music_bar = new JToolBar();
		prev_button = new JButton();
		play_button = new JButton();
		pause_button = new JButton();
		next_button = new JButton();
		volume_combo = new JComboBox<>();
        
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
		new_button = new JButton();
		load_button = new JButton();
		save_button = new JButton();
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

		//======== menu bar ========
        {
            //======== menu ========
            {
                start_item.setText("Start Game");
				start_item.addActionListener(listener);
				start_item.setEnabled(false);
                new_item.setText("New Character");
				new_item.addActionListener(listener);
				load_item.setText("Load Character");
                load_item.addActionListener(listener);
				save_item.setText("Save Character");
                save_item.addActionListener(listener);
                save_item.setEnabled(false);
                exit_item.setText("Exit");
				exit_item.addActionListener(listener);

                file_menu.setText("File");
				file_menu.add(start_item);
                file_menu.add(new_item);
                file_menu.add(load_item);
                file_menu.add(save_item);
                file_menu.add(exit_item);
            }
            menu_bar.add(file_menu);

            //======== music_panel ========
		    { 
            
                music_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));
                music_panel.setBackground(UIManager.getColor("MenuBar.background"));
            
                //======== music_bar ========
                {
                    
                    music_bar.setBackground(UIManager.getColor("MenuBar.background"));

                    //---- prev_button ----
                    prev_button.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.BACK_RES)));
                    prev_button.setBorder(new EmptyBorder(5, 10, 5, 10));
                    prev_button.setBackground(null);
                    prev_button.setToolTipText("Prev Music Track");
					prev_button.addActionListener(listener);
					prev_button.setActionCommand("MainPanel.prev");
                    music_bar.add(prev_button);

                    //---- play_button ----
                    play_button.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PLAY_RES)));
                    play_button.setBackground(null);
                    play_button.setBorder(new EmptyBorder(5, 10, 5, 10));
                    play_button.setToolTipText("Play Music");
					play_button.addActionListener(listener);
					play_button.setActionCommand("MainPanel.play");
					play_button.setVisible(false);
                    music_bar.add(play_button);

					//---- pause_button ----
                    pause_button.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PAUSE_RES)));
                    pause_button.setBackground(null);
                    pause_button.setBorder(new EmptyBorder(5, 10, 5, 10));
                    pause_button.setToolTipText("Pause Music");
					pause_button.addActionListener(listener);
					pause_button.setActionCommand("MainPanel.pause");
                    music_bar.add(pause_button);

                    //---- next_button ----
                    next_button.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.NEXT_RES)));
                    next_button.setBackground(null);
                    next_button.setBorder(new EmptyBorder(5, 10, 5, 10));
                    next_button.setToolTipText("Next Music Track");
					next_button.addActionListener(listener);
					next_button.setActionCommand("MainPanel.next");
                    music_bar.add(next_button);

                    //---- volume_combo ----
                    volume_combo.setMaximumRowCount(10);
                    volume_combo.setModel(new DefaultComboBoxModel<>(new String[] {
                        "10 %",
                        "20 %",
                        "30 %",
                        "40 %",
                        "50 %",
                        "60 %",
                        "70 %",
                        "80 %",
                        "90 %",
                        "100 %"
                    }));
					volume_combo.setSelectedIndex(Constants.DEFAULT_VOL_UI);
                    volume_combo.setBackground(null);
                    volume_combo.setToolTipText("Music Volume");
                    volume_combo.setPreferredSize(new Dimension(70, 23));
					volume_combo.addActionListener(listener);
					volume_combo.setActionCommand("MainPanel.volume");
                    music_bar.add(volume_combo);
                }
                music_panel.add(music_bar);

            }
            menu_bar.add(music_panel);
        }
        add(menu_bar, "north");
        
        //======== avatar_panel ========
		{   
            avatar_panel.setBorder(new TitledBorder(null, "PARTY", TitledBorder.LEFT, TitledBorder.TOP,
				new Font("Fira Sans", Font.BOLD, 13)));
			avatar_panel.setLayout(new BoxLayout(avatar_panel, BoxLayout.Y_AXIS));

			//======== avatar_horz1_panel ========
			{
				avatar_horz1_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
				avatar_horz1_panel.setMinimumSize(new Dimension(256, 128));

				{
					avatar_vert1_panel.setLayout(new BoxLayout(avatar_vert1_panel, BoxLayout.Y_AXIS));
					avatar_vert1_panel.setPreferredSize(new Dimension(128, 128));
					avatar_vert1_panel.setToolTipText("Party Member 1");
					avatar_vert1_panel.addMouseListener(mlisten);

					//---- avatar1_img_label ----
					avatar1_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
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
					avatar_vert2_panel.setToolTipText("Party Member 2");
					avatar_vert2_panel.addMouseListener(mlisten);

					//---- avatar2_img_label ----
					avatar2_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
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
					avatar_vert3_panel.setToolTipText("Party Member 3");
					avatar_vert3_panel.addMouseListener(mlisten);

					//---- avatar3_img_label ----
					avatar3_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
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
					avatar_vert4_panel.setToolTipText("Party Member 4");
					avatar_vert4_panel.addMouseListener(null);
					avatar_vert4_panel.addMouseListener(mlisten);

					//---- avatar4_img_label ----
					avatar4_img_label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
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

			//---- new_button ----
			new_button.setText("New Character");
            new_button.addActionListener(listener);
			new_button.setPreferredSize(new Dimension(135, 23));
			new_button.setMinimumSize(new Dimension(135, 23));
			new_button.setMaximumSize(new Dimension(135, 23));
			button_panel.add(new_button);

			//---- load_button ----
			load_button.setText("Load Character");
            load_button.addActionListener(listener);
			load_button.setPreferredSize(new Dimension(135, 23));
			load_button.setMinimumSize(new Dimension(135, 23));
			load_button.setMaximumSize(new Dimension(135, 23));
			button_panel.add(load_button);

			//---- save_button ----
			save_button.setText("Save Character");
            save_button.addActionListener(listener);
			save_button.setPreferredSize(new Dimension(135, 23));
			save_button.setMinimumSize(new Dimension(135, 23));
			save_button.setMaximumSize(new Dimension(135, 23));
			save_button.setEnabled(false);
			button_panel.add(save_button);

			//---- exit_button ----
			exit_button.setText("Exit");
            exit_button.addActionListener(listener);
			exit_button.setPreferredSize(new Dimension(135, 23));
			exit_button.setMinimumSize(new Dimension(135, 23));
			exit_button.setMaximumSize(new Dimension(135, 23));
			button_panel.add(exit_button);

            button_panel.add(Box.createVerticalGlue());
            button_panel.add(start_button);
            button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
            button_panel.add(new_button);
            button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
            button_panel.add(load_button);
            button_panel.add(Box.createRigidArea(new Dimension(0, 10)));
            button_panel.add(save_button);
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

    //menu bar on top
    private JMenuBar menu_bar;
    private JMenu file_menu;
    private JMenuItem start_item;
    private JMenuItem new_item;
    private JMenuItem load_item;
    private JMenuItem save_item;
    private JMenuItem exit_item;

    //music bar on top
    private JPanel music_panel;
	private JToolBar music_bar;
	private JButton prev_button;
	private JButton play_button;
	private JButton pause_button;
	private JButton next_button;
	private JComboBox<String> volume_combo;
    
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
	private JButton new_button;
	private JButton load_button;
	private JButton save_button;
	private JButton exit_button;

	//status on bottom
	private JLabel status_label;
}
