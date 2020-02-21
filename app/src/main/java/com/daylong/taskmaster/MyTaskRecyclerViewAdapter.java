package com.daylong.taskmaster;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<TaskData> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
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
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getTaskName());
        textViewVersion.setText(dataSet.get(listPosition).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}



//public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {
//
//    private final List<DummyItem> mValues;
//    private final OnListFragmentInteractionListener mListener;
//
//    public MyTaskRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).task);
//
//        holder.mView.setOnClickListener((event) -> {
//            Log.i("DaylongTheGreat", "Stuff Works!!!" + holder.mItem.id);
//        });
//
//        //
//        // For the listener on AllTasks
//        //
////        holder.mView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (null != mListener) {
////                    // Notify the active callbacks interface (the activity, if the
////                    // fragment is attached to one) that an item has been selected.
////                    mListener.onListFragmentInteraction(holder.mItem);
////                }
////            }
////        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public DummyItem mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
//}