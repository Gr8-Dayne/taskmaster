package com.daylong.taskmaster;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


// Credit: https://codingwithmitch.com/
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
public class AllTasks extends AppCompatActivity {

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


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }



        //
        //
        //
        //
        //
        @Override
        public void onClick(View v) {

            //
            //
            // I need to ensure that TaskDetail gives me the information about the Task I click on
            // How can I transfer a Task Data Instance between classes?
            //
            //

//            TextView taskTitleInDepth = findViewById(R.id.taskDetailTitle);
//            TextView taskDescriptionInDepth = findViewById(R.id.taskDetailDescription);
//            TextView taskIDInDepth = findViewById(R.id.chronologicalTaskID);
//
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
//
//            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//
//            String selectedName = (String) textViewName.getText();
//
//            int selectedItemId = -1;
//
//            for (int i = 0; i < HardCodedTasks.nameArray.length; i++) {
//                if (selectedName.equals(HardCodedTasks.nameArray[i])) {
//                    selectedItemId = HardCodedTasks.id[i];
//                }
//            }
//
//            removedItems.add(selectedItemId);
//            data.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
//
//            if (customUsername != null) {
//                taskIDInDepth.setText("Task Number: " + );
//            }

        }
        //
        //
        //
        //
        //



        private void removeItem(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);

            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);

            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);

            String selectedName = (String) textViewName.getText();

            int selectedItemId = -1;

            for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {
                if (selectedName.equals(HardCodedTasks.taskNameArray[i])) {
                    selectedItemId = HardCodedTasks.id[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }

    private void addRemovedItemToList() {

        int addItemAtListPosition = 3;

        data.add(addItemAtListPosition, new TaskData(HardCodedTasks.taskNameArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.id[removedItems.get(0)]));

        adapter.notifyItemInserted(addItemAtListPosition);

        removedItems.remove(0);
    }
}



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button mButton = (Button) findViewById(R.id.button);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(AllTasks.this, SecondActivity.class));
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        if (item.getItemId() == R.id.add_item) {
//            //check if any items to add
//            if (removedItems.size() != 0) {
//                addRemovedItemToList();
//            } else {
//                Toast.makeText(this, "Nothing to add", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return true;
//    }