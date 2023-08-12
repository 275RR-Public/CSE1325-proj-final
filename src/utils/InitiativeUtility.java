package utils;

import java.util.Arrays;
import java.util.Comparator;

import core.Combatant;

/**
 * Generic utility that rolls for initiative for a group of entities
 */
public class InitiativeUtility <T extends Combatant> {
    private T[] combatants;
    private Integer[] indices;

    /**
     * Creates a initiative utility
     * @param combatants an array of objects that implement Combatant
     */
    public InitiativeUtility(T[] combatants) {
        this.combatants = combatants;
    }

    /**
     * Rolls initiative for each entity and resolves duplicates
     * @return the final order
     */
    public Integer[] rollInitiative() {
        
        // Roll initiative for each player
        Integer[] rolls = new Integer[combatants.length];
        for (int i = 0; i < combatants.length; i++) {
            rolls[i] = combatants[i].rollInitiative();
        }

        ArrayIndexComparator c = new ArrayIndexComparator(rolls);
        this.indices = c.createIndexArray();
        Arrays.sort(this.indices, c);

        System.out.println();
        System.out.println("Initiative Order");
        for (int i = 0; i < combatants.length; i++) {
            System.out.printf("\t%d. %s (rolled %d)\n", i + 1, combatants[indices[i]].getName(), rolls[indices[i]]);
        }
        
        // Check for duplicates
        resolveDuplicates(rolls, c, 0, this.indices.length - 1);
        return indices;
    }

    private void printArray(Integer[] rolls, int startIdx, int endIdx) {
        for (int i = startIdx; i <= endIdx; i++) {
            System.out.printf("\t%d. %s (rolled %d)\n", i + 1, combatants[indices[i]].getName(), rolls[indices[i]]);
        }
    }

    private int resolveDuplicates(Integer[] rolls, ArrayIndexComparator c, int startIdx, int endIdx) {
        
        // Check duplicates
        for (int i = startIdx, j = i; i < endIdx; i++, j++) {
            while (j < endIdx && rolls[indices[i]].compareTo(rolls[indices[j + 1]]) == 0) {
                j++;
            }

            if (i != j) {
                System.out.println("Duplicates!");
                System.out.printf("Rerolling %d to %d\n", i + 1, j + 1);
                for (int k = i; k <= j; k++) {
                    rolls[indices[k]] = combatants[indices[k]].rollInitiative();
                }
                Arrays.sort(indices, i, j + 1, c);
                printArray(rolls, i, j);
                i = resolveDuplicates(rolls, c, i, j);
            }
        }
        return endIdx;
    }

    private class ArrayIndexComparator implements Comparator<Integer> {
        private final Integer[] array;

        public ArrayIndexComparator(Integer[] array) {
            this.array = array;
        }

        public Integer[] createIndexArray() {
            Integer[] indices = new Integer[array.length];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }
            return indices;
        }

        @Override
        public int compare(Integer idx1, Integer idx2) {
            return array[idx2].compareTo(array[idx1]);
        }
    }
}
