package info.rynkowski.dbusjavaonandroidovernetworkexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.btn_notify).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.i("MainActivity", "button clicked");

        new Thread(new Runnable() {
          @Override public void run() {
            DbusHelper dbusHelper = new DbusHelper();
            dbusHelper.openConnection("192.168.1.112", "55555");
            dbusHelper.showNotification("Remote Application", "This message is a test.", 10);
            dbusHelper.closeConnection();
          }
        }).start();
      }
    });
  }
}
