package dk.stufkan.foobarcontrolwidget.library;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Stefan on 01-05-2015.
 */
public class ControlWebRequest extends AsyncTask<String, Void, Void> {
    private String prefix;

    public ControlWebRequest(String prefix) {
        this.prefix = prefix;
    }


    private void webrequest(String suffix) {

    }

    @Override
    protected Void doInBackground(String... params) {
        String url = prefix + params[0];

        HttpClient httpclient = new DefaultHttpClient();


        //Log.d("foobarhttpcontrol",httpclient.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY).toString());

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);
        Log.d("foobarhttpcontrol", url);

        try {
            HttpResponse response = httpclient.execute(httpget);
        } catch (IOException e) {
            Log.d("foobarhttpcontrol", "Could not connect to " + prefix + "\nPlease check the IP and port and ensure that the host is able to accept connections\n " + e.getMessage());
        }

        return null;
    }
}
