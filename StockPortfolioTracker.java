import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    String name;
    int quantity;
    double buyPrice;
    double currentPrice;

    Stock(String name, int quantity, double buyPrice, double currentPrice) {
        this.name = name;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
    }

    double investmentValue() {
        return quantity * buyPrice;
    }

    double currentValue() {
        return quantity * currentPrice;
    }

    double profitLoss() {
        return currentValue() - investmentValue();
    }
}

public class StockPortfolioTracker {
    static ArrayList<Stock> portfolio = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== STOCK PORTFOLIO TRACKER =====");
            System.out.println("1. Add Stock");
            System.out.println("2. View Portfolio");
            System.out.println("3. View Total Investment");
            System.out.println("4. View Total Profit/Loss");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Please enter a number (1-5): ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStock();
                    break;
                case 2:
                    viewPortfolio();
                    break;
                case 3:
                    showTotalInvestment();
                    break;
                case 4:
                    showTotalProfitLoss();
                    break;
                case 5:
                    System.out.println("Thank you for using Stock Portfolio Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        } while (choice != 5);

        sc.close();
    }

    static void addStock() {
        System.out.print("Enter stock name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Stock name cannot be empty.");
            return;
        }

        int quantity;
        System.out.print("Enter quantity: ");
        while (!sc.hasNextInt() || (quantity = sc.nextInt()) <= 0) {
            System.out.println("Quantity must be a positive whole number.");
            sc.nextLine();
            System.out.print("Enter quantity again: ");
        }
        quantity = sc.nextInt();
        sc.nextLine();

        double buyPrice = readPositivePrice("Enter purchase price per share: ₹");
        double currentPrice = readPositivePrice("Enter current price per share: ₹");

        portfolio.add(new Stock(name, quantity, buyPrice, currentPrice));
        System.out.println("Stock added successfully!");
    }

    static double readPositivePrice(String message) {
        double price;
        System.out.print(message);

        while (!sc.hasNextDouble() || (price = sc.nextDouble()) < 0) {
            System.out.println("Please enter a valid non-negative price.");
            sc.nextLine();
            System.out.print(message);
        }

        price = sc.nextDouble();
        sc.nextLine();
        return price;
    }

    static void viewPortfolio() {
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty. Add a stock first.");
            return;
        }

        System.out.println("\n--- YOUR PORTFOLIO ---");
        System.out.printf("%-15s %-10s %-15s %-15s %-15s%n",
                "Stock", "Quantity", "Investment", "Current Value", "Profit/Loss");

        for (Stock stock : portfolio) {
            System.out.printf("%-15s %-10d ₹%-14.2f ₹%-14.2f ₹%-14.2f%n",
                    stock.name, stock.quantity, stock.investmentValue(),
                    stock.currentValue(), stock.profitLoss());
        }
    }

    static void showTotalInvestment() {
        double total = 0;

        for (Stock stock : portfolio) {
            total += stock.investmentValue();
        }

        System.out.printf("Total Investment: ₹%.2f%n", total);
    }

    static void showTotalProfitLoss() {
        double total = 0;

        for (Stock stock : portfolio) {
            total += stock.profitLoss();
        }

        System.out.printf("Total Profit/Loss: ₹%.2f%n", total);

        if (total > 0) {
            System.out.println("Your portfolio is currently in profit.");
        } else if (total < 0) {
            System.out.println("Your portfolio is currently at a loss.");
        } else {
            System.out.println("Your portfolio has no profit or loss.");
        }
    }
}