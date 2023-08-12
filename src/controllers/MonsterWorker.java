package controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import events.MonsterPanelActionListener;
import events.MonsterPanelMouseListener;
import views.MonsterPanel;

public class MonsterWorker extends SwingWorker<Void, String> {

    String msg;
    
    @Override
    protected Void doInBackground() throws Exception {
        
        publish(LoadSave.loadBackgroundImages());
        
        try {
            msg = LoadSave.loadMonsters();
            publish(msg);
        } catch (IOException e) {
            System.out.println("Worker: err load monsters");
            msg = e.getMessage();
            publish(msg);
        }

        return null;
    }

    @Override
    protected void process(List<String> msgs) {
        // update GUI while working
        for(var msg : msgs) {
            GUIController.main_panel.getStatusLabel().setText("Status: " + msg);
        }
    }

    @Override
    protected void done() {

        // Update GUI after file operation completes
        publish("All files successfully loaded.");
        try {
            get(); //check with SwingWorker if any exceptions thrown
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        // finish UI loading after file loading complete
        GUIController.monster_panel = new MonsterPanel(new MonsterPanelActionListener(), new MonsterPanelMouseListener());
            
        // load monster_panel avatar getters
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar1Image());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar1Text());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar2Image());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar2Text());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar3Image());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar3Text());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar4Image());
        GUIController.monster_elements.add(GUIController.monster_panel.getAvatar4Text());
    }

}
