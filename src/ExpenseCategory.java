import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

class ExpenseCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String value;

    public ExpenseCategory(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Expense category cannot be empty.");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseCategory)) return false;
        ExpenseCategory that = (ExpenseCategory) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
