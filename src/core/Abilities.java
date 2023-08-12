package core;

public class Abilities {
    private int current_movement = 0;
    private int current_actions = 0;


    public int getCurrentMovement() {
        return current_movement;
    }
    public void setCurrentMovement(int current_movement) {
        this.current_movement = current_movement;
    }
    public int getCurrentActions() {
        return current_actions;
    }
    public void setCurrentActions(int current_actions) {
        this.current_actions = current_actions;
    }
}
