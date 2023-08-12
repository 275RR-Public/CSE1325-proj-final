package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import controllers.GUIController;
import controllers.LoadSave;
import core.Player;

public class MainPanelActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent action_event) {
        String action_name = action_event.getActionCommand();
        switch (action_name) {
            case "MainPanel.prev":
                LoadSave.music.prev();
                GUIController.main_panel.getPauseButton().setVisible(true);
                GUIController.main_panel.getPlayButton().setVisible(false);
                GUIController.showMainPanel();
                break;
            case "MainPanel.play":
                LoadSave.music.resumeAudio();
                GUIController.main_panel.getPauseButton().setVisible(true);
                GUIController.main_panel.getPlayButton().setVisible(false);
                GUIController.showMainPanel();
                break;
            case "MainPanel.pause":
                LoadSave.music.pause();
                GUIController.main_panel.getPauseButton().setVisible(false);
                GUIController.main_panel.getPlayButton().setVisible(true);
                GUIController.showMainPanel();
                break;
            case "MainPanel.next":
                LoadSave.music.next();
                GUIController.main_panel.getPauseButton().setVisible(true);
                GUIController.main_panel.getPlayButton().setVisible(false);
                GUIController.showMainPanel();
                break;
            case "MainPanel.volume":
                String vol_item = (String) GUIController.main_panel.getVolumeCombo().getSelectedItem(); // "10 %"
                float vol = Float.parseFloat(vol_item.split(" ")[0]); // 10.0
                float volume = vol / 100; // .10
                LoadSave.music.setVolume(volume); 
                break;
            case "Start Game":
                GUIController.showMonsterPanel();
                break;
            case "New Character":
                GUIController.showNewCharPanel();
                break;
            case "Load Character":
                
                //check if any Players available to load from files
                if(LoadSave.loaded_players.size() == 0 || LoadSave.loaded_players == null) {
                    JOptionPane.showMessageDialog(GUIController.main_panel, "No Characters saved.");
                    break;
                }

                //add Players from files to JList model
                DefaultListModel<ArrayList<Object>> model = new DefaultListModel<>();
                for(var player : LoadSave.loaded_players) {
                    //check that player is NOT already loaded in memory
                    if(LoadSave.players.size() == 0 || LoadSave.players == null) {
                        ArrayList<Object> inner_list = new ArrayList<>();
                        // pack list (player, boolean saved)
                        inner_list.add(player);
                        inner_list.add(true); //saved to file
                        model.addElement(inner_list);
                    }
                    else {
                        boolean skip = false;
                        for(var list : LoadSave.players.values()) {
                            Player p = (Player) list.get(0);
                            if(p.getName().equals(player.getName()))
                                skip = true;
                        }
                        if(skip == false) {
                            ArrayList<Object> inner_list = new ArrayList<>();
                            // pack list (player, boolean saved)
                            inner_list.add(player);
                            inner_list.add(true); //saved to file
                            model.addElement(inner_list);
                        }
                    }
                }
                // all possible characters to load are loaded (all were skipped)
                if(model.size() == 0) {
                    JOptionPane.showMessageDialog(GUIController.main_panel, "All saved characters are loaded.");
                    break;
                }

                //init JList
                JList<ArrayList<Object>> jlist = new JList<>(model);
                //display JList
                int result = GUIController.displayJList(jlist, "Pick a Character to Load");

                //process results
                if (result == JOptionPane.OK_OPTION) {
                    ArrayList<Object> inner_list = jlist.getSelectedValue();
                    // put list inside map (id, list)
                    UUID id = UUID.randomUUID();    //generate unique key
                    LoadSave.players_keys.add(id);  //save key in order
                    LoadSave.players.put(id, inner_list); //save players in order
                    //resets
                    model.clear();
                    GUIController.main_panel.getStatusLabel().setText("Status: Click icon to remove from the party.");
                    GUIController.showMainPanel();
                } //else result = -1
                break;
            case "Save Character":
                //received save request when exiting
                if(action_event instanceof CustomActionEvent) {
                    CustomActionEvent evt = (CustomActionEvent) action_event;
                    ArrayList<Object> player_list = (ArrayList<Object>) evt.getPassedObject();
                    Player player = (Player) player_list.get(0);

                    try {    
                        LoadSave.savePlayer(player);
                        JOptionPane.showMessageDialog(GUIController.main_panel, player.getName() + " saved.");
                        player_list.set(1, true); //record player has been saved
                    } catch(IOException ex) {
                        JOptionPane.showMessageDialog(GUIController.main_panel, ex);
                    }
                    break;
                }

                //check if any Players available in memory
                if(LoadSave.players.size() == 0 || LoadSave.players == null) {
                    JOptionPane.showMessageDialog(GUIController.main_panel, "No Characters available to save.");
                    break;
                }

                //add Players in memory to JList model
                DefaultListModel<ArrayList<Object>> mem_model = new DefaultListModel<>();
                Boolean all_saved = true;
                for(var player_list : LoadSave.players.values()) {
                    //only add players NOT already saved
                    Boolean saved = (Boolean) player_list.get(1);
                    if(!saved) {
                        all_saved = false;
                        mem_model.addElement(player_list);
                    }
                }
                if(all_saved) {
                    JOptionPane.showMessageDialog(GUIController.main_panel, "No Unsaved Characters.");
                    break;
                }

                //init JList
                JList<ArrayList<Object>> jlist1 = new JList<>(mem_model);
                //display JList
                int result1 = GUIController.displayJList(jlist1, "Pick a Character to Save");
                //process results
                if (result1 == JOptionPane.OK_OPTION) {
                    ArrayList<Object> player_list = jlist1.getSelectedValue();
                    Player player = (Player) player_list.get(0);
                    try {
                        LoadSave.savePlayer(player);
                        JOptionPane.showMessageDialog(GUIController.main_panel, player.getName() + " saved.");
                        player_list.set(1, true); //record player has been saved
                    } catch(IOException ex) {
                        JOptionPane.showMessageDialog(GUIController.main_panel, ex);
                    }
                    //resets
                    mem_model.clear();
                } //else result = -1
                break;
            case "Exit":
                GUIController.trySaving(Optional.ofNullable(null)); //ask to save if not saved
                GUIController.main_frame.dispose();
                break;
        }
    }
}
