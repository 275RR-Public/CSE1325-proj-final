// dont return null - https://www.code4it.dev/cleancodetips/exceptions-instead-of-null/#:~:text=and%20everyone%20else.-,Clean%20Code%20Tip%3A%20throw%20exceptions%20instead%20of%20returning,when%20there%20is%20no%20fallback&text=When%20you%20don't%20have,exception%20instead%20of%20returning%20null.
// exception perf - https://gunnarpeipman.com/cost-of-exceptions/

package core;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import utils.CsvReadException;
import utils.ErrorHandler;
import utils.GameUtility;
import utils.Constants;

/**
 * the Player class extends Creature and represents a playable character
 */
public class Player extends Creature {

//Properties
    private Weapon equipped_weapon;

//Constructors
    /**
     * Creation of the Player Creature with stats
     * @param name the name of the player
     * @param weapon the player's weapon
     * @param hp the health points ({@code Default:20})
     * @param ac the armor class ({@code Default:10})
     * @param str strength ({@code Default:0}) ({@code Range:0-10})
     * @param dex dexterity ({@code Default:0}) ({@code Range:0-10})
     * @param con constitution ({@code Default:0}) ({@code Range:0-10})
     */
    public Player(String name, Avatar avatar, Weapon equipped_weapon, int hp, int ac, int str, int dex, int con) {
        super(name, avatar, hp, ac, str, dex, con);
        this.equipped_weapon = Objects.requireNonNullElse(equipped_weapon, new Weapon("Long Sword", "2d6", 1,1));
        if(equipped_weapon.isRanged()) getInventory().add(new Ammo("Arrow", 20, 1));
    }

//Factory Constructor
    /**
     * Creation of the Player Creature from a formatted .csv file
     * @param line the formatted line of Player data
     * @return the newly created Player
     * @throws CsvReadException when reading in .csv files
     * @throws ParseException from name validation
     * @throws IOException
     */
    public static Player loadFromCsv(String line) throws CsvReadException {
        Player player = null;
        try  {
            String[] element = line.trim().split(",");
            if(element.length != 11) {
                throw new CsvReadException("Invalid number of CSV arguments.");
            }
            String name = ErrorHandler.validateName(element[0]);
            Avatar avatar = new Avatar(element[1], ErrorHandler.validateImage(Constants.IMG_PLAYER_RES + element[1]));

            int bonus = Integer.parseInt(element[4]);
            int range = Integer.parseInt(element[5]);
            Weapon wpn = new Weapon(element[2], element[3], bonus, range);

            int hp = Integer.parseInt(element[6]);
            int ac = Integer.parseInt(element[7]);
            int str = Integer.parseInt(element[8]);
            int dex = Integer.parseInt(element[9]);
            int con = Integer.parseInt(element[10]);

            player = new Player(name,avatar,wpn,hp,ac,str,dex,con);
            
        } catch (NumberFormatException e) {
            throw new CsvReadException("loadFromCsv: Invalid Number.");
        } catch (ParseException e) {
            throw new CsvReadException("loadFromCsv: Invalid Name.");
        } catch (IOException e) {
            throw new CsvReadException("loadFromCsv: Invalid Avatar.");
        }
        return player;   
    }

    /**
     * Pretty print some properties of the {@code Player} class
     * @return custom {@code String} representation of {@code Player} class 
     */
    @Override
    public String toString() {
        int hp = getHP();
        int ac = getAC();
        int str = getSTR();
        int dex = getDEX();
        int con = getCON();
        Ammo ammo = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Name:\t" + getName() + "\n");
        sb.append("Wpn: " + equipped_weapon + "\n"); 
        sb.append("HP: \t" + hp + "\tmHP:  \t" + Math.max(hp + getMod(con),0) + "\n");
        sb.append("AC:  \t" + ac + "\tmAC:  \t" + Math.max(ac + getMod(dex),0) + "\n");
        sb.append("STR:\t" + str + "\tmSTR:\t" + getMod(str) + "\n");
        sb.append("DEX:\t" + dex + "\tmDEX:\t" + getMod(dex) + "\n");
        sb.append("CON:\t" + con + "\tmCON:\t" + getMod(con) + "\n");
        /* if(equipped_weapon.isRanged()) {
            for(var item : getInventory()) {
                if(item instanceof Ammo) {
                    ammo = (Ammo) item;
                    sb.append("Wpn: " + equipped_weapon + " Ammo Left: " + ammo.getQuantity() + "\n");
                }
            }
        }
        else {
            sb.append("Wpn: " + equipped_weapon + "\n");  
        } */
        return sb.toString();
    }

    @Override
    public String toExtraString() {
        Ammo ammo = null;
        StringBuilder sb = new StringBuilder();
        if(getDisarmed() != 0){
            sb.append("DISARMED: " + getDisarmed() + " Turns" + "\n");
        }
        sb.append("Location:\t" + getPosition() + "\n\n");
        sb.append("Moves:\t" + getAbilities().getCurrentMovement() + "\n");
        sb.append("Action:\t" + getAbilities().getCurrentActions() + "\n\n");
        sb.append("Range:\t" + equipped_weapon.getRange() + "\n");
        if(equipped_weapon.isRanged()) {
            for(var item : getInventory()) {
                if(item instanceof Ammo) {
                    ammo = (Ammo) item;
                    sb.append("Ammo:\t" + ammo.getQuantity() + "\n");
                }
            }
        }
        return sb.toString();
    }

    public Weapon getWeapon() {
        return equipped_weapon;
    }
    public void setWeapon(Weapon weapon){
        Objects.requireNonNull(weapon);
        this.equipped_weapon = weapon;
    }

//Methods
    private int rollHit() {
        int roll = GameUtility.rollDice("d20");
        int dex_mod = super.getMod(this.getDEX());
        int roll_hit = Math.max(roll + dex_mod + equipped_weapon.getBonus(), 0);
        System.out.print(" (" + roll_hit + " to hit) ");
        return roll_hit;
    }

    /**
     * Implements attacking for Players
     * @param target the Creature to attack
     */
    @Override
    public String attack(Creature target) {
        System.out.print(getName() + " attacks " + target.getName() + " with " + equipped_weapon.getName());
        int target_mod_ac = Math.max(target.getAC() + target.getMod(target.getDEX()),0);
        int hit = rollHit();
        String s = "";
        s += "(" + hit + " to hit) ";
        if(hit >= target_mod_ac) {
            int str_mod = getMod(getSTR());
            int roll_dmg = Math.max(str_mod + equipped_weapon.rollDamage(), 0);
            s += "...HITS!  for " + roll_dmg + " damage.";
            System.out.println("...HITS!");
            target.takeDamage(roll_dmg);
        }
        else {
            s += "...MISSES!";
            System.out.println("...MISSES!");
        }
        //Decrement ammo if ranged
        if(equipped_weapon.isRanged()) {
            for(var item : getInventory()) {
                if(item instanceof Ammo) {
                    Ammo ammo = (Ammo) item;
                    ammo.setQuantity(ammo.getQuantity() - 1);
                    System.out.printf("%s has %d %ss left.\n", getName(), ammo.getQuantity(), ammo.getName());
                }
            }
        }
        return s;
    }

    /**
     * Implements the Combatant interface for initiative
     * @return the result of the roll
     */
    @Override
    public int rollInitiative() {
        int roll = GameUtility.rollDice("d20");
        return Math.max(roll, 0);
    }
}
