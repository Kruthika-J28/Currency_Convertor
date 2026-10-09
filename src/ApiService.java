import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private static final String API_URL = "https://api.frankfurter.app/latest";

    public double fetchExchangeRate(String base, String target) throws Exception {
        String urlString = API_URL + "?from=" + base + "&to=" + target;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch conversion rate. Check currency codes.");
        }

        return parseRateFromJson(response.body(), target);
    }

    // Basic JSON string parsing without external libraries
    private double parseRateFromJson(String jsonResponse, String targetCurrency) {
        String key = "\"" + targetCurrency.toUpperCase() + "\":";
        int startIndex = jsonResponse.indexOf(key);

        if (startIndex == -1) {
            throw new IllegalArgumentException("Target currency symbol not found in response.");
        }

        startIndex += key.length();
        int endIndex = jsonResponse.indexOf("}", startIndex);
        if (endIndex == -1) {
            endIndex = jsonResponse.indexOf(",", startIndex);
        }

        String rateString = jsonResponse.substring(startIndex, endIndex).trim();
        return Double.parseDouble(rateString);
    }
}