import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ApiMoneda {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/dd55bbbb0b0d632bc146585e/latest/USD";
    private Map<String, Double> exchangeRates;

    public ApiMoneda() throws Exception {
        exchangeRates = new HashMap<>();
        fetchRates();
    }

    private void fetchRates() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Failed to connect to the API");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(content.toString(), JsonObject.class);
        JsonObject rates = json.getAsJsonObject("conversion_rates");
        for (Map.Entry<String, com.google.gson.JsonElement> entry : rates.entrySet()) {
            exchangeRates.put(entry.getKey(), entry.getValue().getAsDouble());
        }
    }

    public double convert(String from, String to, double amount) {
        double rateFrom = exchangeRates.get(from);
        double rateTo = exchangeRates.get(to);
        return amount * (rateTo / rateFrom);
    }
}

