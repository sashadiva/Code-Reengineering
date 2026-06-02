import java.io.*;
import java.util.*;

public class ExpenseStorage {
    private static final String EXPENSES_FILE_NAME = "expenses.txt";
    private static final String BUDGETS_FILE_NAME = "budgets.txt";

    @SuppressWarnings("unchecked")
    public static List<Expense> loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSES_FILE_NAME))) {
            System.out.println("Expenses loaded successfully.");
            return (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing expenses found. Starting with an empty expense list.");
            return new ArrayList<>();
        }
    }

    public static void saveExpenses(List<Expense> expenses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXPENSES_FILE_NAME))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses to file.");
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Double> loadBudgets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BUDGETS_FILE_NAME))) {
            System.out.println("Budgets loaded successfully.");
            return (Map<String, Double>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing budgets found. Starting with an empty budget list.");
            return new HashMap<>();
        }
    }

    public static void saveBudgets(Map<String, Double> budgets) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUDGETS_FILE_NAME))) {
            oos.writeObject(budgets);
            System.out.println("Budgets saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving budgets to file.");
        }
    }
}
