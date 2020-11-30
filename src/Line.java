import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a single line in the  bank
 *
 * @author Aidan Tribble
 *
 */
public class Line {
    protected Queue<Customer> queue;
    protected int lineNum;


    public Line(int lineNum) {
        queue = new LinkedList<Customer>();
        this.lineNum = lineNum;

    }

    public void add(Customer customer) {
        queue.offer(customer);
    }

    public Customer remove() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }
}