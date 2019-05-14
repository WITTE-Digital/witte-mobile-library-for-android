package digital.witte.wittemobilelibrary.net;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import digital.witte.wittemobilelibrary.Configuration;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class IdTokenRequestTest {

    //@Test
    public void executeTest_correct() {

        String wmaToken = null;

        final int customerId = -1; // TODO: Add your customer id here
        final String sdkKey = ""; // TODO: Add your SDK key here
        final String subKey = ""; // TODO: Add your subscription key here
        final int userId = -1; // TODO: Add your user id here

        IdTokenRequest req = new IdTokenRequest();
        try {

            wmaToken = req.execute(customerId, sdkKey, subKey, userId);
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        Configuration configuration = null;

        IdTokenRequest request = new IdTokenRequest();
        try {

            wmaToken = request.execute(configuration, userId);
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        assertNotNull(wmaToken);
    }
}
