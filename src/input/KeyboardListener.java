package input;

import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import controllers.GameController;

public class KeyboardListener {
    
    public static void initKeyListener(GameController gc, JRootPane root_pane) {
        InputMap im = root_pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, false), "t.pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, true), "t.released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "a.pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "a.released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "d.pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "d.released");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "space.pressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "space.released");

        ActionMap am = root_pane.getActionMap();
        am.put("t.pressed", new TAction(gc, true));
        am.put("t.released", new TAction(gc, false));
        am.put("a.pressed", new AAction(gc, true));
        am.put("a.released", new AAction(gc, false));
        am.put("d.pressed", new DAction(gc, true));
        am.put("d.released", new DAction(gc, false));
        am.put("space.pressed", new SpaceAction(gc, true));
        am.put("space.released", new SpaceAction(gc, false));
    }
}
