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
        TextView textViewState;
//        TextView textViewDescription;
//        TextView textViewID;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewState = (TextView) itemView.findViewById(R.id.textViewCardState);
//            this.textViewDescription = (TextView) itemView.findViewById(R.id.textViewCardDescription);
//            this.textViewID = (TextView) itemView.findViewById(R.id.textViewCardID);
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
        TextView textViewName = holder.textViewName;
        TextView textViewState = holder.textViewState;
//        TextView textViewDescription = holder.textViewName;
//        TextView textViewID = holder.textViewState;

        textViewName.setText(dataSet.get(listPosition).getTaskName());
        textViewState.setText(dataSet.get(listPosition).getState());
//        textViewDescription.setText(dataSet.get(listPosition).getDescription());
//        textViewID.setText(dataSet.get(listPosition).getId());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


