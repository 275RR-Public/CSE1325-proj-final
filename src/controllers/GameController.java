package controllers;

//input maps -  https://stackoverflow.com/a/26208193
//              https://stackoverflow.com/q/19720185

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import core.Ammo;
import core.Creature;
import core.Monster;
import core.Player;
import core.Position;
import events.SpriteMoveListener;
import input.InputManager;
import input.KeyboardListener;
import utils.Constants;
import utils.GameUtility;
import utils.InitiativeUtility;
import views.GamePanel;
import views.GameUiPanel;

public class GameController implements SpriteMoveListener, InputManager {
    
    private JFrame game_frame;
    private JRootPane root_pane;
    private GamePanel game_panel;
    private GameUiPanel game_ui_panel;

    //game assets based on specific order of creatures (such as sprite(id))
    private Map<UUID, Creature> creatures_map = new LinkedHashMap<>();

    //(get ordered map in any order) every turn, order of retrieval of creatures changes 
    private ArrayList<UUID> creatures_keys = new ArrayList<>();
    //(get ordered map by index of order) array to get creature map by index of order instead of key
    private UUID[] creatures_index;
    //list of indexs in sprite array which maps to creatures_map
    private ArrayList<Integer> sprite_ids = new ArrayList<>();

    //creature that is taking turn (turn order based on sorted keys)
    private Creature current_creature;
    private Creature current_target;
    private ListIterator<UUID> turn_itr;
    private Iterator<UUID> target_iter;
    
    public GameController() {

        // Get minimal UI to screen
        game_frame = new JFrame("Game.java");
        game_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        game_panel = new GamePanel();
        game_panel.addSpriteMoveListener(this);
        game_panel.setBorder(new EmptyBorder(0, 5, 5, 5));

        game_ui_panel = new GameUiPanel();

        game_frame.add(game_panel);
        game_frame.add(game_ui_panel);
        game_frame.pack();
        game_frame.setFocusable(true);
        game_frame.setVisible(true);
        game_frame.getContentPane().requestFocusInWindow();

        // clean up main menu view
        GUIController.main_frame.dispose();

        // Game init
        root_pane = game_frame.getRootPane();
        KeyboardListener.initKeyListener(this, root_pane);  //init keyboard
        initDataStructures();                               //init creature lists
        initRandomPositions();                              //generate random creature positions
        initSprites();                                      //generate game sprites

        //start game
        roundEvent();
    }

    private void roundEvent() {
        // Round start (1 turn for each creature)
        //get initiative (turn order)
        rollInitiative();
        turn_itr = creatures_keys.listIterator();
        game_ui_panel.getStatus().setText("Status: New Round.");
        if(turn_itr.hasNext())
            turnEvent();
    }
    
    private void turnEvent() {
        // Turn start (player ends with "space")
        //active creature who is taking turn
        UUID key = turn_itr.next();
        current_creature = creatures_map.get(key);
        
        //set ui for current turn creature
        setCurrentUI();
        //find closest attackable target
        current_target = getClosestTarget();
        //set ui for target
        setTargetUI(current_target);

        if(current_creature instanceof Monster) {
            runMonsterAI();
            //auto end monster turn? lose some info but faster
            SpaceWasPerformed(true);
        }
        //player manually ends
    }
    
    private void runMonsterAI() {
        
        Monster monster = (Monster) current_creature;
        int dist_to_target = monster.getPosition().distanceTo(current_target.getPosition());

        if(dist_to_target <= 1) {
            AWasPerformed(true);
        }
    }
    
    private Creature getClosestTarget() {
        Creature target = null;
        int dist_to_target = 0;
        int closer_target = 9999;
        for(var c : creatures_map.values()) {
            if((current_creature instanceof Player && c instanceof Monster) ||
               (current_creature instanceof Monster && c instanceof Player))
            {
                dist_to_target = current_creature.getPosition().distanceTo(c.getPosition());
                if(dist_to_target < closer_target) {
                    closer_target = dist_to_target;
                    target = c; 
                }
            }
        }
        return target;
    }

    private void setCurrentUI() {
        current_creature.getAbilities().setCurrentMovement(Constants.CREATURE_MOVES);
        if(current_creature.getDisarmed() != 0) {
            current_creature.getAbilities().setCurrentActions(0);
        }
        else {
            current_creature.getAbilities().setCurrentActions(Constants.CREATURE_ACTS);
        }
        game_ui_panel.getCurrentAvatar().setIcon(current_creature.getAvatar().getIcon());
        game_ui_panel.getCurrentText().setText(current_creature.toString());
        game_ui_panel.getCurrentText().setVisible(true);
        game_ui_panel.getCurrentStatus().setText(current_creature.toExtraString());
    }

    private void setTargetUI(Creature target) {
        game_ui_panel.getTargetAvatar().setIcon(target.getAvatar().getIcon());
        game_ui_panel.getTargetText().setText(target.toString());
        game_ui_panel.getTargetText().setVisible(true);
        game_ui_panel.getTargetStatus().setText(target.toExtraString());        
    }
    
    private void rollInitiative() {
        int length = creatures_map.size();
        Creature[] creatures_arr = new Creature[length];
        for(int i = 0; i < length; i++) {
            creatures_arr[i] = creatures_map.get(creatures_index[i]);
        }

        creatures_keys.clear();

        InitiativeUtility<Creature> initUtil = new InitiativeUtility<>(creatures_arr);
        Integer[] indices = initUtil.rollInitiative();

        System.out.println();
        System.out.println("Final Order");
        for (int i = 0; i < creatures_arr.length; i++) {
            Creature creature = creatures_arr[indices[i]];
            UUID uuid = GameUtility.getKeyByValue(creatures_map, creature);
            creatures_keys.add(uuid);
            System.out.printf("\t%d. %s\n", i + 1, creature.getName());
        }
    }

    @Override
    public void spriteMoved(int id, int x, int y) {
        Creature creature = creatures_map.get(creatures_index[id]);
        creature.move(x, y);
    }

    @Override
    public boolean canMove(int id) {
        Creature creature = creatures_map.get(creatures_index[id]);

        return creature.getAbilities().getCurrentMovement() > 0;
    }

    @Override
    public boolean canMoveTo(int id, int x, int y) {
        // incoming x and y are in relative pixel coordinates, convert to grid coordinates
        Point p = game_panel.getGridLocation(x, y);

        Creature creature = creatures_map.get(creatures_index[id]);
        int dx = Math.abs(p.x - creature.getPosition().getX());
        int dy = Math.abs(p.y - creature.getPosition().getY());
        int diagonalSteps = Math.min(dx, dy);

        int distance = dx + dy - diagonalSteps;
        //System.out.println("Distance = " + distance);
        
        boolean canMoveDistance = creature.getAbilities().getCurrentMovement() >= distance;
        boolean haveMovementLeft = canMove(id);
        boolean isOccupied = isOccupied(new Position(p.x, p.y));

        if(canMoveDistance && haveMovementLeft && !isOccupied) {
            creature.setPosition(new Position(p.x, p.y));
            creature.getAbilities().setCurrentMovement(creature.getAbilities().getCurrentMovement() - distance);
            game_ui_panel.getCurrentStatus().setText(creature.toExtraString());
            game_ui_panel.getStatus().setText("Status: ");
            return true;
        }
        game_ui_panel.getStatus().setText("Status: Not enough Movement points.");
        return false;
    }
    
    @Override
    public void TWasPerformed(boolean active) {
        //System.out.println((active ? "T pressed" : "T not pressed"));
        if (active) {
            boolean gotTarget = false;
            while(!gotTarget) {
                if(target_iter == null || target_iter.hasNext() == false)
                    target_iter = creatures_map.keySet().iterator();
                gotTarget = getNextTarget(target_iter);
            }
        }
    }

    private boolean getNextTarget(Iterator<UUID> target_iter) {
        if(target_iter.hasNext()) {
            UUID key = target_iter.next();
            Creature value = creatures_map.get(key);
            if((current_creature instanceof Player && value instanceof Monster) ||
                (current_creature instanceof Monster && value instanceof Player))
            {
                current_target = value;
                setTargetUI(current_target);
                return true;
            }
        }
        return false;
    }

    @Override
    public void AWasPerformed(boolean active) {
        if(active) {
            game_ui_panel.getStatus().setText("Status: ");
            
            //player or monster?
            boolean is_player;
            if(current_creature instanceof Monster)
                is_player = false;
            else
                is_player = true;

            //find attack range
            int range = 0;
            if(is_player == false)
                range = 1;
            else
                range = ((Player)current_creature).getWeapon().getRange();

            //check if attack possible
            if(canAttackDisarm(range) == false) {
                return;
            }

            //try attack on target
            String s = "";
            if(is_player) {
                Player player = (Player) current_creature;
                if(player.getWeapon().isRanged()) {
                    for(var item : player.getInventory()) {
                        if(item instanceof Ammo) {
                            Ammo ammo = (Ammo) item;
                            if(ammo.getQuantity() > 0) {
                                s = player.attack(current_target);
                                game_ui_panel.getStatus().setText("Status: " + s);
                            }
                            else
                                game_ui_panel.getStatus().setText("Status: Out of ammo. Attack failed.");
                        }
                    }
                }
                else {
                    s = player.attack(current_target);
                    game_ui_panel.getStatus().setText("Status: " + s);     
                }
            }

            if(!is_player) {
                Monster monster = (Monster) current_creature;
                s = monster.attack(current_target);
                game_ui_panel.getStatus().setText("Status: " + s); 
            }

            //update ui
            game_ui_panel.getCurrentStatus().setText(current_creature.toExtraString());
            game_ui_panel.getTargetText().setText(current_target.toString());

            //check game state (target dead/game over)
            isGameOver();
        }
    }

    private boolean canAttackDisarm(int range) {

        //check if disarmed
        if(current_creature.getDisarmed() != 0) {
            game_ui_panel.getStatus().setText("Status: Currently Disarmed.");
            return false;
        }

        //check if actions points remaining
        if(current_creature.getAbilities().getCurrentActions() == 0) {
            game_ui_panel.getStatus().setText("Status: No Actions left.");
            return false;
        }
        
        //check if in range of target
        if(current_creature.getPosition().distanceTo(current_target.getPosition()) > range) {
            game_ui_panel.getStatus().setText("Status: Target too far.");
            return false;
        }

        //update action points (point used)
        current_creature.getAbilities().setCurrentActions(current_creature.getAbilities().getCurrentActions() - 1);
        return true;
    }

    @Override
    public void DWasPerformed(boolean active) {
        if(active) {
            game_ui_panel.getStatus().setText("Status: ");

            //disarm range is melee
            int range = 1;

            //check if disarm possible
            if(canAttackDisarm(range) == false)
                return;
            
            //try to disarm target
            String s = "";
            s = current_creature.disarm(current_target);
            game_ui_panel.getStatus().setText("Status: " + s);

            //update ui
            game_ui_panel.getCurrentStatus().setText(current_creature.toExtraString());
            game_ui_panel.getTargetStatus().setText(current_target.toExtraString());
        }
    }

    @Override
    public void SpaceWasPerformed(boolean active) {
        if(active) {
            turnResets();
            if(turn_itr.hasNext()) {
                turnEvent();
            }
            else {
                roundEvent();
            }
        }
    }

    private void turnResets() {
        current_creature.getAbilities().setCurrentMovement(0);
        current_creature.getAbilities().setCurrentActions(0);
        game_ui_panel.getStatus().setText("Status: ");
        
        if(current_creature.getDisarmed() != 0) {
            current_creature.setDisarmed(current_creature.getDisarmed() - 1);
        }
        
        /* if(current_creature instanceof Monster) {
            for(var position : grid) {
                position.setObstacle(false);
            }
        } */
        
        current_creature = null;
        current_target = null;
    }

    private void isGameOver() {

        //check if current target is dead
        boolean is_dead = current_target.isDead();
        
        //bring out your dead
        if(is_dead) {
            
            game_ui_panel.getStatus().setText("Status: " + current_target.getName() + " is incapacitated.");
            UUID uuid = GameUtility.getKeyByValue(creatures_map, current_target);
            int target_index = GameUtility.getIdxByValue(creatures_index, uuid);

            //remove from creatures_map
            if(creatures_map.remove(uuid, current_target));
                System.out.println(current_target.getName() + " dead and removed from map");
            
            //remove from creatures_index
            UUID[] temp_arr = new UUID[creatures_index.length - 1];
            int index = 0;
            for (UUID u : creatures_index) {
                if (!u.equals(uuid)) {
                    temp_arr[index++] = u; //post happens after assign
                }
            }
            creatures_index = temp_arr;
            
            //remove from creatures_keys
            creatures_keys.remove(uuid);

            //ensure updated Iterators (preserve position in turn_itr)
            target_iter = null;
            try {
                turn_itr = creatures_keys.listIterator(turn_itr.nextIndex());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Turn Iter: Recovery");
                turn_itr = creatures_keys.listIterator();
            }

            //remove sprite from sprite_ids
            sprite_ids.remove(target_index);
            
            //clean up sprite from game_panel and grid
            int x = current_target.getPosition().getX();
            int y = current_target.getPosition().getY();
            game_panel.removeSprite(target_index, x, y);
            
            //remove target and update ui to next target (unless no targets left)
            current_target = getClosestTarget();
            if(current_target != null)
                setTargetUI(current_target);

            //check if game is over (all players dead or all monsters dead)
            boolean allMonstersDead = true;
            boolean allPlayersDead = true;
            
            for(var creature : creatures_map.values()) {
                if(creature instanceof Player)
                    allPlayersDead = false;
                if(creature instanceof Monster)
                    allMonstersDead = false;
            }

            //game over
            if(allMonstersDead || allPlayersDead) {
                if(allMonstersDead) //players win
                    JOptionPane.showMessageDialog(game_frame, "Congratulations! You win.\nAll Monsters have been defeated.");
                else //monsters win
                    JOptionPane.showMessageDialog(game_frame, "Monsters have overcome your Party.\nBetter luck next time.");
                
                //close game
                game_frame.dispose();
            }
        }
    }

    private void initRandomPositions() {
        Random rand = new Random();
        for(Creature creature : creatures_map.values()) {
            while(true) {
                int x = rand.nextInt(game_panel.getMapCols());
                int y = rand.nextInt(game_panel.getMapRows());
                Position pos = new Position(x, y);
                if(inBounds(pos) && !isOccupied(pos)) {
                    creature.setPosition(pos);
                    break;
                }
            }  
        }
    }

    private boolean isOccupied(Position position) {
        int gridIdx = game_panel.getGridIdx(position.getX(), position.getY());
        GridComponent[] grid = game_panel.getGrid();
        if(grid[gridIdx].isGridComponentOccupied())
            return true;
        return false;
    }
    
    private boolean inBounds(Position position) {
        int pos_x = position.getX();
        int pos_y = position.getY();
        if(!(pos_x == GameUtility.inRange(pos_x, 0, (game_panel.getMapCols() - 1)))) {
            return false;
        }
        if(!(pos_y == GameUtility.inRange(pos_y, 0, (game_panel.getMapRows() - 1)))) {
            return false;
        }
        return true;
    }

    private void initDataStructures() {
        //creature map to preserve order for game assests (sprites)
        //creature key for calling map in turn order (initiative)
        //creature index for calling map by index (sprite(id))
        for(var monster : LoadSave.monsters) {
            UUID uuid = UUID.randomUUID();      //generate unique key
            creatures_keys.add(uuid);           //save key in order (array list)
            creatures_map.put(uuid, monster);   //save monster in order (linked hash)
        }
        for(var list : LoadSave.players.values()) {
            Player player = (Player) list.get(0);
            UUID uuid = UUID.randomUUID();
            creatures_keys.add(uuid);
            creatures_map.put(uuid, player);
        }
        creatures_index = creatures_map.keySet().toArray(new UUID[creatures_map.size()]);
    }

    private void initSprites() {
        //add sprites with position for each creature
        for(var creature : creatures_map.values()) {
            int sprite_idx = game_panel.addSprite(creature.getAvatar().getImg(),
                    creature.getPosition().getX(), creature.getPosition().getY());
            sprite_ids.add(sprite_idx);
        }
        game_frame.repaint();
    }
}
