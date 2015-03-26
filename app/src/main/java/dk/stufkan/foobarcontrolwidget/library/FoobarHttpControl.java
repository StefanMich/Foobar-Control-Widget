package dk.stufkan.foobarcontrolwidget.library;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FoobarHttpControl {
    private String prefix;

    public FoobarHttpControl(String prefix) {
        this.prefix = prefix;
    }

    private void webrequest(String suffix) {
        String prefix = "192.168.1.4:8888";
        String url = prefix + suffix;

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(httpget);
        } catch (IOException e) {
            Log.d("foobarhttpcontrol", "Could not connect to " + prefix + "\nPlease check the IP and port and ensure that the host is able to accept connections");
        }
    }

    public void playPause() {
        webrequest("?cmd=PlayOrPause&param1=");
    }

    public void random() {
        webrequest("?cmd=StartRandom&param1=");
    }

    public void next() {
        webrequest("?cmd=StartNext&param1=");
    }

    public void mute() {
        webrequest("?cmd=VolumeMuteToggle");
    }

    public void volume(int value) {
        webrequest("?cmd=Volume&param1=" + value);
    }
}
