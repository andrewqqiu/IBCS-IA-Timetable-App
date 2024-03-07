package com.example.iatimetableapp.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.iatimetableapp.R;
import com.example.iatimetableapp.databaseHandlerActivity;
import com.example.iatimetableapp.ui.tasks.tasksViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class settingsFragment extends Fragment {

    public static final String tag = "SettingsFragment";

    public static settingsFragment newInstance() {
        return new settingsFragment();
    }

    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        final databaseHandlerActivity dbHandler = new databaseHandlerActivity(getActivity());

        Button dbClearButton = (Button) root.findViewById(R.id.settings_databaseClearButton);
        dbClearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dbHandler.deleteAll();
                Snackbar.make(v, "Database cleared", Snackbar.LENGTH_SHORT).show();
            }
        });


        //Declare preferences for later use.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = preferences.edit();

        //Buttons for adjusting day
        //Button to add a day
        Button plus1Day = (Button) root.findViewById(R.id.settings_incrementPlus1);
        plus1Day.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor edit = preferences.edit();

                //updates day
                int day = preferences.getInt("current day", 1);
                day++;
                if(day > 7) day = 1;

                Log.i(tag, "Day incremented, now " + day);

                //puts to preference.
                edit.putInt("current day", day);
                edit.apply();

                Snackbar.make(v, "Incremented one day, current day now " + day, Snackbar.LENGTH_SHORT).show();
            }
        });

        //Button to reduce a day
        Button minus1Day = (Button) root.findViewById(R.id.settings_incrementMinus1);
        minus1Day.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //updates day
                int day = preferences.getInt("current day", 1);
                day--;
                if(day < 1) day = 7;

                Log.i(tag, "Day deducted, now " + day);

                //puts to preference
                edit.putInt("current day", day);
                edit.apply();

                Snackbar.make(v, "Deducted one day, current day now " + day, Snackbar.LENGTH_SHORT).show();
            }
        });


        //Adjusting username
        String username = preferences.getString("username", "username");
        TextView usernameInput = root.findViewById(R.id.settings_usernameInput);
        usernameInput.setText(username);

        Button usernameChange = (Button) root.findViewById(R.id.settings_usernameButton);
        usernameChange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String newUsername = usernameInput.getText().toString();
                edit.putString("username", newUsername);
                edit.apply();

                Snackbar.make(v, "Username set to " + newUsername, Snackbar.LENGTH_SHORT).show();
            }
        });


        //Delete classes
        Button removeTask = (Button) root.findViewById(R.id.settings_taskEditButton);
        removeTask.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                TextView input = root.findViewById(R.id.settings_taskRemovalInput);
                int inputID = Integer.parseInt(input.getText().toString());

                String taskName = dbHandler.getTask(inputID).getName();

                databaseHandlerActivity db = new databaseHandlerActivity(getContext());
                db.deleteTask(inputID);

                Snackbar.make(v, "Deleted task \"" + taskName + "\".", Snackbar.LENGTH_SHORT).show();
            }
        });

        //Delete class
        Button removeClass = (Button) root.findViewById(R.id.settings_classEditButton);
        removeClass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                TextView input = root.findViewById(R.id.settings_classRemovalInput);
                int inputID = Integer.parseInt(input.getText().toString());

                String className = dbHandler.getClass(inputID).getName();

                databaseHandlerActivity db = new databaseHandlerActivity(getContext());
                db.deleteClass(inputID);

                Snackbar.make(v, "Deleted class \"" + className + "\".", Snackbar.LENGTH_SHORT).show();
            }
        });

        return root;

    }

}