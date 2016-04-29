package dk.stufkan.foobarcontrolwidget;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;

import java.io.IOException;

import dk.stufkan.foobarcontrolwidget.library.FoobarHttpControl;
import dk.stufkan.foobarcontrolwidget.library.ShutdownAction;
import dk.stufkan.foobarcontrolwidget.library.ShutdownControl;
import dk.stufkan.foobarcontrolwidget.library.ShutdownType;


public class FoobarControl extends Activity {

    String destination ="";
    String foobarPort="";
    SharedPreferences sharedPref;

    private void attach_shutdown_type_dropdown()
    {
        Spinner spinner = (Spinner) findViewById(R.id.shutdown_type_dropdown);
        ArrayAdapter<ShutdownType> adapter = new ArrayAdapter<ShutdownType>(this, android.R.layout.simple_spinner_item, ShutdownType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foobar_control);
        sharedPref = this.getSharedPreferences(getString(R.string.appdir), Context.MODE_PRIVATE);

        attach_shutdown_type_dropdown();


        EditText et = (EditText)findViewById(R.id.editText);
        destination = retrieveDestinationIPfromPreferences();
        et.setText(destination);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                destination = s.toString();
                Log.d("onCreate", destination);
            }
        });

        EditText portEditText = (EditText)findViewById(R.id.editText2);
        foobarPort = retrieveFoobarPortfromPreferences();
        et.setText(foobarPort);

        portEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                foobarPort = s.toString();
                Log.d("onCreate", foobarPort);
            }
        });
    }

    public void showNotification(View v) { // method used by "button"
       saveDestinationIPToPreferences();
        saveFoobarPortToPreferences();

        PendingIntent piPlay = getControlPendingIntent(FoobarHttpControl.Control.play,1);
        PendingIntent piNext = getControlPendingIntent(FoobarHttpControl.Control.next,2);
        PendingIntent piRandom = getControlPendingIntent(FoobarHttpControl.Control.random,3);


        RemoteViews widget = new RemoteViews(getPackageName(), R.layout.controlwidget);
        widget.setOnClickPendingIntent(R.id.play, piPlay);
        widget.setOnClickPendingIntent(R.id.next, piNext);
        widget.setOnClickPendingIntent(R.id.random, piRandom);

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

    private PendingIntent getControlPendingIntent(FoobarHttpControl.Control action, int actioncode) {

        Intent Play = new Intent(this, FoobarHttpControl.class);
        Play.putExtra("action", action);
        Play.putExtra("prefix", "http://" + destination + ":" + foobarPort + "/default/");
        return PendingIntent.getBroadcast(this, actioncode, Play, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public void saveDestinationIPToPreferences()
    {
        sharedPref.edit().putString(getString(R.string.savedIP), destination).apply();
    }

    public void saveFoobarPortToPreferences()
    {
        sharedPref.edit().putString(getString(R.string.savedFoobarPort), destination).apply();
    }

    public String retrieveDestinationIPfromPreferences()
    {
        return sharedPref.getString(getString(R.string.savedIP), "");
    }

    public String retrieveFoobarPortfromPreferences()
    {
        return sharedPref.getString(getString(R.string.savedFoobarPort), "");
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

    public void test(View v) throws IOException
    {
        String ip = retrieveDestinationIPfromPreferences();

        Spinner shutdownSpinner = (Spinner) findViewById(R.id.shutdown_type_dropdown);
        ShutdownType shutdownType = (ShutdownType) shutdownSpinner.getSelectedItem();

        EditText intervalET = (EditText) findViewById(R.id.intervalET);
        int interval = Integer.valueOf(intervalET.getText().toString());

        ShutdownAction action = new ShutdownAction(interval, shutdownType);

        ShutdownControl shutdownControl = new ShutdownControl(ip, 8001);
        shutdownControl.execute(action);
    }
}
