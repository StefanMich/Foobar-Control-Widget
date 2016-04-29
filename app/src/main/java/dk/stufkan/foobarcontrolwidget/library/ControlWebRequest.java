package dk.stufkan.foobarcontrolwidget.library;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Stefan on 01-05-2015.
 */
public class ControlWebRequest extends AsyncTask<String, Void, Void> {
    private String prefix;

    public ControlWebRequest(String prefix)
    {
        Log.d("ControlWebRequest",prefix);
        this.prefix = prefix;
    }


    private void webrequest(String suffix) {

    }

    @Override
    protected Void doInBackground(String... params) {
        String url = prefix + params[0];
        Log.d("foobarhttpcontrol", url);

        try {
            URL commandUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) commandUrl.openConnection();

            InputStream response = urlConnection.getInputStream();

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                Log.d("foobarhttpcontrol",responseBody);
            }

        } catch (IOException e) {
            Log.d("foobarhttpcontrol", "Could not connect to " + prefix + "\nPlease check the IP and port and ensure that the host is able to accept connections\n " + e.getMessage());
        }

        return null;
    }
}
