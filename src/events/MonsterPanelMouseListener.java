package events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controllers.GUIController;
import controllers.LoadSave;

public class MonsterPanelMouseListener implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent mouse_event) {
        Object obj = mouse_event.getSource();
        int monster_size = LoadSave.monsters.size();
        if(GUIController.monster_panel.getAvatar1Panel() == obj) {
            if(monster_size >= 1) {
                handleClick(1);
            }
        }
        else if(GUIController.monster_panel.getAvatar2Panel() == obj) {
            if(monster_size >= 2) {
                handleClick(2);
            }
        }
        else if(GUIController.monster_panel.getAvatar3Panel() == obj) {
            if(monster_size >= 3) {
                handleClick(3);
            }
        }
        else if(GUIController.monster_panel.getAvatar4Panel() == obj) {
            if(monster_size >= 4) {
                handleClick(4);
            }
        }

    }

    private void handleClick(int panel_clicked) {
        GUIController.resetMonsters();
        LoadSave.monsters.remove(panel_clicked - 1);
        GUIController.showMonsterPanel();
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
