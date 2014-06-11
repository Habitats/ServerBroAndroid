package no.habitats.serverBroAndroid.activities;

import java.util.Observable;
import java.util.Observer;

import no.habitats.serverBroAndroid.R;
import serverBro.broShared.BroModel;
import serverBro.broShared.utilities.ComputerInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ComputerInfoFragment extends ListFragment implements Observer {
  private View rootView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.view_pager_computer_info_fragment, container, false);
    return rootView;
  }

  private void initListView(ComputerInfo computerInfo) {
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, computerInfo.getSimpleInfoList());

    getActivity().runOnUiThread(new Runnable() {

      @Override
      public void run() {
        setListAdapter(adapter);
      }
    });


  }

  @Override
  public void update(Observable observable, Object data) {
    initListView(((BroModel) observable).getComputerInfo());
  }
}
