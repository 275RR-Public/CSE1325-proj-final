package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import controllers.GUIController;
import controllers.GameController;
import controllers.LoadSave;
import core.Monster;

public class MonsterPanelActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent action_event) {
        String action_name = action_event.getActionCommand();
        switch (action_name) {
            case "Start Game":
                new GameController();
                break;
            case "Load Monster":
                
                //check if any Monsters available to load from files
                if(LoadSave.loaded_monsters.size() == 0 || LoadSave.loaded_monsters == null) {
                    JOptionPane.showMessageDialog(GUIController.main_panel, "No Monsters found.");
                    break;
                }

                //add Monsters from files to JList model
                DefaultListModel<ArrayList<Object>> model = new DefaultListModel<>();
                for(var monster : LoadSave.loaded_monsters) {
                    //check that specific monster is NOT already loaded in memory
                    if(LoadSave.monsters.size() == 0 || LoadSave.monsters == null) {
                        //packing 1 monster to work with generic code
                        ArrayList<Object> inner_list = new ArrayList<>();
                        inner_list.add(monster);
                        model.addElement(inner_list);
                    }
                    else {
                        boolean skip = false;
                        for(var m: LoadSave.monsters) {
                            if(m.getName().equals(monster.getName()))
                                skip = true;
                        }
                        if(skip == false) {
                            ArrayList<Object> inner_list = new ArrayList<>();
                            inner_list.add(monster);
                            model.addElement(inner_list);
                        }
                    }
                }

                //init JList
                JList<ArrayList<Object>> jlist = new JList<>(model);
                //display JList
                int result = GUIController.displayJList(jlist, "Pick a Monster to Load");

                //process results
                if (result == JOptionPane.OK_OPTION) {
                    ArrayList<Object> inner_list = jlist.getSelectedValue();
                    // unpack
                    Monster monster = (Monster) inner_list.get(0);
                    LoadSave.monsters.add(monster);
                    //resets
                    model.clear();
                    GUIController.monster_panel.getStatusLabel().setText("Status: Click icon to remove a Monster.");
                    GUIController.showMonsterPanel();
                } //else result = -1
                break;
            case "Back":
                GUIController.showMainPanel();
                break;
        }
    }
}
