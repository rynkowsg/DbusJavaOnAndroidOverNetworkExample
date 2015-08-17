package info.rynkowski.dbusjavaonandroidovernetworkexample;

import android.util.Log;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.freedesktop.Notifications;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

public class DbusHelper {
  private final static String TAG = "DbusHelper";

  private DBusConnection connection = null;

  public void openConnection(final String host, final String port) {
    try {
      String dbusAddress = "tcp:host=" + host + ",port=" + port;
      Log.i(TAG, "Connecting to: " + dbusAddress);
      connection = DBusConnection.getConnection(dbusAddress);
      System.out.println("D-Bus connection established successfully.");
    } catch (DBusException e) {
      e.printStackTrace();
      Log.i(TAG, "\"D-Bus connection hasn't established, connection: " + connection);
      connection = null;
    }
  }

  public void closeConnection() {
    if (connection != null) {
      connection.disconnect();
    }
    connection = null;
  }

  public void showNotification(final String summary, final String body, final int timeout) {
    if (connection != null) {
      try {
        // get remote object for Notifications
        Notifications notify = connection.getRemoteObject("org.freedesktop.Notifications",
            "/org/freedesktop/Notifications", Notifications.class);
        // set the rest of parameters
        String appName = "";
        String icon = "";
        UInt32 id = new UInt32(0);
        Map<String, Variant> hints = new HashMap<String, Variant>();
        hints.put("urgency", new Variant<Byte>((byte) 0));
        LinkedList<String> actions = new LinkedList<>();
        // call notify()
        notify.Notify(appName, id, icon, summary, body, actions, hints, timeout);
      } catch (DBusException e) {
        e.printStackTrace();
      }
    }
  }
}
