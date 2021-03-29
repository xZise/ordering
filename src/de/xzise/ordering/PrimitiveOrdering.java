package de.xzise.ordering;

public class PrimitiveOrdering extends AbstractOrdering {

    public PrimitiveOrdering(int num) {
        super(num);
    }

    @Override
    protected void innerRun() {
        int[] orders = new int[num_stages];
        orders[0] = 0;
        orders[1] = 1;
        while (true) {
            this.handleEntry(orders);

            // Calculate next number
            boolean carry = true;
            for (int i = 2; i < orders.length; i++) {
                orders[i] = (orders[i] + 1) % this.num;
                carry = orders[i] == 0;
                if (!carry) {
                    break;
                }
            }
            if (carry) {
                // overflowed
                break;
            }
        }
    }
}
