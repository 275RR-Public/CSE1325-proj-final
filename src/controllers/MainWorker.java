package controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import events.NewCharPanelActionListener;
import events.NewCharPanelChangeListener;
import events.NewCharPanelDocListener;
import views.NewCharPanel;

public class MainWorker extends SwingWorker<Void, String> {

    String msg;
    
    @Override
    protected Void doInBackground() throws Exception {

        try {
            msg = LoadSave.createGameDirs();
            publish(msg);
        } catch (IOException e) {
            System.out.println("Worker: err load dir");
            msg = e.getMessage();
            publish(msg);
        }

        try {
            msg = LoadSave.loadPlayers();
            publish(msg);
        } catch (IOException e) {
            System.out.println("Worker: err load players");
            msg = e.getMessage();
            publish(msg);
        }

        try {
            msg = LoadSave.loadWeapons();
            publish(msg);
        } catch (IOException e) {
            System.out.println("Worker: err load weapons");
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
        GUIController.new_char_panel = new NewCharPanel(new NewCharPanelActionListener(), new NewCharPanelDocListener(),
                                new NewCharPanelChangeListener(), LoadSave.loaded_weapons);
            
        // load main_panel avatar getters
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar1Image());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar1Text());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar2Image());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar2Text());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar3Image());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar3Text());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar4Image());
        GUIController.avatar_elements.add(GUIController.main_panel.getAvatar4Text());
    }

}
