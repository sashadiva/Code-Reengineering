import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.*;

public class ExpenseTrackerApp {
    private static final double CONVERSION_RATE = 110.70;
    private final Scanner userInput = new Scanner(System.in);
    private List<Expense> expenses = new ArrayList<>();
    private Map<String, Double> categoryBudgets = new HashMap<>();

    public void run() {
        expenses = ExpenseStorage.loadExpenses();
        categoryBudgets = ExpenseStorage.loadBudgets();

        while (true) {
            displayMenu();
            MenuOption option = readMenuOption();
            option.execute(this);
            if (option == MenuOption.EXIT) {
                break;
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n===== Expense Tracker Menu =====");
        System.out.println("1. Record an expense");
        System.out.println("2. View expense summary");
        System.out.println("3. Set budget");
        System.out.println("4. View budgets");
        System.out.println("5. View expense history");
        System.out.println("6. Convert currency");
        System.out.println("7. Save expenses");
        System.out.println("8. Load expenses");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private MenuOption readMenuOption() {
        return MenuOption.fromChoice(getUserChoice());
    }

    private int getUserChoice() {
        while (!userInput.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            userInput.next();
        }
        return userInput.nextInt();
    }

    void recordExpense() {
        double amount = getValidDoubleInput("Enter the expense amount: ");
        userInput.nextLine();

        System.out.print("Enter the expense category: ");
        String category = userInput.nextLine();

        System.out.print("Enter a brief description: ");
        String description = userInput.nextLine();

        Expense expense = new Expense(amount, new ExpenseCategory(category), description);
        expenses.add(expense);

        System.out.println("Expense recorded successfully.");
    }

    void viewExpenseSummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        double totalSpending = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("Total Spending: $%.2f\n", totalSpending);

        Map<ExpenseCategory, Double> categorySpending = new HashMap<>();
        for (Expense expense : expenses) {
            categorySpending.merge(expense.getCategory(), expense.getAmount(), Double::sum);
        }

        System.out.println("\nCategory-wise Spending:");
        categorySpending.forEach((category, spending) ->
                System.out.printf("%s: $%.2f\n", category, spending));
    }

    void setBudget() {
        System.out.print("Enter the expense category to set a budget: ");
        String category = userInput.nextLine().toLowerCase();

        if (categoryBudgets.containsKey(category)) {
            System.out.printf("Current budget for category '%s': $%.2f\n",
                    category, categoryBudgets.get(category));
        }

        double newBudget = getValidDoubleInput("Enter the new budget amount for the category: ");
        categoryBudgets.put(category, newBudget);

        System.out.println("Budget set successfully for the category: " + category);
    }

    void viewAllBudgets() {
        if (categoryBudgets.isEmpty()) {
            System.out.println("No budgets set yet.");
            return;
        }

        System.out.println("\n===== Expense Budgets =====");
        categoryBudgets.forEach((category, budget) ->
                System.out.printf("Category: %s - Budget: $%.2f\n", category, budget));
    }

    void convertCurrency() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded. Cannot perform currency conversion.");
            return;
        }

        userInput.nextLine();
        Currency targetCurrency = promptForTargetCurrency();

        System.out.println("\n===== Currency Conversion =====");
        printConvertedExpenses(targetCurrency);
    }

    private Currency promptForTargetCurrency() {
        while (true) {
            System.out.print("Enter the target currency code (e.g., USD): ");
            String targetCurrencyCode = userInput.nextLine().toUpperCase();
            try {
                return Currency.getInstance(targetCurrencyCode);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid currency code. Please enter a valid currency code.");
            }
        }
    }

    private void printConvertedExpenses(Currency targetCurrency) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        currencyFormatter.setCurrency(targetCurrency);

        for (Expense expense : expenses) {
            System.out.println(expense.formatConversionEntry(currencyFormatter, CONVERSION_RATE));
        }
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (userInput.hasNextDouble()) {
                double value = userInput.nextDouble();
                if (value >= 0) {
                    return value;
                }
                System.out.println("Invalid input. Please enter a non-negative value.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                userInput.next();
            }
        }
    }

    void viewExpenseHistory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\n===== Expense History =====");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Expense expense : expenses) {
            System.out.println(expense.formatHistoryEntry(dateFormat));
        }
    }

    private void saveExpenses() {
        ExpenseStorage.saveExpenses(expenses);
    }

    private void saveBudgets() {
        ExpenseStorage.saveBudgets(categoryBudgets);
    }

    private void exit() {
        saveExpenses();
        saveBudgets();
        System.out.println("Exiting Expense Tracker. Goodbye!");
    }

    private enum MenuOption {
        RECORD_EXPENSE(1) {
            void execute(ExpenseTrackerApp app) {
                app.recordExpense();
            }
        },
        VIEW_EXPENSE_SUMMARY(2) {
            void execute(ExpenseTrackerApp app) {
                app.viewExpenseSummary();
            }
        },
        SET_BUDGET(3) {
            void execute(ExpenseTrackerApp app) {
                app.setBudget();
                app.saveBudgets();
            }
        },
        VIEW_BUDGETS(4) {
            void execute(ExpenseTrackerApp app) {
                app.viewAllBudgets();
            }
        },
        VIEW_EXPENSE_HISTORY(5) {
            void execute(ExpenseTrackerApp app) {
                app.viewExpenseHistory();
            }
        },
        CONVERT_CURRENCY(6) {
            void execute(ExpenseTrackerApp app) {
                app.convertCurrency();
            }
        },
        SAVE_EXPENSES(7) {
            void execute(ExpenseTrackerApp app) {
                app.saveExpenses();
            }
        },
        LOAD_EXPENSES(8) {
            void execute(ExpenseTrackerApp app) {
                app.expenses = ExpenseStorage.loadExpenses();
            }
        },
        EXIT(9) {
            void execute(ExpenseTrackerApp app) {
                app.exit();
            }
        },
        INVALID(-1) {
            void execute(ExpenseTrackerApp app) {
                System.out.println("Invalid choice. Please try again.");
            }
        };

        private final int choice;

        MenuOption(int choice) {
            this.choice = choice;
        }

        abstract void execute(ExpenseTrackerApp app);

        static MenuOption fromChoice(int choice) {
            for (MenuOption option : values()) {
                if (option.choice == choice) {
                    return option;
                }
            }
            return INVALID;
        }
    }
}
