package digital.witte.wittemobilelibrary.net;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import digital.witte.wittemobilelibrary.Configuration;

/**
 * Performs a web request in order to retrieve an id token.
 */
public class IdTokenRequest {

    public final static String GetOAuthTokenUrl = "https://wittedigitalapimprod.azure-api.net/v1/app/sdk/GetOAuthToken";

    public String execute(Configuration configuration, int userId) throws IOException {

        String wmaToken = execute(
                configuration.getWitteCustomerId(),
                configuration.getWitteSdkKey(),
                configuration.getWitteSubscriptionKey(),
                userId);

        return wmaToken;
    }

    public String execute(int customerId, String sdkKey, String subscriptionKey, int userId) throws IOException {

        String idToken = null;

        HttpsURLConnection httpsURLConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(GetOAuthTokenUrl);

            // create connection
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            httpsURLConnection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);

            // add post body
            JSONObject postParams = new JSONObject();
            postParams.put("CustomerId", customerId);
            postParams.put("UserId", userId);
            postParams.put("SdkKey", sdkKey);
            String postString = postParams.toString();

            OutputStream outputStream = httpsURLConnection.getOutputStream();
            outputStream.write(postString.getBytes("UTF-8"));
            outputStream.close();

            // execute request
            inputStream = httpsURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String str = "";
            while (null != (line = br.readLine())) {
                str += line;
            }

            // evaluate result
            JSONObject response = new JSONObject(str);
            idToken = response
                    .optString("Data", "")
                    .replaceAll("^\"|\"$", "");
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            if (null != inputStream) {

                inputStream.close();
            }

            if (null != httpsURLConnection) {

                httpsURLConnection.disconnect();
            }
        }

        return idToken;
    }
}
