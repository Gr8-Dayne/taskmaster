package com.daylong.taskmaster;


import android.annotation.SuppressLint;
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

    private List<TaskData> dataSet = new ArrayList<>();
    private Context context;

    TaskAdapter(List<TaskData> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        // Credit: https://developer.android.com/reference/android/view/LayoutInflater
        View v = LayoutInflater.from(context).inflate(R.layout.cards_layout, parent, false);
        return new TaskHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TaskHolder holder, final int RVIndex) {

        TextView taskTitle = holder.textViewTitle;
        taskTitle.setText(dataSet.get(RVIndex).getTaskName());

        TextView taskState = holder.textViewPriority;
        taskState.setText("Priority: " + dataSet.get(RVIndex).getState());

        // Credit: The illustrious TA James assisted me here
        holder.itemView.setOnClickListener((event) -> {

            Context context = event.getContext();
            String potatoTitle = taskTitle.getText().toString();
            Intent intentionalToDetails = new Intent(context, TaskDetail.class);
            intentionalToDetails.putExtra("taskName", potatoTitle);
            context.startActivity(intentionalToDetails);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewPriority;

        TaskHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.individualTaskName);
            textViewPriority = itemView.findViewById(R.id.individualTaskState);
        }
    }
}


