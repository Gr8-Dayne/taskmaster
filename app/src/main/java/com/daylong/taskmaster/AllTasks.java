package com.daylong.taskmaster;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


// Credit: https://codingwithmitch.com/
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
// Credit: https://codinginflow.com/tutorials/android/open-a-new-activity-and-pass-variables
// Credit: https://proandroiddev.com/a-guide-to-recyclerview-selection-3ed9f2381504
public class AllTasks extends AppCompatActivity {

    public static final String NAME_REPLACE = "String.NAME_REPLACE";
    public static final String ID_REPLACE = "String.ID_REPLACE";
    static View.OnClickListener myOnClickListener;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private static ArrayList<TaskData> data;
    private static ArrayList<Integer> removedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Research more about this later
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<TaskData>();
        for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {
            data.add(new TaskData(HardCodedTasks.taskNameArray[i], HardCodedTasks.descriptionArray[i], HardCodedTasks.stateArray[i], HardCodedTasks.id[i]));
        }

        removedItems = new ArrayList<Integer>();
        adapter = new MyTaskRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
    }


    public class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            toTaskDetailView(v);
        }

//        private void removeItem(View r) {
//
//            int selectedItemPosition = recyclerView.getChildPosition(r);
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
//
//            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//
//            String selectedName = (String) textViewName.getText();
//
//            int selectedItemId = -1;
//
//            for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {
//                if (selectedName.equals(HardCodedTasks.taskNameArray[i])) {
//                    selectedItemId = HardCodedTasks.id[i];
//                }
//            }
//
//            removedItems.add(selectedItemId);
//            data.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
//        }
    }

//    private void addRemovedItemToBin() {
//
//        int addItemAtListPosition = 3;
//
//        data.add(addItemAtListPosition, new TaskData(HardCodedTasks.taskNameArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.id[removedItems.get(0)]));
//
//        adapter.notifyItemInserted(addItemAtListPosition);
//
//        removedItems.remove(0);
//    }


    // Credit: https://stackoverflow.com/questions/33897978/android-convert-edittext-to-string
    public void toTaskDetailView(View d) {

        int selectedItemPosition = recyclerView.getChildPosition(d);

        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
        TextView textViewName = viewHolder.itemView.findViewById(R.id.textViewName);
        String selectedName = (String) textViewName.getText();

        String selectedItemName = " ";
        String selectedItemDescription = " ";
        String selectedItemState = " ";
        int selectedItemId = -1;

        for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {

            if (selectedName.equals(HardCodedTasks.taskNameArray[i])) {
                //
                // Info for selected Task
                //
                selectedItemName = HardCodedTasks.taskNameArray[i];
                selectedItemDescription = HardCodedTasks.descriptionArray[i];
                selectedItemState = HardCodedTasks.stateArray[i];
                selectedItemId = HardCodedTasks.id[i];
            }
        }

        TextView textViewNameOfTask = findViewById(R.id.textViewName);
        String text = textViewNameOfTask.getText().toString();

        EditText textViewIDOfTask = findViewById(selectedItemId);
//        int number = Integer.parseInt(textViewIDOfTask.getText().toString());

        Intent showTaskDetailsFromTaskDetailPage = new Intent(this, TaskDetail.class);

        showTaskDetailsFromTaskDetailPage.putExtra(NAME_REPLACE, text);
//        showTaskDetailsFromTaskDetailPage.putExtra(ID_REPLACE, number);

        startActivity(showTaskDetailsFromTaskDetailPage);
    }
}


