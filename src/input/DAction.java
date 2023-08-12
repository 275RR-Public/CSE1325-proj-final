package input;

import java.awt.event.ActionEvent;

public class DAction extends InputManagerAction {
    
    public DAction(InputManager manager, boolean activate) {
        super(manager, activate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getManager().DWasPerformed(shouldActivate());
    }
}
