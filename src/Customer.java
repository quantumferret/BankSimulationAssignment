/**
 * @author Aidan Tribble
 */
public class Customer {

    private int customerNumber;
    private int arrivalTime;
    private int transactionTime;
    private boolean walkIn;
    private int transactionStartTime;

    public Customer(int customerNumber, int arrivalTime, int transactionTime, boolean walkIn) {
        this.customerNumber = customerNumber;
        this.arrivalTime = arrivalTime;
        this.transactionTime = transactionTime;
        this.walkIn = walkIn;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getTransactionTime() {
        return transactionTime;
    }

    public boolean isWalkIn() {
        return walkIn;
    }

    public int getWaitTime() {
        return transactionStartTime - arrivalTime;
    }

    public void setTransactionStartTime(int time) {
        transactionStartTime = time;
    }

    public int getTransactionStartTime() {
        return transactionStartTime;
    }

    public int getTransactionEndTime() {
        return transactionStartTime + transactionTime;
    }
}