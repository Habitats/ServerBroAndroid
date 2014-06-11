package no.habitats.serverBroAndroid.activities;

import java.util.Observable;
import java.util.Observer;

import no.habitats.serverBroAndroid.AndroidLogView;
import no.habitats.serverBroAndroid.R;
import serverBro.broShared.BroModel;
import serverBro.broShared.Logger;
import serverBro.broShared.view.LogView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FeedFragment extends Fragment implements Observer {

  private LogView logView;
  private TextView tvStatus;

  private TextView tvLogFeed;
  private TextView tvMessageFeed;
  private AndroidLogView messageView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.view_pager_feed_fragment, container, false);
    initializeComponents(rootView);
    return rootView;
  }

  private void initializeComponents(View rootView) {
    tvLogFeed = (TextView) rootView.findViewById(R.id.tvLogFeed);
    tvMessageFeed = (TextView) rootView.findViewById(R.id.tvMessageFeed);
    tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);

    logView = new AndroidLogView(tvLogFeed, this);
    Logger.setLogView(logView);

    messageView = new AndroidLogView(tvMessageFeed, this);

  }

  @Override
  public void update(Observable o, Object data) {
    final BroModel model = (BroModel) o;
    String msg = Double.toString(Math.random() * 1000);
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        tvStatus.setText(model.getNetworkStatus());
        messageView.add(model.getLastMessage());
        if (model.getComputerInfo() != null) {
          messageView.add(model.getComputerInfo().getUptimeStats().toString());
          messageView.add(model.getComputerInfo().getRamStats().toString());
          messageView.add(model.getComputerInfo().getCpuStats().toString());
        }
      }
    });
  }
}
