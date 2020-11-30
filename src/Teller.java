
/**
 *
 * @author Aidan Tribble
 *
 */
public class Teller {
    private final int id;
    private int totalTransactionTime;
    private int customerCount;
    private Customer customer;

    /**
     * @param id which line number the teller is assigned to.
     */
    public Teller(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public int getTotalTransactionTime() {
        return totalTransactionTime;
    }

    /**
     * @return
     */
    public int getCustomerCount() {
        return customerCount;
    }

    /**
     * @return teller's assigned line
     */
    public int getId() {
        return id;
    }

    public void startTransaction(Customer customer, int time) {
        this.customer = customer;
        customer.setTransactionStartTime(time);
        totalTransactionTime += customer.getTransactionTime();
        customerCount++;
    }

    public void endTransaction(Customer customer, int time){
        this.customer = null;

    }

    public Customer getCustomer() {
        return customer;
    }
}
