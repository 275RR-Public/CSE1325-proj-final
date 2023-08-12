package input;

import java.awt.event.ActionEvent;

public class TAction extends InputManagerAction {
    
    public TAction(InputManager manager, boolean activate) {
        super(manager, activate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getManager().TWasPerformed(shouldActivate());
    }
}
