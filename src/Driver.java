import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aidan Tribble
 * Drives the program, like it says on the tin. It reads the data file of customer info given,
 * parses it into an array, and then returns a List of customers with the required data.
 */
public class Driver {

    public static void main(String[] arguments) throws IOException {
        if (arguments.length == 0) {
            //command line argument; for example, the first simulation would be
            // /Users/quantumferret/IdeaProjects/Assignment 4/src/sample_data.txt 3 3 true
            System.out.println("customer-file teller-count line-count drive-through?");
            return;
        }

        List<Customer> customerList = readCustomerList(arguments[0]);
        int tellerCount = Integer.parseInt(arguments[1]);
        int lineCount = Integer.parseInt(arguments[2]);
        boolean driveThrough = Boolean.parseBoolean(arguments[3]);
        Bank bank = new Bank(tellerCount, lineCount, driveThrough);
        bank.processCustomers(customerList);
        // Print control parameters.
        print("Tellers: ", tellerCount, "  Lines: ", lineCount, "  Drive-through: ", driveThrough);
        // Print the total elapsed time, in seconds
        print("Total time: ", bank.getTotalTime());
        // Print the total number of customers helped
        print("Number of customers: ", customerList.size());
        // Print the average customer waiting time
        int totalWait = 0;
        for (Customer customer : customerList) {
            totalWait += customer.getWaitTime();
        }
        print("Average wait time: ", totalWait/customerList.size());
        // For each teller, print the number of customers helped
        for (Teller teller : bank.getTellers()) {
            print("Teller ", teller.getId(), " helped ", teller.getCustomerCount(), " customers.");
        }
        // For each teller, print the percentage of time they were idle
        for (Teller teller : bank.getTellers()) {
            print("Teller ", teller.getId(), " was idle ", 100 - (100 * teller.getTotalTransactionTime())/bank.getTotalTime(), " percent of the time.");
        }
    }

    private static void print (Object... objects)
    {
        for (Object object : objects)
        {
            System.out.print(object);
        }
        System.out.println();
    }

    /**
     * See class description description.
     * @param argument
     * @return  List of customers filled from data file
     * @throws IOException
     */
    private static List<Customer> readCustomerList(String argument) throws IOException {
        List<Customer> customerList; BufferedReader reader = new BufferedReader(new FileReader(argument));

        customerList = new ArrayList<Customer>();

        for (;;) {      //infinite loop that ends when all non-null lines have been read
            String line = reader.readLine();
            if (line == null) break;    //keeps infinite loop from actually being infinite
            String[] fields = line.split("\\s+");
            int arrivalTime = Integer.parseInt(fields[0]);
            int transactionTime = Integer.parseInt(fields[1]);
            boolean walkIn = Boolean.parseBoolean(fields[2]);
            Customer customer = new Customer(customerList.size() + 1, arrivalTime, transactionTime, walkIn);
            customerList.add(customer);
        }
        return customerList;
    }
}
