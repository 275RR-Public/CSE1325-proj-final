package input;

import java.awt.event.ActionEvent;

public class SpaceAction extends InputManagerAction {
    
    public SpaceAction(InputManager manager, boolean activate) {
        super(manager, activate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getManager().SpaceWasPerformed(shouldActivate());
    }
}
