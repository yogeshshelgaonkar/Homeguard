package android.example.homeguard;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // CurrentUser
    @Test
    public void setNameCurrentUser_isCorrect() {
        CurrentUser userCheck = new CurrentUser();
        userCheck.setUserName("John");
        assertEquals("John", userCheck.getUserName());
    }
    @Test
    public void currentUserOverloading_isCorrect() {
        assertEquals("John@gmail",new CurrentUser("John@gmail.com").getUserName());
    }

    // Devices
    @Test
    public void devices_isCorrect() {
        assertEquals("cam1", new Devices("cam1","1").getCamName());
        assertEquals("1", new Devices("cam1","1").getIp());
    }

    // IpHashMap
    @Test
    public void oneArgIpHashMapConstructor() {
        IpHashMap ihm = new IpHashMap("12345");
        assertEquals("12345",ihm.getIpAddress());
        assertEquals("Unknown",ihm.getCameraName());
        assertEquals("IP",ihm.getIp());
    }
    @Test
    public void twoArgIphHashMapConstructor() {
        IpHashMap ihm = new IpHashMap("cam1","123");
        assertEquals("cam1",ihm.getCameraName());
        assertEquals("123",ihm.getIpAddress());
        assertEquals("IP",ihm.getIp());
    }

    @Test
    public void urlGetter_isCorrect() {
        assertEquals("http://127.0.0.1:8082/", urlGetter.getInstance("rpi : 127.0.0.1").getUrl());
    }

}