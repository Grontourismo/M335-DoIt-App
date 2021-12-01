package ch.mgb.uek.m335_doit_app.adabter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ch.mgb.uek.m335_doit_app.activity.MainActivity;
import ch.mgb.uek.m335_doit_app.entity.ToDo;
import ch.mgb.uek.m335_doit_app.R;

public class ToDoListAdapter extends ArrayAdapter<ToDo> {
    private Context context;
    private int resource;

    public ToDoListAdapter(@NonNull Context context, int resource, @NonNull List<ToDo> objects){
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource,parent,false);
        }
        ((RadioButton) convertView.findViewById((R.id.todoRadioButton))).setText(getItem(position).getTitle());
        ((RadioButton) convertView.findViewById((R.id.todoRadioButton))).setChecked(getItem(position).isFinished());
        ((RadioButton) convertView.findViewById((R.id.todoRadioButton))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setFinished(true);
                MainActivity.instance.setItems();
            }
        });
        return convertView;
    }
}
