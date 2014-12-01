package no.habitats.serverbroandroid.activities;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import no.habitats.serverbroandroid.R;
import no.habitats.serverbroandroid.adapters.ProcessesListAdapter;
import serverBro.broShared.BroModel;
import serverBro.broShared.misc.Logger;
import serverBro.broShared.utilities.ComputerInfo;
import serverBro.broShared.utilities.ComputerProcess;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ProcessesFragment extends ListFragment implements Observer {
  private View rootView;
  private List<ComputerProcess> processes;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.view_pager_computer_info_fragment, container, false);

    // TextView heading = rootView.findViewById(R.id.t)
    return rootView;
  }

  private void initListView(ComputerInfo computerInfo) {
    List<ComputerProcess> processes = computerInfo.getRunningProcesses();
    final ArrayAdapter<ComputerProcess> adapter = new ProcessesListAdapter(getActivity(), android.R.layout.simple_list_item_2, processes);

    // update is called whenever model is changed, avoid updating the processes unless they have changed!
    if (this.processes != processes) {
      getActivity().runOnUiThread(new Runnable() {

        @Override
        public void run() {
          Logger.log("Updating processes!");
          setListAdapter(adapter);
        }
      });
    }
  }

  @Override
  public void update(Observable observable, Object data) {
    BroModel model = (BroModel) observable;
    if (model.getComputerInfo() != null) {
      initListView(model.getComputerInfo());
    }
  }
}
