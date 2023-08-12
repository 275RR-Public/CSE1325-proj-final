package events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;
import java.util.UUID;

import controllers.GUIController;
import controllers.LoadSave;

public class MainPanelMouseListener implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent mouse_event) {
        Object obj = mouse_event.getSource();
        int player_size = LoadSave.players.size();
        if(GUIController.main_panel.getAvatar1Panel() == obj) {
            if(player_size >= 1) {
                handleClick(1);
            }
        }
        else if(GUIController.main_panel.getAvatar2Panel() == obj) {
            if(player_size >= 2) {
                handleClick(2);
            }
        }
        else if(GUIController.main_panel.getAvatar3Panel() == obj) {
            if(player_size >= 3) {
                handleClick(3);
            }
        }
        else if(GUIController.main_panel.getAvatar4Panel() == obj) {
            if(player_size >= 4) {
                handleClick(4);
            }
        }

    }

    private void handleClick(int panel_clicked) {
        UUID id = LoadSave.players_keys.get(panel_clicked - 1);
        GUIController.trySaving(Optional.of(id)); //ask to save if not saved
        GUIController.resetAvatars();
        LoadSave.players_keys.remove(panel_clicked - 1);
        LoadSave.players.remove(id);
        GUIController.showMainPanel();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
}
