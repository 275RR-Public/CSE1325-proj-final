package events;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controllers.GUIController;
import utils.Constants;
import utils.ErrorHandler;

public class NewCharPanelChangeListener implements ChangeListener {
    
    @Override
	public void stateChanged(ChangeEvent change_event) {
		changedStats(change_event.getSource());
		int remaining = Constants.STAT_POINTS - (
            GUIController.new_char_panel.getStr() +
            GUIController.new_char_panel.getDex() +
            GUIController.new_char_panel.getCon()
        );
		GUIController.new_char_panel.setStatsMsg("Stat Points Remaining: " + remaining);
	}

	private synchronized void changedStats(Object changed_obj) {
		Object str = GUIController.new_char_panel.getStrObj();
		Object dex = GUIController.new_char_panel.getDexObj();
		Object con = GUIController.new_char_panel.getConObj();

        Object str_value = ((JSpinner) str).getValue();
        Object dex_value = ((JSpinner) dex).getValue();
        Object con_value = ((JSpinner) con).getValue();

		try {
			boolean can_change = ErrorHandler.validateStats(str_value, dex_value, con_value);
			if(can_change) return;
		} catch (NumberFormatException e) {
			if(str == changed_obj) {
				GUIController.new_char_panel.setStr(0);
			}
			if(dex == changed_obj) {
				GUIController.new_char_panel.setDex(0);
			}
			if(con == changed_obj) {
				GUIController.new_char_panel.setCon(0);
			}
			return;
		}

		if(str == changed_obj) {
			GUIController.new_char_panel.setStr( ((int) str_value) - 1);
		}
		if(dex == changed_obj) {
			GUIController.new_char_panel.setDex( ((int) dex_value) - 1);
		}
		if(con == changed_obj) {
			GUIController.new_char_panel.setCon( ((int) con_value) - 1);
		}
	}
}
