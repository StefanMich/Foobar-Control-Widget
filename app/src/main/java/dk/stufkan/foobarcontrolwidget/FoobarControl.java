package dk.stufkan.foobarcontrolwidget;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import dk.stufkan.foobarcontrolwidget.library.FoobarHttpControl;


public class FoobarControl extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foobar_control);


    }

    public void showNotification(View v) {
        Log.d("notif", "before init");

        Intent Play = new Intent(this, FoobarHttpControl.class);
        Play.putExtra("action", "playPause");
        Play.putExtra("prefix", "http://192.168.1.4:8888/default/");
        PendingIntent piPlay = PendingIntent.getBroadcast(this, 2, Play, PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews widget = new RemoteViews(getPackageName(), R.layout.controlwidget);
        widget.setOnClickPendingIntent(R.id.play,piPlay);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setContent(widget);

        Notification not = mBuilder.build();

        not.bigContentView = widget;


        NotificationManager notman = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notman.notify(1, not);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foobar_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
