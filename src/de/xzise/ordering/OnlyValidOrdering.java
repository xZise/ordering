package de.xzise.ordering;

public class OnlyValidOrdering extends AbstractOrdering {

    public OnlyValidOrdering(int num) {
        super(num);
    }

    private void step(final int idx, final int[] orders, final boolean[][] exists) {
        for (int character = 0; character < this.num; character++) {
            checks++;
//            if (idx < 6) {
//                System.out.println(String.format("%d: %d", idx, character));
//            }
            if (character != orders[idx - 1] && !exists[character][orders[idx - 1]]) {
                orders[idx] = character;
                if (idx < this.num_stages - 1) {
                    exists[character][orders[idx - 1]] = true;
                    step(idx + 1, orders, exists);
                    exists[character][orders[idx - 1]] = false;
                } else {
                    addEntry(orders);
                }
            }
        }
    }

    @Override
    protected void innerRun() {
        int[] orders = new int[num_stages];

        /*
        Build step by step:
        * Count each character
        * start with 0 and 1
        * in loop:
            * insert a number (which wasn't the number before)
            * which is still available (count > 0?)
            * otherwise it failed and unroll
         */


        // First is always 0 and 1
        orders[0] = 0;
        orders[1] = 1;

        boolean[][] exists = new boolean[this.num][this.num];
        exists[1][0] = true;

        step(2, orders, exists);

//        for (int idx = 2; idx < orders.length; idx++) {
//            int found = -1;
//            for (int character = 0; character < this.num; character++) {
//                if (character != orders[idx - 1] && available[character] > 0) {
//                    orders[idx] = character;
//                    available[character]--;
//                    found = character;
//                }
//            }
//        }
    }

}
