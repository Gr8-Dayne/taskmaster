package com.daylong.taskmaster;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

//    private static View.OnClickListener myOnClickListener;

    private List<TaskData> dataSet = new ArrayList<>();

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Credit: https://developer.android.com/reference/android/view/LayoutInflater
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        return new TaskHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int index) {

        TaskData selectedTask = dataSet.get(index);

        holder.textViewTitle.setText(selectedTask.getTaskName());
        holder.textViewDescription.setText(selectedTask.getDescription());
        holder.textViewPriority.setText(selectedTask.getState());

        //--------------------------------------//
        //--------------------------------------//
        //--------------------------------------//

        // Credit: The illustrious TA James assisted me here
        holder.itemView.setOnClickListener((event) -> {

            Context context = event.getContext();

            String potatoTitle = selectedTask.getTaskName();
            String potatoState = selectedTask.getDescription();
            String potatoDescription = selectedTask.getState();

            Intent intentionalAddToDataBase = new Intent(context, TaskDetail.class);

            intentionalAddToDataBase.putExtra("taskName", potatoTitle);
            intentionalAddToDataBase.putExtra("taskState", potatoState);
            intentionalAddToDataBase.putExtra("taskDescription", potatoDescription);

            context.startActivity(intentionalAddToDataBase);
        });
        //--------------------------------------//
        //--------------------------------------//
        //--------------------------------------//
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setDataSet(List<TaskData> data) {
        this.dataSet = data;
        notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public TaskHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.individualTaskName);
            textViewPriority = itemView.findViewById(R.id.individualTaskState);
            textViewDescription = itemView.findViewById(R.id.individualTaskDescription);
        }
    }
}


