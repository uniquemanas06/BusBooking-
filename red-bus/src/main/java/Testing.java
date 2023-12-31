import org.springframework.beans.factory.annotation.Value;

public class Testing {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public static void main(String[] args) {
        Testing testing = new Testing();
        System.out.println(testing.stripeApiKey);
    }
}
