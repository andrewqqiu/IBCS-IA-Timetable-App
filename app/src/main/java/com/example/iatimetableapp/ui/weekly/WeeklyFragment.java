package com.example.iatimetableapp.ui.weekly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.iatimetableapp.R;
import com.example.iatimetableapp.Subject;
import com.example.iatimetableapp.databaseHandlerActivity;
import com.example.iatimetableapp.timeHandlerActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyFragment extends Fragment{

    public static final String tag = "WeeklyFragment";

    private WeeklyViewModel weeklyViewModel;

    //Declares class creation inputs
    TextView classNameEntry;
    TextView classLocationEntry;
    TextView classTeacherEntry;

    TextView classTimeEntry1;
    TextView classTimeEntry2;
    TextView classTimeEntry3;
    TextView classTimeEntry4;
    TextView classTimeEntry5;
    TextView classTimeEntry6;
    TextView classTimeEntry7;

    CheckBox classIsDay1;
    CheckBox classIsDay2;
    CheckBox classIsDay3;
    CheckBox classIsDay4;
    CheckBox classIsDay5;
    CheckBox classIsDay6;
    CheckBox classIsDay7;

    //On creation variable initialisation
    databaseHandlerActivity dbHandler;
    timeHandlerActivity timeHandler = new timeHandlerActivity();
    int day;

    ArrayList<String> classNames = new ArrayList<>();
    ArrayList<Subject> classes;

    ArrayList<String> classNames2 = new ArrayList<>();
    ArrayList<Subject> classes2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, "Weekly view created.");

        weeklyViewModel =
                ViewModelProviders.of(this).get(WeeklyViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_weekly, container, false);

        dbHandler = new databaseHandlerActivity(getActivity());

        //On creation GUI elements initialisation (for new class pane).
        classNameEntry = root.findViewById(R.id.newClassPage_classNameEntry);
        classLocationEntry = root.findViewById(R.id.newClassPage_classLocationEntry);
        classTeacherEntry = root.findViewById(R.id.newClassPage_classTeacherEntry);

        classIsDay1 = root.findViewById(R.id.newClassPage_classTimesCheckbox1);
        classIsDay2 = root.findViewById(R.id.newClassPage_classTimesCheckbox2);
        classIsDay3 = root.findViewById(R.id.newClassPage_classTimesCheckbox3);
        classIsDay4 = root.findViewById(R.id.newClassPage_classTimesCheckbox4);
        classIsDay5 = root.findViewById(R.id.newClassPage_classTimesCheckbox5);
        classIsDay6 = root.findViewById(R.id.newClassPage_classTimesCheckbox6);
        classIsDay7 = root.findViewById(R.id.newClassPage_classTimesCheckbox7);

        classTimeEntry1 = root.findViewById(R.id.newClassPage_classTimesEntry1);
        classTimeEntry2 = root.findViewById(R.id.newClassPage_classTimesEntry2);
        classTimeEntry3 = root.findViewById(R.id.newClassPage_classTimesEntry3);
        classTimeEntry4 = root.findViewById(R.id.newClassPage_classTimesEntry4);
        classTimeEntry5 = root.findViewById(R.id.newClassPage_classTimesEntry5);
        classTimeEntry6 = root.findViewById(R.id.newClassPage_classTimesEntry6);
        classTimeEntry7 = root.findViewById(R.id.newClassPage_classTimesEntry7);

        ListView classesList1 = root.findViewById(R.id.weeklyClasses_listView_1);
        ListView classesList2 = root.findViewById(R.id.weeklyClasses_listView_2);


        //Disappears new class option pane on initial opens
        root.findViewById(R.id.newClassPageLayout).setVisibility(View.GONE);
        root.findViewById(R.id.newClassPageLayoutBg).setVisibility(View.GONE);


        //Assigns listener for button to create classes
        Button classButton = (Button) root.findViewById(R.id.weeklyTop_newClassButton);
        classButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Operations on tap
                 root.findViewById(R.id.newClassPageLayout).setVisibility(View.VISIBLE);
                root.findViewById(R.id.newClassPageLayoutBg).setVisibility(View.VISIBLE);
            }
            });

        //Assigns listener for button to close class creation pane
        Button closeClassButton = (Button) root.findViewById(R.id.newClassPage_closeButton);
        closeClassButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Operations on tap
                clearEntries();
                root.findViewById(R.id.newClassPageLayout).setVisibility(View.GONE);
                root.findViewById(R.id.newClassPageLayoutBg).setVisibility(View.GONE);
            }
        });

        //Assigns listener for button to submit classes
        Button submitClassButton = (Button) root.findViewById(R.id.newClassPage_submitButton);
        submitClassButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(validation()){

                    root.findViewById(R.id.newClassPageLayout).setVisibility(View.GONE);
                    root.findViewById(R.id.newClassPageLayoutBg).setVisibility(View.GONE);

                    Subject subject = new Subject(
                            classNameEntry.getText().toString(),
                            classLocationEntry.getText().toString(),
                            classTeacherEntry.getText().toString(),
                            classIsDay1.isChecked(),
                            classIsDay2.isChecked(),
                            classIsDay3.isChecked(),
                            classIsDay4.isChecked(),
                            classIsDay5.isChecked(),
                            classIsDay6.isChecked(),
                            classIsDay7.isChecked(),
                            classTimeEntry1.getText().toString(),
                            classTimeEntry2.getText().toString(),
                            classTimeEntry3.getText().toString(),
                            classTimeEntry4.getText().toString(),
                            classTimeEntry5.getText().toString(),
                            classTimeEntry6.getText().toString(),
                            classTimeEntry7.getText().toString()
                    );

                    dbHandler.setClass(subject);

                    clearEntries();

                    Snackbar.make(v, "New class successfully added", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        //Update list headings
        TextView date1 = root.findViewById(R.id.weeklyClasses_listTitle1);
        TextView date1mo = root.findViewById(R.id.weeklyClasses_listTitle1Date);
        TextView date2 = root.findViewById(R.id.weeklyClasses_listTitle2);
        TextView date2mo = root.findViewById(R.id.weeklyClasses_listTitle2Date);

        Calendar current = Calendar.getInstance();
        DateFormat d1 = new SimpleDateFormat("EEEE");
        String date = d1.format(current.getTime());
        date1.setText(date);

        DateFormat d2 = new SimpleDateFormat("MMMM dd");
        date = d2.format(current.getTime());
        date1mo.setText(date);

        current.add(Calendar.DAY_OF_MONTH, 1);
        DateFormat d3 = new SimpleDateFormat("EEEE");
        date = d3.format(current.getTime());
        date2.setText(date);

        DateFormat d4 = new SimpleDateFormat("MMMM dd");
       date = d4.format(current.getTime());
        date2mo.setText(date);


        //Update current day
        timeHandler.updateDay(getContext());
        day = timeHandler.getDay(getContext());
        Log.i(tag, "Current day: " + day);

        getClasses("day" + day);


        //Create list for first displayed day
        ArrayAdapter<String> listSetup = new ArrayAdapter<String>(
                getActivity(),
                R.layout.listview_classes,
                R.id.listView_classes_textView,
                classNames
        );

        classesList1.setAdapter(listSetup);

        //Create list for next displayed day
        int nextDay = timeHandler.getDay(getContext()) + 1;
        if(nextDay > 7) nextDay = 1;
        Log.i(tag, "next day: " + nextDay);

        classes2 = dbHandler.getClassesOnDay("day" + nextDay);


        for (int i = 0; i < classes2.size(); i++) {
            classNames2.add("Time: " + classes2.get(i).getTime(nextDay) + "\n" + classes2.get(i).getName() + "\nwith " + classes2.get(i).getTeacher() + "\nat " + classes2.get(i).getLocation());
        }

        ArrayAdapter<String> listSetup2 = new ArrayAdapter<String>(
                getActivity(),
                R.layout.listview_classes,
                R.id.listView_classes_textView,
                classNames2
        );

        classesList2.setAdapter(listSetup2);


        return root;
    }

    public void getClasses(String day){
        classes = dbHandler.getClassesOnDay(day);

        int dayNumber = timeHandler.getDay(getContext());

        for (int i = 0; i < classes.size(); i++) {
            classNames.add("Time: " + classes.get(i).getTime(dayNumber) + "\n" + classes.get(i).getName() + "\nwith " + classes.get(i).getTeacher() + "\nat " + classes.get(i).getLocation());
        }
    }

    //Validates inputs on new class pane
    private boolean validation(){
        if(classNameEntry.getText().toString().equals(""))
            classNameEntry.setError("You must enter a class name");
        else if(classLocationEntry.getText().toString().equals(""))
            classLocationEntry.setError("You must enter a location");
        else if(classTeacherEntry.getText().toString().equals(""))
            classTeacherEntry.setError("You must enter a teacher");

        else return true;

        return false;
    }

    //Clears input fields after successful submission
    private void clearEntries(){
        classNameEntry.setText("");
        classLocationEntry.setText("");
        classTeacherEntry.setText("");

        classTimeEntry1.setText("");
        classTimeEntry2.setText("");
        classTimeEntry3.setText("");
        classTimeEntry4.setText("");
        classTimeEntry5.setText("");
        classTimeEntry6.setText("");
        classTimeEntry7.setText("");
    }

}