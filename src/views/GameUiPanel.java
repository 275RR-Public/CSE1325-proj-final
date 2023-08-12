package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import controllers.LoadSave;
import net.miginfocom.swing.*;
import utils.Constants;

public class GameUiPanel extends JPanel {
	public GameUiPanel() {
		initComponents();
	}


    public JLabel getCurrentAvatar() {
        return player_avatar;
    }
    public JTextArea getCurrentText() {
        return player_text;
    }
    public JTextArea getCurrentStatus() {
        return player_stats;
    }


    public JLabel getTargetAvatar() {
        return target_avatar;
    }
    public JTextArea getTargetText() {
        return target_text;
    }
    public JTextArea getTargetStatus() {
        return target_stats;
    }


    public JLabel getStatus() {
        return status_label;
    }

	private void initComponents() {
		ui_panel = new JPanel();
		player_panel = new JPanel();
        player_vert1_panel = new JPanel();
		player_avatar = new JLabel();
        player_text = new JTextArea();
		player_stats = new JTextArea();

		ui_panel2 = new JPanel();
		target_panel = new JPanel();
        target_vert1_panel = new JPanel();
		target_avatar = new JLabel();
        target_text = new JTextArea();
		target_stats = new JTextArea();

		ui_panel3 = new JPanel();
		help_panel = new JPanel();
		help_img = new JLabel();
		help_info = new JTextArea();
		help1_panel = new JPanel();
		help1_img = new JLabel();
		help1_info = new JTextArea();
		help2_panel = new JPanel();
		help2_img = new JLabel();
		help2_info = new JTextArea();
		help3_panel = new JPanel();
		help3_img = new JLabel();
		help3_info = new JTextArea();
		help4_panel = new JPanel();
		help4_img = new JLabel();
		help4_info = new JTextArea();

        status_label = new JLabel();

		setLayout(new MigLayout(
			"align center center",
			// columns
			"[fill]",
			// rows
			"[]" +
			"[]" +
			"[]"));

		//======== ui_panel ========
		{
			ui_panel.setBorder(new CompoundBorder(
				new EmptyBorder(5, 10, 5, 10),
				new TitledBorder(null, "CURRENT PLAYER", TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Fira Sans", Font.BOLD, 13))));
			ui_panel.setLayout(new FlowLayout());

			//======== player_panel ========
			{
				player_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
                player_panel.setMinimumSize(new Dimension(256, 128));

				//---- player_vert1_panel ----
                {
					player_vert1_panel.setLayout(new BoxLayout(player_vert1_panel, BoxLayout.Y_AXIS));
					player_vert1_panel.setPreferredSize(new Dimension(128, 128));

					//---- player_avatar ----
                    player_avatar.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
                    player_avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
                    player_vert1_panel.add(player_avatar);

					//---- player_text ----
					player_text.setText("");
					player_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					player_text.setTabSize(2);
					player_text.setEditable(false);
					//player_text.setWrapStyleWord(true);
					player_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					player_text.setVisible(false);
					player_vert1_panel.add(player_text);
				}
				player_panel.add(player_vert1_panel);

                //---- player_stats ----
                player_stats.setText("Name\nWeapon\nStr, Dex, Con");
                player_stats.setTabSize(2);
                player_stats.setEditable(false);
                //player_stats.setWrapStyleWord(true);
				player_panel.add(player_stats);
			}
			ui_panel.add(player_panel);
		}
		add(ui_panel, "cell 0 0");

		//======== ui_panel2 ========
		{
			ui_panel2.setBorder(new CompoundBorder(
				new EmptyBorder(5, 10, 5, 10),
				new TitledBorder(null, "CURRENT TARGET", TitledBorder.CENTER, TitledBorder.TOP,
					new Font("Fira Sans", Font.BOLD, 13))));
			ui_panel2.setLayout(new FlowLayout());

			//======== target_panel ========
			{
				target_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
                target_panel.setMinimumSize(new Dimension(256, 128));

                //---- target_vert1_panel ----
                {
					target_vert1_panel.setLayout(new BoxLayout(target_vert1_panel, BoxLayout.Y_AXIS));
					target_vert1_panel.setPreferredSize(new Dimension(128, 128));

					//---- target_avatar ----
                    target_avatar.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
                    target_avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
                    target_vert1_panel.add(target_avatar);

					//---- target_text ----
					target_text.setText("");
					target_text.setFont(new Font("Fira Sans", Font.PLAIN, 10));
					target_text.setTabSize(2);
					target_text.setEditable(false);
					//target_text.setWrapStyleWord(true);
					target_text.setAlignmentX(Component.CENTER_ALIGNMENT);
					target_text.setVisible(false);
					target_vert1_panel.add(target_text);
				}
				target_panel.add(target_vert1_panel);

				//---- target_stats ----
				target_stats.setText("Name\nWeapon\nStr, Dex, Con");
				target_stats.setTabSize(2);
				target_stats.setEditable(false);
				//target_stats.setWrapStyleWord(true);
				target_panel.add(target_stats);
			}
			ui_panel2.add(target_panel);
		}
		add(ui_panel2, "cell 0 1");

		//======== ui_panel3 ========
		{
			ui_panel3.setBorder(new CompoundBorder(
				new EmptyBorder(5, 10, 5, 10),
				new TitledBorder(null, "HELP", TitledBorder.CENTER, TitledBorder.TOP,
					new Font("Fira Sans", Font.BOLD, 13))));
			ui_panel3.setLayout(new BoxLayout(ui_panel3, BoxLayout.Y_AXIS));

			//======== help_panel ========
			{
				help_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

				//---- help_img ----
				help_img.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.KEY_T_RES)));
				help_panel.add(help_img);

				//---- help_info ----
				help_info.setText("\"T\" to select the next target.");
				help_info.setTabSize(2);
				help_info.setEditable(false);
				//help_info.setWrapStyleWord(true);
				help_panel.add(help_info);
			}
			ui_panel3.add(help_panel);

			//======== help1_panel ========
			{
				help1_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

				//---- help_img ----
				help1_img.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.KEY_A_RES)));
				help1_panel.add(help1_img);

				//---- help_info ----
				help1_info.setText("\"A\" to attack selected target.");
				help1_info.setTabSize(2);
				help1_info.setEditable(false);
				//help_info.setWrapStyleWord(true);
				help1_panel.add(help1_info);
			}
			ui_panel3.add(help1_panel);

			//======== help2_panel ========
			{
				help2_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

				//---- help_img ----
				help2_img.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.KEY_D_RES)));
				help2_panel.add(help2_img);

				//---- help_info ----
				help2_info.setText("\"D\" to disarm selected target.");
				help2_info.setTabSize(2);
				help2_info.setEditable(false);
				//help_info.setWrapStyleWord(true);
				help2_panel.add(help2_info);
			}
			ui_panel3.add(help2_panel);

			//======== help3_panel ========
			{
				help3_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

				//---- help_img ----
				help3_img.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.KEY_SPACE_RES)));
				help3_panel.add(help3_img);

				//---- help_info ----
				help3_info.setText("\"Space\" to end turn.");
				help3_info.setTabSize(2);
				help3_info.setEditable(false);
				//help_info.setWrapStyleWord(true);
				help3_panel.add(help3_info);
			}
			ui_panel3.add(help3_panel);

			//======== help4_panel ========
			{
				help4_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

				//---- help_img ----
				help4_img.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.MOUSE_RES)));
				help4_panel.add(help4_img);

				//---- help_info ----
				help4_info.setText("\"Drag and Drop\" to move.");
				help4_info.setTabSize(2);
				help4_info.setEditable(false);
				//help_info.setWrapStyleWord(true);
				help4_panel.add(help4_info);
			}
			ui_panel3.add(help4_panel);
		}
		add(ui_panel3, "cell 0 2");

        //---- status_label ----
		status_label.setText("Status:");
        status_label.setBorder(new EmptyBorder(0, 5, 0, 5));
		add(status_label, "south");
	}

    private JPanel ui_panel;
	private JPanel player_panel;
    private JPanel player_vert1_panel;
	private JLabel player_avatar;
    private JTextArea player_text;
	private JTextArea player_stats;

	private JPanel ui_panel2;
	private JPanel target_panel;
    private JPanel target_vert1_panel;
	private JLabel target_avatar;
    private JTextArea target_text;
	private JTextArea target_stats;

	private JPanel ui_panel3;
	private JPanel help_panel;
	private JLabel help_img;
	private JTextArea help_info;
	private JPanel help1_panel;
	private JLabel help1_img;
	private JTextArea help1_info;
	private JPanel help2_panel;
	private JLabel help2_img;
	private JTextArea help2_info;
	private JPanel help3_panel;
	private JLabel help3_img;
	private JTextArea help3_info;
	private JPanel help4_panel;
	private JLabel help4_img;
	private JTextArea help4_info;

    private JLabel status_label;
}


