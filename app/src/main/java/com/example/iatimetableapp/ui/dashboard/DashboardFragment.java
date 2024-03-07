package com.example.iatimetableapp.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.example.iatimetableapp.MainActivity;
import com.example.iatimetableapp.R;
import com.example.iatimetableapp.Subject;
import com.example.iatimetableapp.Task;
import com.example.iatimetableapp.databaseHandlerActivity;
import com.example.iatimetableapp.timeHandlerActivity;

import java.util.*;
import java.text.*;

public class DashboardFragment extends Fragment {

    public static final String tag = "DashboardFragment";

    private DashboardViewModel dashboardViewModel;

    //On creation variable initialisation
    databaseHandlerActivity dbHandler;
    timeHandlerActivity timeHandler = new timeHandlerActivity();
    int day;

    ArrayList<String> classNames = new ArrayList<>();
    ArrayList<Subject> classes;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, "Dashboard view created.");

        dbHandler = new databaseHandlerActivity(getActivity());

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //On creation GUI elements declaration
        ListView classesList = root.findViewById(R.id.dashboardClasses_listView);
        TextView dayTitle = root.findViewById(R.id.dashboardClasses_dayTitle);
        TextView dateTitle = root.findViewById(R.id.dashboardTop_currentDate);
        TextView username = root.findViewById(R.id.dashboardTop_username);

        //Update top line for username and current date.
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        dateTitle.setText("Today is " + date);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        username.setText("Hi, " + preferences.getString("username", "username") + "!");

        //Update current 1 to 7 day
        timeHandler.updateDay(getContext());
        day = timeHandler.getDay(getContext());
        Log.i(tag, "Current day: " + day);

        dayTitle.setText("is Day " + day);

        //Update classes to day
        getClasses("day" + day);


        ArrayAdapter<String> listSetup = new ArrayAdapter<String>(
                getActivity(),
                R.layout.listview_classes,
                R.id.listView_classes_textView,
                classNames
        );

        classesList.setAdapter(listSetup);

        return root;
    }

    public void getClasses(String day){
        classes = dbHandler.getClassesOnDay(day);

        int dayNumber = timeHandler.getDay(getContext());

        for (int i = 0; i < classes.size(); i++) {
            classNames.add("Time: " + classes.get(i).getTime(dayNumber) + "\n" + classes.get(i).getName() + "\nwith " + classes.get(i).getTeacher() + "\nat " + classes.get(i).getLocation());
        }
    }



}