package com.example.iatimetableapp.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.iatimetableapp.R;
import com.example.iatimetableapp.Subject;
import com.example.iatimetableapp.Task;
import com.example.iatimetableapp.databaseHandlerActivity;
import com.example.iatimetableapp.timeHandlerActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class tasksFragment extends Fragment {

    private tasksViewModel tasksViewModel;

    //Declares task creation inputs
    TextView taskNameEntry;
    TextView taskAssoClassEntry;
    TextView taskDeadline;

    //On creation variable initialisation
    databaseHandlerActivity dbHandler;

    ArrayList<String> tasksStrings = new ArrayList<>();
    ArrayList<Task> tasks;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksViewModel =
                ViewModelProviders.of(this).get(tasksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        dbHandler = new databaseHandlerActivity(getActivity());

        //On creation GUI elements initialisation
        taskNameEntry = root.findViewById(R.id.newTasksPage_taskNameEntry);
        taskAssoClassEntry = root.findViewById(R.id.newTasksPage_taskAssociatedClassEntry);
        taskDeadline = root.findViewById(R.id.newTasksPage_taskDeadlineEntry);

        ListView tasksList = root.findViewById(R.id.tasksTasks_listView);


        //Disappears new class option pane on initial opens
        root.findViewById(R.id.newTasksPageLayout).setVisibility(View.GONE);
        root.findViewById(R.id.newTasksPageLayoutBg).setVisibility(View.GONE);

        //Assigns listener for button to create tasks
        Button tasksButton = (Button) root.findViewById(R.id.tasksTop_newTaskButton);
        tasksButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Operations on tap
                root.findViewById(R.id.newTasksPageLayout).setVisibility(View.VISIBLE);
                root.findViewById(R.id.newTasksPageLayoutBg).setVisibility(View.VISIBLE);
            }
        });

        //Assigns listener for button to close tasks creation pane
        Button closeTaskButton = (Button) root.findViewById(R.id.newTasksPage_closeButton);
        closeTaskButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Operations on tap
                clearEntries();
                root.findViewById(R.id.newTasksPageLayout).setVisibility(View.GONE);
                root.findViewById(R.id.newTasksPageLayoutBg).setVisibility(View.GONE);
            }
        });

        //Assigns listener for button to submit classes
        Button submitTaskButton = (Button) root.findViewById(R.id.newTasksPage_taskSubmissionButton);
        submitTaskButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(validation()){

                    root.findViewById(R.id.newTasksPageLayout).setVisibility(View.GONE);
                    root.findViewById(R.id.newTasksPageLayoutBg).setVisibility(View.GONE);

                    Task task = new Task(
                            taskNameEntry.getText().toString(),
                            taskAssoClassEntry.getText().toString(),
                            taskDeadline.getText().toString()
                    );

                    dbHandler.setTask(task);

                    clearEntries();

                    Snackbar.make(v, "New task successfully added", Snackbar.LENGTH_SHORT).show();
                }
                //Using instance of databaseHandlerActivity class,

            }
        });

        //Creates list of tasks
        getTasks();

        ArrayAdapter<String> listSetup = new ArrayAdapter<String>(
                getActivity(),
                R.layout.listview_classes,
                R.id.listView_classes_textView,
                tasksStrings
        );

        tasksList.setAdapter(listSetup);

        return root;
    }

    private void clearEntries(){
        taskNameEntry.setText("");
        taskAssoClassEntry.setText("");
        taskDeadline.setText("");
    }

    private boolean validation(){
        if(taskNameEntry.getText().toString().equals(""))
            taskNameEntry.setError("You must enter a task name");
        else if(taskAssoClassEntry.getText().toString().equals(""))
            taskAssoClassEntry.setError("You must enter an associated class");
        else if(taskDeadline.getText().toString().equals(""))
            taskDeadline.setError("You must enter a deadline");

        else return true;

        return false;
    }

    public void getTasks(){
        tasks = dbHandler.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            tasksStrings.add("#" + tasks.get(i).getId() + " - " + tasks.get(i).getName() + "\n" + tasks.get(i).getAssoClass() + "\nDue " + tasks.get(i).getDeadline());
        }
    }
}