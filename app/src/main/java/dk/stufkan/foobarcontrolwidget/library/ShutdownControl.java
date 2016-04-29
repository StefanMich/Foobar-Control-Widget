package dk.stufkan.foobarcontrolwidget.library;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Stefan on 2015-11-30.
 */
public class ShutdownControl extends AsyncTask<ShutdownAction, Void, Boolean> {

    Socket s;
    String ip;
    int port;

    public ShutdownControl(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void sendMessage(ShutdownAction action) throws IOException {
        s = new Socket(this.ip, this.port);

        OutputStream out = s.getOutputStream();

        int intervalInMilliSeconds = action.getInterval() *60 * 1000;

        byte[] time = intToByteArray(intervalInMilliSeconds);

        byte[] data = new byte[5];
        data[0] = action.getShutdownType().val; //shutdown type
        System.arraycopy(time, 0, data, 1, 4);


        out.write(data);
    }

    public static byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[0] = (byte) (a & 0xFF);
        ret[1] = (byte) ((a >> 8) & 0xFF);
        ret[2] = (byte) ((a >> 16) & 0xFF);
        ret[3] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    @Override
    protected Boolean doInBackground(ShutdownAction... params) {
        try {
            Log.d("ShutdownControl", "Shutdown Attempt");
            sendMessage(params[0]);
        } catch (IOException e) {
            Log.d("ShutdownControl", "den er gal" + e.getMessage());
        }
        return true;
    }
}
