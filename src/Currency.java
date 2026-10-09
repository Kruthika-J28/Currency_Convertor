public class Currency {
    private final String code;
    private final double amount;

    public Currency(String code, double amount) {
        this.code = code.toUpperCase();
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public double getAmount() {
        return amount;
    }
}