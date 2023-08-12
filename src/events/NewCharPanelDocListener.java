package events;

import java.awt.Color;
import java.text.ParseException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controllers.GUIController;
import utils.ErrorHandler;

public class NewCharPanelDocListener implements DocumentListener{

    @Override
    public void insertUpdate(DocumentEvent doc_event) {
        changedName();
    }

    @Override
    public void removeUpdate(DocumentEvent doc_event) {
        changedName();
    }

    @Override
    public void changedUpdate(DocumentEvent doc_event) {}

    
    private void changedName() {
		try {
            ErrorHandler.validateName(GUIController.new_char_panel.getName());
            GUIController.new_char_panel.getNameMsg().setText("Name: Valid.");
            GUIController.new_char_panel.getNameMsg().setForeground(new Color(0, 128, 0));
			ErrorHandler.valid_name = true;
			ErrorHandler.validateForm(GUIController.new_char_panel.getSubmitButton());
        } catch (ParseException e) {
            GUIController.new_char_panel.getNameMsg().setText("Name: " + e.getMessage());
            GUIController.new_char_panel.getNameMsg().setForeground(Color.RED);
			ErrorHandler.valid_name = false;
			ErrorHandler.validateForm(GUIController.new_char_panel.getSubmitButton());
        }
	}
}
