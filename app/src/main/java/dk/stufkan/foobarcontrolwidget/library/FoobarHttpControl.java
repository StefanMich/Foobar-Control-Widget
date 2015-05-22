package dk.stufkan.foobarcontrolwidget.library;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class FoobarHttpControl extends BroadcastReceiver {
    String TAG = "FoobarHttpControl";

    private String prefix;
    ControlWebRequest control;

    public void playPause() {
        Log.d(TAG,"playPause");
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

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Bundle extras = intent.getExtras();

        String action = extras.getString("action");
        String prefix = extras.getString("prefix");

        control = new ControlWebRequest(prefix);

        Log.d(TAG, "Indhold " + action + " " + prefix);
        if (action.equals("playPause")) {
            playPause();
        }
    }
}
