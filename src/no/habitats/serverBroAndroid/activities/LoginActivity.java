package no.habitats.serverBroAndroid.activities;

import java.io.IOException;

import no.habitats.serverBroAndroid.R;
import serverBro.broShared.Identity;
import serverBro.broShared.misc.Config;
import serverBro.broShared.misc.Logger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

  private Button bConnect;
  private TextView tvUsername;
  private TextView tvPassword;
  private TextView tvHostname;
  private TextView tvPort;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    tvHostname = (TextView) findViewById(R.id.tvHostname);
    tvPort = (TextView) findViewById(R.id.tvPort);
    tvUsername = (TextView) findViewById(R.id.tvUsername);
    tvPassword = (TextView) findViewById(R.id.tvPassword);

    bConnect = (Button) findViewById(R.id.bConnect);
    bConnect.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        initializeConfig();
        if (checkFields()) {
          Intent intent = new Intent(LoginActivity.this, ViewPagerActivity.class);

          String hostname = tvHostname.getText().toString();
          int port = Integer.parseInt(tvPort.getText().toString());
          String username = tvUsername.getText().toString();
          String password = tvPassword.getText().toString();

          Config.getInstance().setId(new Identity(username, password));
          Config.getInstance().setServerHostname(hostname);
          Config.getInstance().setServerPort(port);

          startActivity(intent);
        } else {
          // TODO: this should be temporary
          Toast.makeText(LoginActivity.this, "Fill in all the fields!", Toast.LENGTH_SHORT).show();
          tvHostname.setText("192.168.1.12");
          tvPort.setText("1337");
          tvUsername.setText("mrherp");
          tvPassword.setText("dicks");
          onClick(v);
        }
      }

      private boolean checkFields() {
        boolean enoughInfo = true;
        if (tvHostname.getText().toString().length() == 0) {
          enoughInfo = false;
        }
        if (tvPort.getText().toString().length() == 0) {
          enoughInfo = false;
        }
        if (tvUsername.getText().toString().length() == 0) {
          enoughInfo = false;
        }
        if (tvPassword.getText().toString().length() == 0) {
          enoughInfo = false;
        }
        return enoughInfo;
      }

    });
  }

  private void initializeConfig() {
    try {
      Config.getInstance().loadProperties(getResources().getAssets().open("serverbro.properties"));
      Config.getInstance().loadSecretKey(getResources().getAssets().open("secret_key"));
    } catch (IOException e) {
      Logger.error("Unable to get assets", e);
    }
  }



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }


}
