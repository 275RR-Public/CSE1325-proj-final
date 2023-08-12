package input;

import java.awt.event.ActionEvent;

public class AAction extends InputManagerAction {
    
    public AAction(InputManager manager, boolean activate) {
        super(manager, activate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getManager().AWasPerformed(shouldActivate());
    }
}