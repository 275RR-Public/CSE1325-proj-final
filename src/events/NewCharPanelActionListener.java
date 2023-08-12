package events;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controllers.GUIController;
import controllers.LoadSave;
import core.Avatar;
import core.Player;
import utils.Constants;
import utils.ErrorHandler;
import utils.GameUtility;

public class NewCharPanelActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent action_event) {
        String action_name = action_event.getActionCommand();
        switch (action_name) {
            case "NewCharPanel.Submit":
                ArrayList<Object> inner_list = new ArrayList<>();
                // pack list (player, boolean saved)
                inner_list.add(new Player(
                    GUIController.new_char_panel.getName(),
                    GUIController.new_char_panel.getAvatar(),
                    GUIController.new_char_panel.getWeapon(),
                	Constants.CREATURE_HP,
                    Constants.CREATURE_AC,
                    GUIController.new_char_panel.getStr(),
                    GUIController.new_char_panel.getDex(),
                    GUIController.new_char_panel.getCon()
                ));
                inner_list.add(false); //not saved to file
				// put list inside map (id, list)
				UUID id = UUID.randomUUID();
				LoadSave.players_keys.add(id);
				LoadSave.players.put(id, inner_list);
                
                GUIController.new_char_panel.clearFields();
                GUIController.main_panel.getStatusLabel().setText("Status: Click icon to remove from the party.");
                GUIController.showMainPanel();
                break;
            case "NewCharPanel.Cancel":
                GUIController.new_char_panel.clearFields();
                GUIController.showMainPanel();
                break;
			case "NewCharPanel.Random":
				int[] stats = new int[3];
				stats = GameUtility.randomStats(stats, Constants.STAT_POINTS);
				GUIController.new_char_panel.setStr(0);
				GUIController.new_char_panel.setDex(0);
				GUIController.new_char_panel.setCon(0);
				GUIController.new_char_panel.setStr(stats[0]);
				GUIController.new_char_panel.setDex(stats[1]);
				GUIController.new_char_panel.setCon(stats[2]);
				break;
            case "NewCharPanel.Avatar":
				//get all avatar images and add to JList model
				String res = Constants.IMG_PLAYER_RES;
				DefaultListModel<Avatar> model = new DefaultListModel<>();
				List<Path> paths = LoadSave.getResourcePaths(res);
				for(var path : paths) {
					String filename = path.getFileName().toString();
					Optional<BufferedImage> player_avatar = LoadSave.loadImage(res+filename);
					if(player_avatar.isPresent()) {
						BufferedImage img = player_avatar.get();
						model.addElement(new Avatar(filename, img));
					}
				}

				//init JList
				JList<Avatar> list = new JList<>(model);
				list.setSelectedIndex(0); //default selection
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
				list.setVisibleRowCount(-1); //tries to fit all elements on screen

				//set custom JList renderer to display icons
				list.setCellRenderer(new DefaultListCellRenderer() {
					@Override
					public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
						JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
						Avatar avatar = (Avatar) value;
						label.setText("");
						label.setIcon(avatar.getIcon());
						return label;
					}
				});

				//add JList to JScrollPane in case of overflow
				JScrollPane scroll_pane = new JScrollPane(list);
				scroll_pane.setPreferredSize(GUIController.new_char_panel.getPreferredSize());

				//use input if multiple data returns needed
				/* final JComponent[] inputs = new JComponent[] {
						scroll_pane
				}; */
				int result = JOptionPane.showConfirmDialog(GUIController.new_char_panel, scroll_pane, "Pick an Avatar", JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
                    GUIController.new_char_panel.setAvatar(list.getSelectedValue());
					changedAvatar(list.getSelectedValue().getFileName());
				} // else result = -1
				break;
        }
    }

    private void changedAvatar(String img_name) {
		GUIController.new_char_panel.setAvatarButtonText(img_name);
		GUIController.new_char_panel.setAvatarMsg("Avatar: Valid.");
		ErrorHandler.valid_avatar = true;
		ErrorHandler.validateForm(GUIController.new_char_panel.getSubmitButton());
	}
}
