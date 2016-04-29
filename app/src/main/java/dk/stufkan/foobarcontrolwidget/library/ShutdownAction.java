package dk.stufkan.foobarcontrolwidget.library;

/**
 * Created by Stefan on 2016-04-29.
 */
public class ShutdownAction {
    private int interval;
    private ShutdownType shutdownType;

    public ShutdownAction(int interval, ShutdownType shutdownType) {
        this.interval = interval;
        this.shutdownType = shutdownType;
    }

    public int getInterval() {
        return interval;
    }

    public ShutdownType getShutdownType()
    {
        return shutdownType;
    }
}
