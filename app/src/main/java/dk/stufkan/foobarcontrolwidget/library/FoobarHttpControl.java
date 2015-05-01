package dk.stufkan.foobarcontrolwidget.library;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FoobarHttpControl {
    private String prefix;
    ControlWebRequest control;

    public FoobarHttpControl(String prefix) {
        this.prefix = prefix;
        control = new ControlWebRequest(prefix);

        Log.d("foobarhttpcontrol", "constructor");
    }



    public void playPause() {

        control.execute("?cmd=PlayOrPause&param1=");
    }

    public void random() {
        control.execute("?cmd=StartRandom&param1=");
    }

    public void next() {
        control.execute("?cmd=StartNext&param1=");
    }

    public void mute() {
        control.execute("?cmd=VolumeMuteToggle");
    }

    public void volume(int value) {
        control.execute("?cmd=Volume&param1=" + value);
    }
}
