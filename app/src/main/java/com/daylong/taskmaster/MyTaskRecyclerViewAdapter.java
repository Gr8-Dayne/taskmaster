package com.daylong.taskmaster;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<TaskData> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textOfName;
        TextView textOfCardState;
        TextView textOfCardDescription;
//        TextView textOfCardID;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textOfName = itemView.findViewById(R.id.textViewName);
            this.textOfCardState = itemView.findViewById(R.id.textViewCardState);
            this.textOfCardDescription = itemView.findViewById(R.id.textViewCardDescription);
//            this.textOfCardID = itemView.findViewById(R.id.textViewCardID);
        }
    }

    public MyTaskRecyclerViewAdapter(ArrayList<TaskData> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        view.setOnClickListener(AllTasks.myOnClickListener);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView taskTitle = holder.textOfName;
        TextView taskState = holder.textOfCardState;
        TextView taskDescription = holder.textOfCardDescription;
//        TextView taskID = holder.textOfCardID;

        taskTitle.setText(dataSet.get(listPosition).getTaskName());
        taskState.setText(dataSet.get(listPosition).getState());
        taskDescription.setText(dataSet.get(listPosition).getDescription());
//        taskID.setText(dataSet.get(listPosition).getId());
        //
        //
        //

        // Credit: The illustrious TA James assisted me here
        holder.itemView.setOnClickListener((event) -> {
            Context context = event.getContext();
            String potatoTitle = taskTitle.getText().toString();
            String potatoState = taskState.getText().toString();
            String potatoDesc = taskDescription.getText().toString();
            // ID to Integer
//            Integer potatoID = Integer.valueOf(taskID.toString());
            //
            //
            //

            Intent i = new Intent(context, TaskDetail.class);
            i.putExtra("taskName", potatoTitle);
            i.putExtra("taskState", potatoState);
            i.putExtra("taskDescription", potatoDesc);
//            i.putExtra("taskIDInteger", potatoID);

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


