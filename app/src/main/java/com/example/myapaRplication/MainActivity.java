package com.example.myapaRplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import me.tankery.lib.circularseekbar.CircularSeekBar;



public class MainActivity extends AppCompatActivity {
    private CircularSeekBar circularSeekBar; //The Slider for turning time
    private HashMap<Integer,HourScreen> hourScreenHashMap;
    private Integer hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circularSeekBar = findViewById(R.id.circularSeekBar);
        hourScreenHashMap = new HashMap<>();

        //set up the views for each hour in a HashMap
        for(int i = 0; i<=23; i++){
            hourScreenHashMap.put(i,new HourScreen(new NotesWidget(), new ToDoWidget(),new AlarmWidget()));
        }

        hour = (int)circularSeekBar.getProgress(); //get initial value of circular seek
        displayEntriesForHour(hour); //initial update of the views on screen


        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                TextView hourDisplay = findViewById(R.id.timeHour); //accesses the hour display text
                hour = (int) progress; //adjusts hour to the time
                hourDisplay.setText(hour.toString()); //sets the text displaying the time to the hour

                // add code to update the UI based on the selected hour
                displayEntriesForHour(hour);
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                // Handle start of touch event
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                // Handle stop of touch event
            }
        });

        Button writeNoteButton = findViewById(R.id.writeNote_button); //the button to initiate notes
        writeNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "writeNoteButton clicked"); //log note information
                showNotesInformation(hour);
            }
        });

        TextView toDoButton = findViewById(R.id.todo_button);
        toDoButton.setOnClickListener(new View.OnClickListener() { //button to initiate todos
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "toDoNoteButton clicked");
                showToDoInformation(hour); //generate todo method
            }

        });


        Button saveNoteButton = findViewById(R.id.saveNotesButton); //button to sve note
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "saveNoteButton clicked");
                saveNotesInformation(hour);
                // Show a toast message
                try {
                    Toast.makeText(getBaseContext(), "Note saved for hour: " + hour, Toast.LENGTH_SHORT).show();
                    //gives a notification to the phone
                } catch(Exception e){
                    Log.d("MainActivity","Toast Failed"); //log if failed for some reason
                }

            }
        });

        ImageButton addToDoButton = findViewById(R.id.toDoAddButton); //button to add todo elements
        addToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "addToDoButton clicked");
                addToDoContents();

            }
        });

        Button saveToDoButton = findViewById(R.id.saveToDoButton);
        saveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "saveToDoButton clicked");

                saveToDoContents();

            }
        });

        ImageButton alarmSetButton = findViewById(R.id.alarmSetButton);
        alarmSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "alarmbutton clicked");
                setAlarm();
            }
        });

        Button alarmShowButton = findViewById(R.id.alarmShowButton);
        alarmShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourScreenHashMap.get(hour).setShowAlarmButtonClicked(true); //sets the alarm boolean to true
                displayEntriesForHour(hour); //refresh screen method
            }
        });

        Button dayListButton = findViewById(R.id.dayListButton); //"Your day" button to show daylist
        dayListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ConstraintLayout mainHolder = findViewById(R.id.mainContentHolder);
                ConstraintLayout dayListHolder = findViewById(R.id.dayListHolder);
               mainHolder.setVisibility(View.GONE);  //hides the main holder --> the hour contents
               dayListHolder.setVisibility(View.VISIBLE); //shows the day holder --> the daylist contents

               generateDayListContents();
            }
        });

        Button hourGoButton = findViewById(R.id.hourGoButton);// "Your hours" button to show daylist
        hourGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout mainHolder = findViewById(R.id.mainContentHolder);
                ConstraintLayout dayListHolder = findViewById(R.id.dayListHolder);
                mainHolder.setVisibility(View.VISIBLE);   //shows the main holder --> the hour contents
                dayListHolder.setVisibility(View.GONE); //hides the day holder --> the daylist contents
            }
        });
    }

    // Method to display entries for the selected hour (this will be implemented later)
    private void displayEntriesForHour(int hour) {
        HourScreen currentHour = hourScreenHashMap.get(hour);

        //checking for Notes
        if(currentHour.getNotesTitle()!=null){ //if there are notes, the title shouldnt be null
            ScrollView notesBox = findViewById(R.id.notesBox);
            TextView notesBodyText = findViewById(R.id.notesBodyText);
            TextView notesTitle= findViewById(R.id.notesTitle);
            notesTitle= findViewById(R.id.notesTitle);
            Button writeNoteButton = findViewById(R.id.writeNote_button); //the writenot button
            writeNoteButton.setVisibility(View.GONE); //hides the note button
            notesTitle.setText(currentHour.getNotesTitle()); //sets notes to the title stored in the class
            notesBodyText.setText(currentHour.getNotesBody()); //sets notes text to the body
            notesBox.setVisibility(View.VISIBLE); //shows the notes

        } else{
            ScrollView notesBox = findViewById(R.id.notesBox);
            TextView notesBodyText = findViewById(R.id.notesBodyText);
            TextView notesTitle= findViewById(R.id.notesTitle);
            Button writeNoteButton = findViewById(R.id.writeNote_button);
            writeNoteButton.setVisibility(View.VISIBLE);
            notesBox.setVisibility(View.GONE);  //otherwise show the button and make sure text bodytext have nothing
            notesTitle.setText("");
            notesBodyText.setText("");
        }

        //to do Clicked
        if(currentHour.getToDoButtonClicked()){ //if this hour object's to do was clicked
            ConstraintLayout toDoBox = findViewById(R.id.toDoBox);
            TextView toDoButton = findViewById(R.id.todo_button);
            toDoBox.setVisibility(View.VISIBLE); //setting the box to visible and generating contents
            toDoButton.setVisibility(View.GONE);
            generateToDoContents(currentHour);

        } else{
            ConstraintLayout toDoBox = findViewById(R.id.toDoBox);
            TextView toDoButton = findViewById(R.id.todo_button);
            toDoBox.setVisibility(View.GONE); //if the button was never clicked, dont show
            toDoButton.setVisibility(View.VISIBLE);

        }

        //alarm clicked
        if(currentHour.getShowAlarmButtonClicked()){ //same system for alarm button as the other buttons
            Button alarmShowButton = findViewById(R.id.alarmShowButton);
            alarmShowButton.setVisibility(View.GONE);
            ConstraintLayout alarmBox = findViewById(R.id.alarmBox);
            alarmBox.setVisibility(View.VISIBLE);

        } else{
            Button alarmShowButton = findViewById(R.id.alarmShowButton);
            alarmShowButton.setVisibility(View.VISIBLE);
            ConstraintLayout alarmBox = findViewById(R.id.alarmBox);
            alarmBox.setVisibility(View.GONE);
        }


    }

    private long calculateTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()); //sets calendar class and acquires time

        // Set the calendar to the specified time (e.g., 8:00 AM)
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Check if the time is in the past and add one day if it is
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

       return calendar.getTimeInMillis(); //returns the time in millis
    }

    private void setAlarm(){
        HourScreen currentHour = Objects.requireNonNull(hourScreenHashMap.get(hour));
        currentHour.setAlarmButtonClicked(!(currentHour.getAlarmButtonClicked())); //flips the boolean for alarm button
        EditText alarmTitle = findViewById(R.id.alarmTitleText);
        currentHour.getAlarmWidget().setWidgetTitle(alarmTitle.getText().toString());
        //access relevant objects: page, button, alarm, etc.

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        Intent intent = new Intent(this, alarmReceiver.getClass()); //specific classes for setting up timed broadcasts
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        TextView alarmStatus = findViewById(R.id.alarmStatusText); //accesses the status text

        if(currentHour.getAlarmButtonClicked()){ //if the alarm is toggled to on

            alarmStatus.setText("Your alarm is set");
            long triggerTime = calculateTime(); //calculates time function

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
                Toast.makeText(this, "Alarm set for  " + hour + ":00", Toast.LENGTH_SHORT).show();
                //adds the reminder to the alarm manager service and gives a notification
            }
        } else{
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent); //if the alarm is cancelled then itll cancel services
                alarmStatus.setText("Your alarm is unset");
                Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
                AlarmReceiver.stopRinging(); //stops the ringing service
            }
        }
    }



    private void generateToDoContents(HourScreen currentHour){
        LinearLayout toDoBoxLinearLayout = findViewById(R.id.toDoBoxLinearLayout);
        toDoBoxLinearLayout.removeAllViews(); //refreshes the todo box
        ArrayList<EditText> generatedEditTexts = new ArrayList<>(); //creates an array to store the generated edit texts

        for(String text : currentHour.getToDo().getToDoContents()){
            ConstraintLayout toDoElement = getBox(currentHour, text,generatedEditTexts);
            toDoBoxLinearLayout.addView(toDoElement);
        }
        currentHour.getToDo().setEditTexts(generatedEditTexts); //saves the array of edittexts in the currenthour
        //this array is accessed when saving the edittext contents
    }

    private void saveToDoContents(){
        ToDoWidget toDoList = Objects.requireNonNull(hourScreenHashMap.get(hour)).getToDo(); //accesses toDo Class

        ArrayList<String> contentsToSave = new ArrayList<>(); //array to hold contents to save
        for(EditText editText : toDoList.getEditTexts()){ //accesses the edit text arraylists in the class
            contentsToSave.add(String.valueOf(editText.getText()));  //accesses the text in them and uploads them to class
        }
        toDoList.setToDoContents(contentsToSave); //uploads the array to be stored in the class
        Log.d("saveToDoContents",contentsToSave.toString());
        Log.d("saveToDoContents actual saved", toDoList.getToDoContents().toString());
    }
    private void addToDoContents(){
        String text = "";
        HourScreen currentHour = Objects.requireNonNull(hourScreenHashMap.get(hour));
        currentHour.getToDo().addToDoContents(text);
        //adds an empty string to the todo arrays, which will make the generation method create an empty edit text
        displayEntriesForHour(hour); //refreshes screen

    }

    private void generateDayListContents(){
        TableLayout contentTable = findViewById(R.id.contentTable);
        contentTable.removeAllViews(); //acts to refresh the table

        for(int i = 0; i<24; i++){
            if(hourScreenHashMap.get(i).getNotesTitle()!=null || hourScreenHashMap.get(i).getAlarmButtonClicked() || hourScreenHashMap.get(i).getToDoButtonClicked()){
            getHourRow(contentTable, i);}
            //goes through all hours and checks if any content exists in the relevant instances, if so then generates the content

        }
    }

    private TextView placeHolderTextGen(TableRow.LayoutParams params){

        TextView placeHolderTextView = new TextView(this); //generating the placeholder textview
        placeHolderTextView.setGravity(Gravity.CENTER);
        placeHolderTextView.setPadding(8,8,8,8);


        placeHolderTextView.setLayoutParams(params); //simple weight parameters from the gethourRow method
        placeHolderTextView.setBackground(Drawable.createFromPath("#00E0E0E0"));
        placeHolderTextView.setTextSize(12); placeHolderTextView.setText("");

        return placeHolderTextView;
    }

    private void getHourRow(TableLayout contentTable, Integer hourSelected){
        HourScreen currentHour = hourScreenHashMap.get(hourSelected); //accesses the object corresponding to the hour pages information

        //first row layout
        TableRow firstTableRow = new TableRow(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        firstTableRow.setPadding(0,24,0,16);
        firstTableRow.setLayoutParams(layoutParams);

        //creating the time text layout wtih certain parameters
        TextView timeText = new TextView(this);
        timeText.setGravity(Gravity.CENTER); //sets the text to be in the center of its row column
        timeText.setPadding(8,8,8,8); //padding for spacing
        Typeface alata = ResourcesCompat.getFont(this, R.font.alata); //setting the font
        timeText.setTypeface(alata);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.3F //sets the weight to be relatively low, so it takes up less space on the row
        );

        timeText.setLayoutParams(params); //sets params
        timeText.setBackground(Drawable.createFromPath("#00E0E0E0"));
        timeText.setTextSize(12);

        if(hourSelected.toString().length()==1){ //if hour is a 1 digit number
            timeText.setText("0"+hourSelected+"00");
        } else{
            timeText.setText(hourSelected+"00");
        }
        firstTableRow.addView(timeText); //the time text will always be added



        //creating the notes text and applyng parameters
        TextView notesText = new TextView(this);
        notesText.setGravity(Gravity.CENTER);
        notesText.setPadding(8,8,8,8);
        Typeface actor = ResourcesCompat.getFont(this, R.font.actor);
        notesText.setTypeface(actor);
        TableRow.LayoutParams titleParams = new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.2F
        ); //sets weight to be high to take up more space on the row
        notesText.setLayoutParams(titleParams); //sets params
        notesText.setBackground(Drawable.createFromPath("#00E0E0E0"));
        notesText.setTextSize(14);
        if(currentHour.getNotesTitle()!=null) {
            notesText.setText("Notes: " + currentHour.getNotesTitle());
            notesText.setBackground((getDrawable(R.drawable.tablecolumnborders)));
        }else{
            notesText.setText("");
        }

        firstTableRow.addView(notesText);
        contentTable.addView(firstTableRow);

        //toDo text

       TextView placeHolderTextView = placeHolderTextGen(params);
       TextView placeHolderTextViewTwo = placeHolderTextGen(params);
       //creating two placeholder texts to take up space on the column and create indents


        try{
           String toDoTitle = (currentHour.getToDo().getToDoContents().get(0)); //if the todo content exists
            TableRow secondTableRow = new TableRow(this); //generating the to do row
            secondTableRow.setLayoutParams(layoutParams);

            TextView toDoText = new TextView(this);
            toDoText.setGravity(Gravity.CENTER);
            toDoText.setPadding(8,8,8,8);
            toDoText.setTypeface(actor);
            toDoText.setLayoutParams(titleParams);
            toDoText.setBackground(Drawable.createFromPath("#00E0E0E0"));
            toDoText.setTextSize(14);
            toDoText.setText("To-Do: " + toDoTitle);


            secondTableRow.addView(placeHolderTextView); //adding the placeholder textview
            toDoText.setBackground((getDrawable(R.drawable.tablecolumnborders)));
            secondTableRow.setPadding(0,0,0,16);
            secondTableRow.addView(toDoText); //adding the todo textview

            contentTable.addView(secondTableRow);

        }catch(Exception e){ //does nothing if it the todo odesnt exist

        }

        //alarm
        if(currentHour.getAlarmWidget().getWidgetTitle() != null) { //if the alarm title exists

            TableRow thirdTableRow = new TableRow(this); //creating the alarm row with the same parameters as todo
            thirdTableRow.setLayoutParams(layoutParams);

            TextView alarmText = new TextView(this);
            alarmText.setGravity(Gravity.CENTER);
            alarmText.setPadding(8,8,8,8);
            alarmText.setTypeface(actor);
            alarmText.setLayoutParams(titleParams);
            alarmText.setBackground(Drawable.createFromPath("#00E0E0E0"));
            alarmText.setTextSize(14);
            alarmText.setText("Alarm: " + currentHour.getAlarmWidget().getWidgetTitle());


            thirdTableRow.addView(placeHolderTextViewTwo);
            alarmText.setBackground((getDrawable(R.drawable.tablecolumnborders)));
            thirdTableRow.setPadding(0,0,0,16);
            thirdTableRow.addView(alarmText);
            contentTable.addView(thirdTableRow);

        }



    }
    private ConstraintLayout getBox(HourScreen currentHour, String text, ArrayList<EditText> generatedEditTexts){

        ConstraintLayout constraintLayout = new ConstraintLayout(this); //creates a constraint layout and
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams( //applies relevent parameters
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        constraintLayout.setLayoutParams(layoutParams); //e,g this will fill the todo width and expand to fill content
        constraintLayout.setMinHeight(50);

        //creates an editText with certain layout parameters
        EditText editText = new EditText(this);
        editText.setId(View.generateViewId());
        ConstraintLayout.LayoutParams editTextParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        editTextParams.setMargins(8, 0, 0, 0);
        editText.setLayoutParams(editTextParams);
        editText.setBackground(null);
        editText.setEms(10);
        editText.setHint(R.string.toDoHint);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setMinHeight(48);
        editText.setTextSize(12);
        editText.setTypeface(ResourcesCompat.getFont(this, R.font.actor));
        constraintLayout.addView(editText);

        //adding the edit text contents
        editText.setText(text);
        editText.setHint("Drink some water");
        generatedEditTexts.add(editText); //adds the object to the array of edit texts to be stored in the array

        return constraintLayout;


    }

    private void showToDoInformation(int hour){
        try{
        HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
        currentHour.setToDoButtonClicked(true); //records that the button was clicked
        displayEntriesForHour(hour); //updates page wth that information

            Log.d("MainActivity showToDoInformation","succeeded showing To Do Information");
        } catch(Exception e){
            Log.e("MainActivity showToDoInformation","failed to show To Do Information");
        }


    }

    private void showNotesInformation(int hour){
        try {
        HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
        currentHour.setNotes("Write your title!","Write your note!"); //sets a placeholder value for the notes
        displayEntriesForHour(hour); //updates the page
        Log.d("MainActivity showNotesInformation","succeeded showing To Notes Information");
    } catch(Exception e){
        Log.e("MainActivity showNotesInformation","failed to show Notes Information");
    }

    }
    private void saveNotesInformation(int hour){
        try {
            TextView notesTitle= findViewById(R.id.notesTitle);
            TextView notesBodyText = findViewById(R.id.notesBodyText);
            HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
            currentHour.setNotes(notesTitle.getText().toString(), notesBodyText.getText().toString()); //saves the notes in the class object
            Log.d("MainActivity savedNotesInformation","succeeded Save Notes");
        } catch(Exception e){
            Log.e("MainActivity savedNotesInformation","failed to Save Notes");
        }

    }

}