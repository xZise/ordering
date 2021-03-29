package de.xzise.ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractOrdering {

    private final List<int[]> validCodes = new ArrayList<>();
    protected final int num;
    protected final int num_stages;
    protected long checks;

    public AbstractOrdering(int num) {
        // Wir benutzten nur 1 Nibble pro "Eintrag"
        // 1, 2, 3, 1, 3, 2 => 0000000 12 02 10
        /*
        3 => 6 = 3 * 2 = (anzahl) * (anzahl der vorläufer)
         */
        // num * (num - 1) sollte immer gerade sein, da entweder num gerade ist oder num - 1 gerade ist
        // also gibt es hier kein Rest
        this.num = num;
        this.num_stages = num * (num - 1);
    }

    public void run() {
        validCodes.clear();
        checks = 0;
        innerRun();
    }

    protected abstract void innerRun();

    protected void handleEntry(final int[] orders) {
        if (isValid(orders)) {
            addEntry(orders);
        }
    }

    protected void addEntry(final int[] orders) {
        // Transcribe into "length"-mode
        int[] distances = new int[orders.length];
        for (int i = 0; i < orders.length; i++) {
            for (int j = 1; j < orders.length; j++) {
                int idx = (i + j) % orders.length;
                if (orders[i] == orders[idx]) {
                    distances[i] = j;
                    break;
                }
            }
        }
        //                System.out.println("Found solution:");
        //                System.out.println(String.join(", ", Arrays.stream(orders).mapToObj(String::valueOf).toArray(String[]::new)));
        //                System.out.println("\t" + String.join(", ", Arrays.stream(distances).mapToObj(String::valueOf).toArray(String[]::new)));

        boolean matches = false;
        for (int[] others : validCodes) {
            // Check if they are the same, optionally with an offset
            for (int offset = 0; offset < orders.length && !matches; offset++) {
                for (int i = 0; i < orders.length; i++) {
                    int idx = (offset + i) % orders.length;
                    if (others[i] != distances[idx]) {
                        break;
                    } else if (i == orders.length - 1) {
                        matches = true;
                    }
                }
            }

            if (matches) {
                break;
            }
        }

        if (!matches) {
//            System.out.println("New solution!");
            validCodes.add(distances);
        }
    }

    public void printResults() {
        System.out.println(String.format("Found solutions: %d after %d checks", validCodes.size(), checks));
        for (int[] solution : validCodes) {
            int[] orders = new int[solution.length];
            boolean[] visited = new boolean[solution.length];
            // Go through each character.
            for (int character = 0; character < this.num; character++) {
                // Find the first cell which hasn't been visited yet.
                for (int i = 0; i < solution.length; i++) {
                    if (!visited[i]) {
                        // Found the corresponding cell, lets iterate over each following cell
                        int nextIdx = i;
                        do {
                            visited[nextIdx] = true;
                            orders[nextIdx] = character;
                            nextIdx += solution[nextIdx];
                        } while (nextIdx < solution.length);
                        break;
                    }
                }
            }
            System.out.println(String.join(", ", Arrays.stream(orders).mapToObj(String::valueOf).toArray(String[]::new)));
        }
    }

    protected boolean isValid(final int[] orders) {
        boolean[][] exists = new boolean[this.num][this.num];
        for (int i = 0; i < orders.length; i++) {
            int other = (i + 1) % orders.length;
            if (orders[other] == orders[i]) {
                return false;
            }
            exists[orders[other]][orders[i]] = true;
        }

        for (int i = 0; i < this.num; i++) {
            for (int j = 0; j < this.num; j++) {
                if (i != j && !exists[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
