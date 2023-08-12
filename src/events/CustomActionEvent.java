package events;

import java.awt.event.ActionEvent;

public class CustomActionEvent extends ActionEvent {

  private Object pass_object;

  public CustomActionEvent(Object source, int id, String command, Object pass_object) {
    super(source, id, command);
    this.pass_object = pass_object;
  }

  public Object getPassedObject() {
    return pass_object; 
  }

}