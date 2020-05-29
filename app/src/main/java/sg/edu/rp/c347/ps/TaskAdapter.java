package sg.edu.rp.c347.ps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> task;
    private Context context;
    private TextView tvName, tvDesc;
    private String id;

    public TaskAdapter(Context context, int resource, ArrayList<Task> objects){
        super(context, resource, objects);
        task = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tvName = rowView.findViewById(R.id.tvName);
        tvDesc = rowView.findViewById(R.id.tvDesc);

        Task currentTask = task.get(position);

        id = Integer.toString(currentTask.getId());

        tvName.setText(currentTask.getId() + " " + currentTask.getName());
        tvDesc.setText(currentTask.getDescription());

        return rowView;
    }


}

