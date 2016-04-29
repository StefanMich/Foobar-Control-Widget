package dk.stufkan.foobarcontrolwidget.library;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by Stefan on 2016-04-29.
 */
public class ShutdownControlTest extends TestCase {

    public void testIntToByteArray() throws Exception {
        byte[] res = ShutdownControl.intToByteArray(100);

        Assert.assertEquals(0x64, res[0]);
        Assert.assertEquals(0x0, res[1]);
        Assert.assertEquals(0x0, res[2]);
        Assert.assertEquals(0x0, res[3]);
    }
}