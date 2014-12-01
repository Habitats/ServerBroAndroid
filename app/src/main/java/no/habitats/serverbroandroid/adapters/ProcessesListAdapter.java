package no.habitats.serverbroandroid.adapters;

import java.util.List;

import no.habitats.serverbroandroid.R;
import serverBro.broShared.utilities.ComputerProcess;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProcessesListAdapter extends ArrayAdapter<ComputerProcess> {

  private FragmentActivity context;
  private List<ComputerProcess> runningProcesses;
  private LayoutInflater inflater;

  public ProcessesListAdapter(FragmentActivity context, int simpleListItem2, List<ComputerProcess> runningProcesses) {
    super(context, simpleListItem2, runningProcesses);
    this.context = context;
    this.runningProcesses = runningProcesses;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View rowView = inflater.inflate(R.layout.view_pager_processes_list_item, parent, false);
    TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
    TextView description = (TextView) rowView.findViewById(R.id.tvDescription);

    title.setText(runningProcesses.get(position).getProcessName());
    description.setText(runningProcesses.get(position).getMemoryInMB());
    return rowView;
  }
}
