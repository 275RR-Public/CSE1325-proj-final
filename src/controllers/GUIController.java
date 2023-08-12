package controllers;

import javax.swing.*;

import core.Creature;
import core.Player;
import events.CustomActionEvent;
import events.MainPanelActionListener;
import events.MainPanelMouseListener;
import utils.Constants;
import views.MainPanel;
import views.MonsterPanel;
import views.NewCharPanel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class GUIController {

    public static JFrame main_frame;
    public static NewCharPanel new_char_panel;
    public static MainPanel main_panel;
    public static MonsterPanel monster_panel;
    public static JOptionPane dialog;

    public static ArrayList<Object> avatar_elements = new ArrayList<>();
    public static ArrayList<Object> monster_elements = new ArrayList<>();

    public GUIController() {

        // Get minimal UI to screen
        main_frame = new JFrame("Game.java");
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main_frame.setSize(800, 600);

        main_panel = new MainPanel(new MainPanelActionListener(), new MainPanelMouseListener());

        main_frame.add(main_panel);
        main_frame.pack();
        main_frame.setVisible(true);
        main_frame.getContentPane().requestFocusInWindow();

        LoadSave.music = new MusicPlayer(Constants.DEFAULT_SONG, Constants.DEFAULT_VOL);
        
        // Continue loading Program
        MainWorker load_worker = new MainWorker();
        load_worker.execute();

        MonsterWorker monster_worker = new MonsterWorker();
        monster_worker.execute();
    }
    
    public static void showNewCharPanel() {
        if (new_char_panel.isShowing()) {
            return;
        }

        main_frame.remove(main_panel);
        main_frame.add(new_char_panel);
        main_frame.revalidate();  // recalculates the layout
        main_frame.repaint();
    }

    public static void showMainPanel() {
        /* if (game_panel.isShowing()) {
            return;
        } */
        if(LoadSave.players.size() <= 0) {
            main_panel.getStartButton().setEnabled(false);
            main_panel.getStartItem().setEnabled(false);
            main_panel.getSaveButton().setEnabled(false);
            main_panel.getSaveItem().setEnabled(false);
            main_panel.getStatusLabel().setText("Status: Add Characters to party."); 
        }
        else {
            int i = 0;
            for(var list : LoadSave.players.values()) {
                Player player = (Player) list.get(0);
                JLabel label = (JLabel) avatar_elements.get(i*2);
                JTextArea text = (JTextArea) avatar_elements.get((i*2)+1);
                label.setIcon(player.getAvatar().getIcon());
                text.setText(player.toString());
                text.setVisible(true);
                i++;  
            }

            if(LoadSave.players.size() >= 4) {
                main_panel.getNewButton().setEnabled(false);
                main_panel.getNewItem().setEnabled(false);
                main_panel.getLoadButton().setEnabled(false);
                main_panel.getLoadItem().setEnabled(false);
            }
            else {
                main_panel.getStartButton().setEnabled(true);
                main_panel.getStartItem().setEnabled(true);
                main_panel.getSaveButton().setEnabled(true);
                main_panel.getSaveItem().setEnabled(true);
                main_panel.getNewButton().setEnabled(true);
                main_panel.getNewItem().setEnabled(true);
                main_panel.getLoadButton().setEnabled(true);
                main_panel.getLoadItem().setEnabled(true);
            }
        }

        main_frame.remove(new_char_panel);
        main_frame.remove(monster_panel);
        main_frame.add(main_panel);
        main_frame.revalidate();  // recalculates the layout
        main_frame.repaint();
    }

    public static void showMonsterPanel() {
        /* if (game_panel.isShowing()) {
            return;
        } */
        if(LoadSave.monsters.size() <= 0) {
            monster_panel.getStartButton().setEnabled(false);
            monster_panel.getStatusLabel().setText("Status: Add Monsters to fight."); 
        }
        else {
            int i = 0;
            for(var monster : LoadSave.monsters) {
                JLabel label = (JLabel) monster_elements.get(i*2);
                JTextArea text = (JTextArea) monster_elements.get((i*2)+1);
                label.setIcon(monster.getAvatar().getIcon());
                text.setText(monster.toString());
                text.setVisible(true);
                i++;  
            }

            if(LoadSave.monsters.size() >= 4) {
                monster_panel.getLoadButton().setEnabled(false);
            }
            else {
                monster_panel.getStartButton().setEnabled(true);
                monster_panel.getLoadButton().setEnabled(true);
            }
        }

        main_frame.remove(main_panel);
        main_frame.add(monster_panel);
        main_frame.revalidate();  // recalculates the layout
        main_frame.repaint();
    }

    public static void resetAvatars() {
        int num_players = LoadSave.players.size();
        if(num_players <= 0) return;

        for(int i = 0; i < num_players; i++) {
            JLabel label = (JLabel) GUIController.avatar_elements.get(i*2);
            JTextArea text = (JTextArea) GUIController.avatar_elements.get((i*2)+1);
            label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE1_RES)));
            text.setVisible(false);
        }
    }

    public static void resetMonsters() {
        int num_monsters = LoadSave.monsters.size();
        if(num_monsters <= 0) return;

        for(int i = 0; i < num_monsters; i++) {
            JLabel label = (JLabel) GUIController.monster_elements.get(i*2);
            JTextArea text = (JTextArea) GUIController.monster_elements.get((i*2)+1);
            label.setIcon(new ImageIcon(LoadSave.getContext().getResource(Constants.PROFILE_RES)));
            text.setVisible(false);
        }
    }
    
    public static void trySaving(Optional<UUID> id) {
        
        //check passed player if saved
        if(id.isPresent()) {
            Boolean saved = (Boolean) LoadSave.players.get(id.get()).get(1);
            if(saved)
                return;
            else {
                ArrayList<Object> list = (ArrayList<Object>) LoadSave.players.get(id.get());
                askSave(list);
            }
        }
        //check all players if saved
        else {
            for(var list : LoadSave.players.values()) {
                if(list.contains(false)) { //found unsaved player
                    askSave(list);
                }
            }
        }
    }

    private static void askSave(ArrayList<Object> player_list) {
        Player player = (Player) player_list.get(0);
        int choice = JOptionPane.showConfirmDialog(GUIController.main_panel,
                player.getName() + " has not been saved.\nSave?",
                "Character Loss", JOptionPane.YES_NO_OPTION);
        if(choice == 0) {       //yes, save
            CustomActionEvent event = new CustomActionEvent(GUIController.main_panel,
                                    ActionEvent.ACTION_PERFORMED, "Save Character", player_list);
            for(ActionListener a: GUIController.main_panel.getSaveButton().getActionListeners()) {a.actionPerformed(event);}
        }
    }

    public static int displayJList(JList<ArrayList<Object>> jlist, String title_msg) {
        
        jlist.setSelectedIndex(0); //default selection
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jlist.setVisibleRowCount(-1); //tries to fit all elements on screen

        //set custom JList renderer to display icons
        jlist.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
                Creature creature = (Creature) ((ArrayList<Object>) value).get(0);
                label.setIcon(creature.getAvatar().getIcon());
                label.setText(creature.getName());
                return label;
            }
        });

        //add JList to JScrollPane in case of overflow
        JScrollPane scroll_pane = new JScrollPane(jlist);
        //scroll_pane.setPreferredSize(GUIController.main_panel.getPreferredSize());

        //use inputs if multiple data returns needed
        /* final JComponent[] inputs = new JComponent[] {
                scroll_pane
        }; */
        return JOptionPane.showConfirmDialog(GUIController.main_frame, scroll_pane, title_msg, JOptionPane.PLAIN_MESSAGE);
    }
}