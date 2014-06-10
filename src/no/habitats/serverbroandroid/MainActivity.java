package no.habitats.serverbroandroid;

import java.util.Observable;
import java.util.Observer;

import serverBro.broClient.ClientController;
import serverBro.broShared.BroModel;
import serverBro.broShared.Logger;
import serverBro.broShared.events.external.MessageEvent;
import serverBro.broShared.events.external.PingRequest;
import serverBro.broShared.events.internal.ConnectButtonEvent;
import serverBro.broShared.events.internal.DisconnectButtonEvent;
import serverBro.broShared.events.internal.MessageButtonEvent;
import serverBro.broShared.view.BroGuiController;
import serverBro.broShared.view.LogView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Observer {

  private ClientController clientController;
  private Button bRequest;
  private Button bDisconnect;
  private Button bConnect;
  private LogView logView;
  private TextView tvStatus;

  private TextView tvLogFeed;
  private TextView tvMessageFeed;
  private AndroidLogView messageView;

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
    bConnect = (Button) findViewById(R.id.buttonConnect);
    bDisconnect = (Button) findViewById(R.id.buttonDisconnect);
    bRequest = (Button) findViewById(R.id.buttonRequest);

    tvLogFeed = (TextView) findViewById(R.id.tvLogFeed);
    tvMessageFeed = (TextView) findViewById(R.id.tvMessageFeed);
    tvStatus = (TextView) findViewById(R.id.tvStatus);

    logView = new AndroidLogView(tvLogFeed, this);
    Logger.setLogView(logView);

    messageView = new AndroidLogView(tvMessageFeed, this);

    bConnect.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new ConnectButtonEvent());
      }
    });

    bDisconnect.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new DisconnectButtonEvent());
      }
    });

    bRequest.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new MessageButtonEvent());
      }
    });
  }

  @Override
  public void update(Observable o, Object data) {
    final BroModel model = (BroModel) o;
    String msg = Double.toString(Math.random() * 1000);
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        tvStatus.setText(model.getNetworkStatus());
        messageView.add(model.getLastMessage());
      }
    });
  }
}
