import java.util.Scanner;

public class BankAccount {
    private final int accNo;
    private final String name;
    private double balance;

    public BankAccount(int accNo, String name, double openingBalance) {
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        this.accNo = accNo;
        this.name = name;
        this.balance = openingBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance. Available: " + balance);
        balance -= amount;
    }

    public double getBalance() { return balance; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Account number: ");
        int no = sc.nextInt();
        System.out.print("Name: ");
        String name = sc.next();
        System.out.print("Opening balance: ");
        BankAccount acc;
        try {
            acc = new BankAccount(no, name, sc.nextDouble());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        int choice;
        do {
            System.out.print("\n1.Deposit 2.Withdraw 3.Balance 4.Exit : ");
            choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1 -> { System.out.print("Amount: "); acc.deposit(sc.nextDouble()); System.out.println("Deposited."); }
                    case 2 -> { System.out.print("Amount: "); acc.withdraw(sc.nextDouble()); System.out.println("Withdrawn."); }
                    case 3 -> System.out.println("Balance: " + acc.getBalance());
                    case 4 -> System.out.println("Goodbye.");
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 4);
    }
}
