package input;

import javax.swing.AbstractAction;

public abstract class InputManagerAction extends AbstractAction {

    private InputManager manager;
    private boolean activate;

    public InputManagerAction(InputManager manager, boolean activate) {
        this.manager = manager;
        this.activate = activate;
    }

    public InputManager getManager() {
        return manager;
    }

    public boolean shouldActivate() {
        return activate;
    }

}
