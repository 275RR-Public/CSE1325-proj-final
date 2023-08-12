// vscode jar imports
//https://stackoverflow.com/questions/64273185/vscode-how-to-import-a-jar-file-into-your-java-project

//https://stackoverflow.com/questions/5217611/the-mvc-pattern-and-swing
//https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system

//https://www.formdev.com/flatlaf/
//http://www.miglayout.com/QuickStart.pdf
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import controllers.GUIController;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            if(SystemInfo.isLinux) {
                // enable custom window decorations
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
            }
        } catch(Exception ex) {
            System.err.println("LAF: Look and Feel failed.");
            System.err.println("LAF: Possible jar import error.");
        }

        SwingUtilities.invokeLater(() -> {
            new GUIController();
        });
    }
}