package com.daylong.taskmaster;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<TaskData> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewCardState;
        TextView textViewCardDescription;
//        TextView textViewCardID;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewCardState = (TextView) itemView.findViewById(R.id.textViewCardState);
            this.textViewCardDescription = (TextView) itemView.findViewById(R.id.textViewCardDescription);
//            this.textViewCardID = (TextView) itemView.findViewById(R.id.textViewCardID);
        }
    }

    public MyTaskRecyclerViewAdapter(ArrayList<TaskData> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(AllTasks.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView taskTitle = holder.textViewName;
        TextView taskState = holder.textViewCardState;
        TextView taskDescription = holder.textViewCardDescription;
//        TextView taskID = holder.textViewCardID;

        taskTitle.setText(dataSet.get(listPosition).getTaskName());
        taskState.setText(dataSet.get(listPosition).getState());
        taskDescription.setText(dataSet.get(listPosition).getDescription());
//        taskID.setText(dataSet.get(listPosition).getId());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


