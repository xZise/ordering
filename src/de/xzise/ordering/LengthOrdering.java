package de.xzise.ordering;

import java.util.Arrays;

public class LengthOrdering extends AbstractOrdering {
    public LengthOrdering(int num) {
        super(num);
    }

    private void step(final int idx, final int[] orders, final int[] available) {
        for (int character = 0; character < this.num; character++) {
//            if (idx < 6) {
//                System.out.println(String.format("%d: %d", idx, character));
//            }
            checks++;
            if (character != orders[idx - 1] && available[character] > 0) {
                orders[idx] = character;
                if (idx < this.num_stages - 1) {
                    available[character]--;
                    step(idx + 1, orders, available);
                    available[character]++;
                } else {
                    handleEntry(orders);
                }
            }
        }
    }

    @Override
    protected void innerRun() {
        int[] orders = new int[num_stages];
        orders[0] = 0;
        orders[1] = 1;

        int[] available = new int[this.num];
        Arrays.fill(available, this.num - 1);
        available[0]--;
        available[1]--;

        step(2, orders, available);
    }
}
