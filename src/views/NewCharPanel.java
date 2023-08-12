// JFormDesigner - https://www.formdev.com/

package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;

import core.Avatar;
import core.Weapon;

import java.util.List;
import java.util.Vector;

import net.miginfocom.swing.*;
import utils.Constants;
import utils.ErrorHandler;

public class NewCharPanel extends JPanel {
	
	//temp holder until form submit. saves reloading entire avatar on submit
	private Avatar chosen_avatar = null;
	
	public NewCharPanel(ActionListener listener, DocumentListener dlisten, ChangeListener clisten, List<Weapon> weapons) {
		initComponents(listener, dlisten, clisten, weapons);
	}

	public void clearFields() {
        name_field.setText("");
        name_msg.setText("");
		avatar_msg.setText("");
		avatar_button.setText("Choose an Avatar");
		chosen_avatar = null;
		ErrorHandler.valid_avatar = false;
		weapon_field.setSelectedIndex(0);
		str_field.setValue(0);
		dex_field.setValue(0);
		con_field.setValue(0);
		stats_msg.setText("Stat Points Remaining: " + Constants.STAT_POINTS);
    }

	public void setName(String s) {
		name_field.setText(s);
	}
	public void setAvatar(Avatar chosen_avatar) {
		this.chosen_avatar = chosen_avatar;
	}
	public void setAvatarButtonText(String s) {
		avatar_button.setText(s);
	}
	public void setAvatarMsg(String s) {
		avatar_msg.setForeground(new Color(0, 128, 0));
		avatar_msg.setText(s);
	}
	public void setWeapon(Weapon w) {
		weapon_field.setEditable(true);
		weapon_field.setSelectedItem(w);
		weapon_field.setEditable(false);
	}
	public void setStr(int i) {
		str_field.setValue(i);
	}
	public void setDex(int i) {
		dex_field.setValue(i);
	}
	public void setCon(int i) {
		con_field.setValue(i);
	}
	public void setStatsMsg(String s) {
		stats_msg.setText(s);
	}
	
	public JButton getSubmitButton() {
		return submit_button;
	}
	public String getName() {
        return name_field.getText();
    }
	public JLabel getNameMsg() {
		return name_msg;
	}
	public Avatar getAvatar() {
		return chosen_avatar;
	}
    public Weapon getWeapon() {
        return (Weapon) weapon_field.getSelectedItem();
    }
	public int getStr() {
		return (int) str_field.getValue();
	}
	public int getDex() {
		return (int) dex_field.getValue();
	}
	public int getCon() {
		return (int) con_field.getValue();
	}
	public Object getStrObj() {
		return str_field;
	}
	public Object getDexObj() {
		return dex_field;
	}
	public Object getConObj() {
		return con_field;
	}

	private void initComponents(ActionListener listener, DocumentListener dlisten, ChangeListener clisten, List<Weapon> weapons) {
		name_label = new JLabel();
		name_field = new JTextField();
		avatar_label = new JLabel();
		avatar_button = new JButton();
		weapon_label = new JLabel();
		weapon_field = new JComboBox<Weapon>(new Vector<Weapon>(weapons));
		stats_label = new JLabel();
		str_label = new JLabel();
		str_field = new JSpinner();
		dex_label = new JLabel();
		dex_field = new JSpinner();
		con_label = new JLabel();
		con_field = new JSpinner();
		random_button = new JButton();
		stats_msg = new JLabel();
		name_msg = new JLabel();
		avatar_msg = new JLabel();
		button_panel = new JPanel();
		submit_button = new JButton();
		cancel_button = new JButton();

		setLayout(new MigLayout(
			"align center center,gap 10 30",
			// columns
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]" +
			"[fill]",
			// rows
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]" +
			"[]"));

		//---- name_label ----
		name_label.setText("NAME");
		name_label.setFont(name_label.getFont().deriveFont(name_label.getFont().getStyle() | Font.BOLD));
		add(name_label, "cell 0 0");

		//---- name_field ----
		name_field.getDocument().addDocumentListener(dlisten);
		add(name_field, "cell 1 0 6 1");

		//---- avatar_label ----
		avatar_label.setText("AVATAR");
		avatar_label.setFont(avatar_label.getFont().deriveFont(avatar_label.getFont().getStyle() | Font.BOLD));
		add(avatar_label, "cell 0 1");

		//---- avatar_button ----
		avatar_button.setText("Choose an Avatar");
		avatar_button.addActionListener(listener);
		avatar_button.setActionCommand("NewCharPanel.Avatar");
		add(avatar_button, "cell 1 1 6 1");

		//---- weapon_label ----
		weapon_label.setText("WEAPON");
		weapon_label.setFont(weapon_label.getFont().deriveFont(weapon_label.getFont().getStyle() | Font.BOLD));
		add(weapon_label, "cell 0 2");

		//---- weapon_field ----
		add(weapon_field, "cell 1 2 6 1");

		//---- stats_label ----
		stats_label.setText("STATS");
		stats_label.setFont(stats_label.getFont().deriveFont(stats_label.getFont().getStyle() | Font.BOLD));
		add(stats_label, "cell 0 3");

		//---- str_label ----
		str_label.setText("STR:");
		add(str_label, "cell 1 3,alignx right,growx 0");

		//---- str_field ----
		str_field.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		str_field.setMinimumSize(new Dimension(50, 23));
		str_field.setPreferredSize(new Dimension(50, 23));
		str_field.addChangeListener(clisten);
		add(str_field, "cell 2 3");

		//---- dex_label ----
		dex_label.setText("DEX:");
		add(dex_label, "cell 3 3,alignx right,growx 0");

		//---- dex_field ----
		dex_field.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		dex_field.setMinimumSize(new Dimension(50, 23));
		dex_field.setPreferredSize(new Dimension(50, 23));
		dex_field.addChangeListener(clisten);
		add(dex_field, "cell 4 3");

		//---- con_label ----
		con_label.setText("CON:");
		add(con_label, "cell 5 3,alignx right,growx 0");

		//---- con_field ----
		con_field.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		con_field.setMinimumSize(new Dimension(50, 23));
		con_field.setPreferredSize(new Dimension(50, 23));
		con_field.addChangeListener(clisten);
		add(con_field, "cell 6 3");

		//---- random_button ----
		random_button.setText("Randomize Stats");
		random_button.addActionListener(listener);
		random_button.setActionCommand("NewCharPanel.Random");
		add(random_button, "cell 1 4 6 1");

		//---- stats_msg ----
		stats_msg.setText("Stat Points Remaining: " + Constants.STAT_POINTS);
		add(stats_msg, "south");

		//---- avatar_msg ----
		avatar_msg.setText("");
		add(avatar_msg, "south");

		//---- name_msg ----
		name_msg.setText("");
		add(name_msg, "south");

		//======== button_panel ========
		{
			button_panel.setLayout(new FlowLayout(FlowLayout.TRAILING));

			//---- submit_button ----
			submit_button.setText("Create");
			submit_button.addActionListener(listener);
			submit_button.setActionCommand("NewCharPanel.Submit");
			submit_button.setEnabled(false);
			button_panel.add(submit_button);

			//---- cancel_button ----
			cancel_button.setText("Cancel");
			cancel_button.addActionListener(listener);
        	cancel_button.setActionCommand("NewCharPanel.Cancel");
			button_panel.add(cancel_button);
		}
		add(button_panel, "cell 0 5 7 1");
	}
	
	private JLabel name_label;
	private JTextField name_field;

	private JLabel avatar_label;
	private JButton avatar_button;

	private JLabel weapon_label;
	private JComboBox<Weapon> weapon_field;

	private JLabel stats_label;
	private JLabel str_label;
	private JSpinner str_field;
	private JLabel dex_label;
	private JSpinner dex_field;
	private JLabel con_label;
	private JSpinner con_field;
	private JButton random_button;

	private JLabel stats_msg;
	private JLabel name_msg;
	private JLabel avatar_msg;

	private JPanel button_panel;
	private JButton submit_button;
	private JButton cancel_button;
}
