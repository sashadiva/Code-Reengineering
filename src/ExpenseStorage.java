import java.io.*;
import java.util.*;

public class ExpenseStorage {
    private static final String EXPENSES_FILE_NAME = "expenses.txt";
    private static final String BUDGETS_FILE_NAME = "budgets.txt";

    public static List<Expense> loadExpenses() {
        return loadObject(EXPENSES_FILE_NAME,
                new ArrayList<>(),
                "Expenses loaded successfully.",
                "No existing expenses found. Starting with an empty expense list.");
    }

    public static void saveExpenses(List<Expense> expenses) {
        saveObject(expenses,
                EXPENSES_FILE_NAME,
                "Expenses saved successfully.",
                "Error saving expenses to file.");
    }

    public static Map<String, Double> loadBudgets() {
        return loadObject(BUDGETS_FILE_NAME,
                new HashMap<>(),
                "Budgets loaded successfully.",
                "No existing budgets found. Starting with an empty budget list.");
    }

    public static void saveBudgets(Map<String, Double> budgets) {
        saveObject(budgets,
                BUDGETS_FILE_NAME,
                "Budgets saved successfully.",
                "Error saving budgets to file.");
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadObject(String fileName, T defaultValue, String successMessage, String failureMessage) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println(successMessage);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(failureMessage);
            return defaultValue;
        }
    }

    private static void saveObject(Object object, String fileName, String successMessage, String failureMessage) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            System.out.println(successMessage);
        } catch (IOException e) {
            System.out.println(failureMessage);
        }
    }
}
