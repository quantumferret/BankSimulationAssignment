import java.util.List;
/**
 * @author Aidan Tribble
 *         Tracks how many lines and tellers are in the bank if there is a
 *         drive-through lane present, and routes customers to the shortest line.
 */
public class Bank {

    private Teller[] tellers;
    private Line[] lines;
    private Line driveUp;
    private int currentTime;



    public Bank (int tellerCount, int lineCount, boolean driveUpPresent) {
        // if neither of the below are true it throws an assertion exception
        assert tellerCount == lineCount || lineCount == 1;

        tellers = new Teller[tellerCount];
        for (int i = 0; i < tellerCount; i++) {
            tellers[i] = new Teller(i + 1);
        }

        lines = new Line[tellerCount];
        if (lineCount == 1) {
            Line line = new Line(1);
            for (int i = 0; i < lines.length; i++) {
                lines[i] = line;
            }
        }
        else {
            for (int i = 0; i < lineCount; i++) {
                lines[i] = new Line(i + 1);
            }
        }

        if (driveUpPresent) {
            driveUp = new Line(lineCount + 1);
        }
    }


    /**
     * Adds a customer to the shortest walk-in line or the drive up line.
     */
    public void addCustomerToLine(Customer customer) {
        if (customer.isWalkIn() || driveUp == null) {
            int index = 0;
            for (int i = 1; i < lines.length; i++) {
                if (customers(i) < customers(index)) {
                    index = i;
                }
            }
            lines[index].add(customer);
        } else {
            driveUp.add(customer);
        }
    }

    /**
     * Computes the length of a line including the teller's current customer if any.
     */
    private int customers (int index)  {
        int count = lines[index].size();
        if (tellers[index].getCustomer() != null) count++;
        return count;
    }

    public void processCustomers(List<Customer> customers) {
        int nextCustomer = 0;
        int customersDone = 0;
        for (;;)
        {
            while (nextCustomer < customers.size() && customers.get(nextCustomer).getArrivalTime() == currentTime) {
                addCustomerToLine(customers.get(nextCustomer++));
            }
            for (int i = 0; i < tellers.length; i++) {
                Teller teller = tellers[i];
                if (teller.getCustomer() == null) {
                    if (lines[i].size() > 0) {
                        teller.startTransaction(lines[i].remove(), currentTime);
                    }
                    else if (driveUp != null && driveUp.size() > 0)
                    {
                        teller.startTransaction(driveUp.remove(), currentTime);
                    }
                } else {
                    Customer customer = teller.getCustomer();
                    if (customer.getTransactionEndTime() == currentTime) {
                        teller.endTransaction(customer, currentTime);
                        customersDone++;

                        if (customer.isWalkIn() && driveUp != null && driveUp.size() > 0) {
                            teller.startTransaction(driveUp.remove(), currentTime);
                        }
                        else {
                            if (lines[i].size() > 0) {
                                teller.startTransaction(lines[i].remove(), currentTime);
                            }
                        }
                    }
                }
            }
            if (customersDone == customers.size()) break;
            currentTime++;
        }
    }

    public int getTotalTime() {
        return currentTime;
    }

    public Teller[] getTellers() {
        return tellers;
    }
}