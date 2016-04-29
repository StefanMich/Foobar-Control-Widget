package dk.stufkan.foobarcontrolwidget.library;

/**
 * Created by Stefan on 2016-04-29.
 */
public enum ShutdownType {
    Shutdown(0),
    Reboot(1),
    Hibernate(2),
    Cancel(3);


    public final byte val;
    ShutdownType(int val)
    {
        this.val = (byte)val;
    }
}
