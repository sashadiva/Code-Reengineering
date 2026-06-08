import java.io.Serial; // For serializing and deserializing objects for serialization and deserialization.
import java.io.Serializable; // For serializing and deserializing objects.
import java.util.Date; // For handling date and time in the application.

class Expense implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // For serializing and deserializing objects.

    private final double amount; // Amount of the expense
    private final ExpenseCategory category; // Category of the expense
    private final String description; // Description of the expense
    private final Date timestamp; // Timestamp of the expense

    public Expense(double amount, ExpenseCategory category, String description) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.timestamp = new Date();
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double convertAmount(double conversionRate) {
        return amount * conversionRate;
    }

    public String formatHistoryEntry(java.text.SimpleDateFormat dateFormat) {
        return String.format("%s - Category: %s - Amount: $%.2f - Description: %s",
                dateFormat.format(timestamp), category, amount, description);
    }

    public String formatConversionEntry(java.text.NumberFormat currencyFormatter, double conversionRate) {
        return String.format("%s - Original: %s - Converted: %s",
                description,
                currencyFormatter.format(amount),
                currencyFormatter.format(convertAmount(conversionRate)));
    }

    /* Override the toString() method to display the expense in a readable format
     with the timestamp, category, amount, and description*/
    @Override
    public String toString() {
        return String.format("%tF %tT - %s - $%.2f - %s",
                timestamp, timestamp, category, amount, description);
    }
}