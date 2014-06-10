package no.habitats.serverbroandroid;

import java.util.Observable;
import java.util.Observer;
import no.habitats.serverbroandroid.R;

import serverBro.broClient.ClientController;
import serverBro.broShared.BroModel;
import serverBro.broShared.events.internal.ConnectButtonEvent;
import serverBro.broShared.view.BroGuiController;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements Observer {

  private BroModel model;
  private ClientController clientController;
  private TextView viewFeed;
  private Button buttonRequest;
  private Button buttonDisconnect;
  private Button buttonConnect;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeComponents();
    initializeController();
  }

  private void initializeController() {
    BroGuiController guiController = new GuiControllerAndroid(this);
    clientController = new ClientController(guiController);
  }


  private void initializeComponents() {
    buttonConnect = (Button) findViewById(R.id.buttonConnect);
    buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);
    buttonRequest = (Button) findViewById(R.id.buttonRequest);
    viewFeed = (TextView) findViewById(R.id.textViewFeed);

    buttonConnect.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new ConnectButtonEvent());
      }
    });

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void update(Observable o, Object data) {
    runOnUiThread(new Runnable() {

      @Override
      public void run() {
        String msg = Double.toString(Math.random() * 1000);
        viewFeed.setText(msg);
      }
    });
    // BroModel model = (BroModel) o;
    // viewFeed.setText(model.getNetworkStatus());
    // logFeed.append(model.getLastMessage() + "\n");
    // logFeed.append(model.getProcesses().toString());
  }
}
