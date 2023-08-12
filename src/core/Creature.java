// https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/

package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Constants;
import utils.GameUtility;

/**
 * the Creature class represents all living entities
 */
public abstract class Creature implements Comparable<Creature>, Combatant {

//Properties
    private String name = "";
    private Avatar avatar = null;
    private Position position = new Position(0, 0);
    private Abilities abilities = new Abilities();
    private List<Item> inventory = new ArrayList<>();
    private int disarmed = 0;       //0 not disarmed, num is rounds disarmed

    private int hp = Constants.CREATURE_HP;     //health points
    private int ac = Constants.CREATURE_AC;     //armor class
    private int str = 0;                        //strength      [0,10]
    private int dex = 0;                        //dexterity     [0,10]
    private int con = 0;                        //constitution  [0,10]

//Constructors
    /**
     * Creation of the Creature with stats
     * @param name the name of the creature
     * @param hp the health points ({@code Default:20})
     * @param ac the armor class ({@code Default:10})
     * @param str strength ({@code Default:0}) ({@code Range:0-10})
     * @param dex dexterity ({@code Default:0}) ({@code Range:0-10})
     * @param con constitution ({@code Default:0}) ({@code Range:0-10})
     * @throws IOException
     */
    public Creature(String name, Avatar avatar, int hp, int ac, int str, int dex, int con) {
        setName(name);
        setAvatar(avatar);
        setHP(hp);
        setAC(ac);
        setSTR(str);
        setDEX(dex);
        setCON(con);
    }
    
//Overrides
    /**
     * equality check by Creature's name
     * @param o the Creature to check for equality
     * @return boolean representing equality
     */
    @Override
    public boolean equals(Object o) {
        return o != null
            && getClass() == o.getClass()
            && name.equals(((Creature) o).getName());
    }
    /**
     * compareTo compares Creature's by HP
     * @param creature the Creature to compareTo
     * @return int representing ordering
     */
    @Override
    public int compareTo(Creature creature) {
        return this.getHP() - creature.getHP();
    }
    /**
     * Combatant interface allows Creatures to roll initiative
     */
    @Override
    public abstract int rollInitiative();
    /**
     * Combatant interface requires a name
     */
    @Override
    public String getName() {
        return name;
    }
    
//Setters and Getters
    /**
     * Sets Creature's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }
    public void setDisarmed(int rounds) {
        this.disarmed = Math.max(rounds, 0);
    }
    /**
     * Sets Creature's avatar (both name and img).
     * @param avatar_name the name of the image
     * @throws IOException
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
    /**
     * Sets Creature's hp.
     * @param the hp to set
     */
    public void setHP(int hp) {
        this.hp = Math.max(hp, 0);
    }
    /**
     * Sets Creature's ac.
     * @param the ac to set
     */
    public void setAC(int ac) {
        this.ac = Math.max(ac, 0);
    }
    /**
     * Sets Creature's str.
     * @param the str to set
     */
    public void setSTR(int str) {
        this.str = GameUtility.inRange(str, 0, 10);
    }
    /**
     * Sets Creature's dex.
     * @param the dex to set
     */
    public void setDEX(int dex) {
        this.dex = GameUtility.inRange(dex, 0, 10);
    }
    /**
     * Sets Creature's con.
     * @param the con to set
     */
    public void setCON(int con) {
        this.con = GameUtility.inRange(con, 0, 10);
    }

    public Position getPosition() {
        return position;
    }
    public Abilities getAbilities() {
        return abilities;
    }
    public List<Item> getInventory() {
        return inventory;
    }
    public int getDisarmed() {
        return disarmed;
    }
    /**
     * Gets Creature's avatar.
     * @return the name, img, and icon of the Creature's avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }
     /**
     * Gets Creature's hp.
     * @return the hp of the Creature
     */
    public int getHP() {
        return hp;
    }
     /**
     * Gets Creature's ac.
     * @return the ac of the Creature
     */
    public int getAC() {
        return ac;
    }
     /**
     * Gets Creature's str.
     * @return the str of the Creature
     */
    public int getSTR() {
        return str;
    }
     /**
     * Gets Creature's dex.
     * @return the dex of the Creature
     */
    public int getDEX() {
        return dex;
    }
     /**
     * Gets Creature's con.
     * @return the con of the Creature
     */
    public int getCON() {
        return con;
    }
    
//Methods
    /**
     * Creature's can attack other Creatures
     * @param target the Creature to attack
     */
    public abstract String attack(Creature target);
    public abstract String toExtraString();

    public boolean isDead() {
        return hp + getMod(con) <= 0;
    }
    
    /**
     * Creature's can takeDamage from attacks
     * @param damage the amount of hp damage to take
     */
    public void takeDamage(int damage) {
        Math.max(damage, 0);
        setHP(hp - damage);
        System.out.println(name + " took " + damage + " damage and has " + hp + " hp left. (Effective Hp: " + (hp + getMod(con)) + ")");
    }

    // Modifies stats based on distance from center of range
    public int getMod(int stat) {
        return stat - 5;
    }

    public void move(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    private int rollDisarm() {
        int roll = GameUtility.rollDice("d20");
        int str_mod = getMod(this.getSTR());
        int roll_disarm = Math.max(roll + str_mod, 0);
        System.out.println(getName() + " rolled " + roll_disarm);
        return roll_disarm;
    }

    public String disarm(Creature target) {
        int hit = rollDisarm();
        int target_roll = target.rollDisarm();
        String s = "";
        s += "(" + hit + " to hit) vs " + target_roll;
        if(hit > target_roll) {
            s += " DISARMS for 2 rounds.";
            System.out.println(getName() + " DISARMS " + target.getName() + " for 2 rounds!");
            target.setDisarmed(2);
        }
        else {
            s += " FAILS to disarm.";
            System.out.println(getName() + " couldn't overpower " + target.getName() + " and FAILS to disarm!");
        }
        return s;
    }
}
