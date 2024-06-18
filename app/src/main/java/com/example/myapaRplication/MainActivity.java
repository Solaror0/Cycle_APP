package com.example.myapaRplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

import me.tankery.lib.circularseekbar.CircularSeekBar;


/**
 * Title: CYCLE
 * Description: This app allows the user to cycle through each hour in the day and set notes, to-do lists, and alarms
 * as they wish. It also allows the user to see all the hours they've modified in the day.
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class MainActivity extends AppCompatActivity {
    private CircularSeekBar circularSeekBar; //The Slider for turning time
    private HashMap<Integer,HourScreen> hourScreenHashMap;
    private Integer hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularSeekBar = findViewById(R.id.circularSeekBar);
        hourScreenHashMap = new HashMap<>(); //variable setup, the onCreate function is similar to a Cosntructor


        //set up the views for each hour in a HashMap
        for(int i = 0; i<=23; i++){
            hourScreenHashMap.put(i,new HourScreen(new NotesWidget(), new ToDoWidget(),new AlarmWidget()));
        }

        hour = (int)circularSeekBar.getProgress(); //get initial value of circular seek
        displayEntriesForHour(hour); //initial update of the views on screen



        // The below contains onClickListeners fur interactables like the circular seek and button clicks.
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
               saveAll(hour); //saves the content for that hour!
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
                hourScreenHashMap.get(hour).getAlarmWidget().setAlarmShow(true); //sets the alarm boolean to true
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
    } //BUTTON ON CLICK LISTENERS END HERE




    //LARGER/MORE BROAD METHODS
    /**
     * This method is a central method that is used to refresh the screen.
     * It checks different booleans and updates the texts & views accordingly
     * It is called by many methods dealing with content because it acts as a centre to call methods for different widgets
     * @param hour The hour currently selected by the circular seek button
     */
    private void displayEntriesForHour(int hour) {
        HourScreen currentHour = hourScreenHashMap.get(hour);

        //checking for Notes
        if(currentHour.getNotesWidget().getShowNotes()){ //if there are notes, the title shouldnt be null
            ScrollView notesBox = findViewById(R.id.notesBox);
            TextView notesBodyText = findViewById(R.id.notesBodyText);
            TextView notesTitle= findViewById(R.id.notesTitle);
            Button writeNoteButton = findViewById(R.id.writeNote_button); //the writenot button
            writeNoteButton.setVisibility(View.GONE); //hides the note button
            notesTitle.setText(currentHour.getNotesWidget().getWidgetTitle()); //sets notes to the title stored in the class
            notesBodyText.setText(currentHour.getNotesWidget().getNotes()); //sets notes text to the body
            notesTitle.setTextColor(Color.parseColor("#494646"));
            notesBodyText.setTextColor(Color.parseColor("#494646"));
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
        if(currentHour.getToDo().getShowToDo()){ //if this hour object's to do was clicked
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
        if(currentHour.getAlarmWidget().getAlarmShow()){ //same system for alarm button as the other buttons
            Button alarmShowButton = findViewById(R.id.alarmShowButton);
            TextView alarmTitle = findViewById(R.id.alarmTitleText);
            alarmTitle.setText(currentHour.getAlarmWidget().getWidgetTitle());
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

    private void saveAll(int hour){
        saveToDoContents();
        saveNotesInformation(hour);
        saveAlarm();

    }

    //METHODS RELATING TO DAY LIST

    /**
     * Accesses the 24 hour objects from 0-23 and checks if they have any contents,
     * If they do, it will generate a row in the content table for the Day List
     */


    private void generateDayListContents(){
        TableLayout contentTable = findViewById(R.id.contentTable);
        contentTable.removeAllViews(); //acts to refresh the table

        for(int i = 0; i<24; i++){
            if(hourScreenHashMap.get(i).getNotesWidget().getWidgetTitle()!=null || hourScreenHashMap.get(i).getAlarmWidget().getAlarmSet() || hourScreenHashMap.get(i).getToDo().getShowToDo()){
                getHourRow(contentTable, i);}
            //goes through all hours and checks if any content exists in the relevant instances, if so then generates the content

        }
    }


    /**
     * helper method for getHourRow method
     * generates a placeholder text to take space in the row columns
     */
    private TextView placeHolderTextGen(TableRow.LayoutParams params){

        TextView placeHolderTextView = new TextView(this); //generating the placeholder textview
        placeHolderTextView.setGravity(Gravity.CENTER);
        placeHolderTextView.setPadding(8,8,8,8);


        placeHolderTextView.setLayoutParams(params); //simple weight parameters from the gethourRow method
        placeHolderTextView.setBackground(Drawable.createFromPath("#00E0E0E0"));
        placeHolderTextView.setTextSize(12); placeHolderTextView.setText("");

        return placeHolderTextView;
    }

    /**
     * Creates a table row with specific layout parameters
     * Generates text for the, and if they exist, notes/todo/alarm titles respecitvely
     */

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


        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.3F //sets the weight to be relatively low, so it takes up less space on the row
        );

        TextView timeText = placeHolderTextGen(params);
        Typeface alata = ResourcesCompat.getFont(this, R.font.alata); //setting the font
        timeText.setTypeface(alata);
        timeText.setBackground(Drawable.createFromPath("#00E0E0E0"));
        timeText.setTextColor(Color.parseColor("#494646"));
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
        notesText.setTextColor(Color.parseColor("#494646"));
        if(currentHour.getNotesWidget().getWidgetTitle()!=null) {
            notesText.setText("Notes: " + currentHour.getNotesWidget().getNotes());
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


        String toDoTitle = (currentHour.getToDo().getToDoContents().peek()); //if the todo content exists
        if (toDoTitle!=null){
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
            toDoText.setTextColor(Color.parseColor("#494646"));

            secondTableRow.addView(placeHolderTextView); //adding the placeholder textview
            toDoText.setBackground((getDrawable(R.drawable.tablecolumnborders)));
            secondTableRow.setPadding(0,0,0,16);
            secondTableRow.addView(toDoText); //adding the todo textview

            contentTable.addView(secondTableRow);}



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
            alarmText.setTextColor(Color.parseColor("#494646"));


            thirdTableRow.addView(placeHolderTextViewTwo);
            alarmText.setBackground((getDrawable(R.drawable.tablecolumnborders)));
            thirdTableRow.setPadding(0,0,0,16);
            thirdTableRow.addView(alarmText);
            contentTable.addView(thirdTableRow);

        }
    }
    //DAY LIST METHOD ENDS HERe
    //LARGER/BROAD METHODS END HERE


    //METHODS ACCESSED BY BUTTONS/OTHER BUTTON METHODS

    //ALARM METHODS
    /**
     * Calculate Time
     * This calculates the current time in milliseconds by using the Calender class
     *
     * @return the calculated time in a long
     */

    private long calculateTime(){
        Calendar calendar = Calendar.getInstance();
        return System.currentTimeMillis() + 10000;
//        calendar.setTimeInMillis(System.currentTimeMillis()); //sets calendar class and acquires time
//
//        // Set the calendar to the specified time (e.g., 8:00 AM)
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        // Check if the time is in the past and add one day if it is
//        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//        }
//
//       return calendar.getTimeInMillis(); //returns the time in millis
    }

    /**
     * Save Alarm
     * This method saves the alarm title into the current hour's object and alarm object
     */

    private void saveAlarm(){
        HourScreen currentHour = Objects.requireNonNull(hourScreenHashMap.get(hour));
        EditText alarmTitle = findViewById(R.id.alarmTitleText);
        currentHour.getAlarmWidget().setWidgetTitle(alarmTitle.getText().toString()); //access text and upload to class
        //method because I need this save function elsewhere
    }

    /**
     * Set Alarm
     * This method is called to set the alarm, using the current hour/circularseek value
     */
    private void setAlarm(){
        HourScreen currentHour = Objects.requireNonNull(hourScreenHashMap.get(hour));
        currentHour.getAlarmWidget().setAlarmSet(!(currentHour.getAlarmWidget().getAlarmSet())); //flips the boolean for alarm button
        saveAll(hour); //saves all so as to not refresh and delete other widgets that might not be saved
        //access relevant objects: page, button, alarm, etc.

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        Intent intent = new Intent(this, alarmReceiver.getClass()); //specific classes for setting up timed broadcasts
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        TextView alarmStatus = findViewById(R.id.alarmStatusText); //accesses the status text

        if(currentHour.getAlarmWidget().getAlarmSet()){ //if the alarm is toggled to on

            alarmStatus.setText("Your alarm is set");

            long triggerTime = calculateTime(); //calculates time function
            Log.d("alarm", String.valueOf(triggerTime));

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
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

    //ALARM METHODS END HERE

    //TODO METHODS START


    /**
     * This method generates the to-do contents.
     * The entire to-do generating system will be explained in this method for ease of understanding
     *
     * The to-Do generation is based on the String arrayList, more elements in the arrayList = more todo items
     * Upon generation, each editText is generated based on each arrayList item
     * Adding a to-Do element means adding an element to the arraylist and running the method that refreshes the screen
     * The editText objects are stored in an arrayList, which are stored in the class.
     * After editing a to-Do item, the save function is executed and iterates through the editText arrayList
     * It accesses the text inside the editText, and sets the String arrayList to that text, thus saving
     * @param currentHour the current object related to the selected hour
     */

    private void generateToDoContents(HourScreen currentHour){
        LinearLayout toDoBoxLinearLayout = findViewById(R.id.toDoBoxLinearLayout);
        toDoBoxLinearLayout.removeAllViews(); //refreshes the todo box
        ArrayDeque<EditText> generatedEditTexts = new ArrayDeque<>(); //creates an deque to store the generated edit texts
        ArrayDeque<String> existingTexts = currentHour.getToDo().getToDoContents().clone(); //clones existing texts to not affect the actual stored data when using removefirst
        int dequeLength = existingTexts.size();

        for(int i = 0; i< dequeLength; i++){
            String text = existingTexts.removeFirst();
            ConstraintLayout toDoElement = getBox(currentHour, text,generatedEditTexts);
            toDoBoxLinearLayout.addView(toDoElement);
        }
        currentHour.getToDo().setEditTexts(generatedEditTexts); //saves the array of edittexts in the currenthour
        //this array is accessed when saving the edittext contents
    }

    /**
     * Iterates through each  edit text in the arrayList,
     * extracting their text Strings
     * and updates the String Arraylist, uploading to class
     *
     */
    private void saveToDoContents(){
        ToDoWidget toDoList = Objects.requireNonNull(hourScreenHashMap.get(hour)).getToDo(); //accesses toDo Class

        ArrayDeque<String> contentsToSave = new ArrayDeque<>(); //arraydeque to hold contents to save
        ArrayDeque<EditText> copyExistingEditTexts = toDoList.getEditTexts();
        int currentSize = copyExistingEditTexts.size();

        for(int i = 0; i<currentSize; i++){ //accesses the edit text arraylists in the class
            EditText editText = copyExistingEditTexts.removeFirst(); //accesses the edittexts and uploads their text to the string arraylist
            contentsToSave.addLast(String.valueOf(editText.getText()));
        }
        toDoList.setToDoContents(contentsToSave); //uploads the array to be stored in the class
        Log.d("saveToDoContents",contentsToSave.toString());
        Log.d("saveToDoContents actual saved", toDoList.getToDoContents().toString());
    }

    /** addToDoContents
     * Adds an empty string to the ToDo Objects String ArrayList, which is one more element to be generated,
     * and refreshes the screen to reflect changes
     */
    private void addToDoContents(){
        saveToDoContents(); //saves, otherwise the todo texts before the recently added one will disappear after refreshing screen
        String text = "";
        HourScreen currentHour = Objects.requireNonNull(hourScreenHashMap.get(hour));
        currentHour.getToDo().addToDoContents(text);
        //adds an empty string to the todo arrays, which will make the generation method create an empty edit text
        displayEntriesForHour(hour); //refreshes screen

    }


    /**
     * Generation method to generate a new to do item
     * @param currentHour the current hour object
     * @param text the text for the to do
     * @param generatedEditTexts the editText ArrayList
     */
    private ConstraintLayout getBox(HourScreen currentHour, String text, ArrayDeque<EditText> generatedEditTexts){

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

        //adding the edit text contents & setting placeholder hints
        editText.setText(text);
        editText.setHint("Drink some water"); //will always set a hint if there is no text
        editText.setTextColor(Color.parseColor("#494646"));
        //allyhao
        generatedEditTexts.addLast(editText); //adds the object to the array of edit texts to be stored in the array

        return constraintLayout;


    }

    /**
     * Sets the to do button clicked to true and refreshes the page
     *
     */

    private void showToDoInformation(int hour){
        try{
        HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
        currentHour.getToDo().setShowToDo(true); //records that the button was clicked
        displayEntriesForHour(hour); //updates page wth that information

            Log.d("MainActivity showToDoInformation","succeeded showing To Do Information");
        } catch(Exception e){
            Log.e("MainActivity showToDoInformation","failed to show To Do Information");
        }


    }

    //TODO METHODS ENDS HERE
    //NOTES METHODS START HERE

    /**
     * sets placeholder text for the notes, thus telling the refresh method to make the notes widget visible
     */
    private void showNotesInformation(int hour){
        try {
        HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
        currentHour.getNotesWidget().setShowNotes(true);
        displayEntriesForHour(hour); //updates the page
        Log.d("MainActivity showNotesInformation","succeeded showing To Notes Information");
    } catch(Exception e){
        Log.e("MainActivity showNotesInformation","failed to show Notes Information");
    }

    }

    /**
     * Saves the text inside the notes EditTexts into the HourScreen object for that hour
     */

    private void saveNotesInformation(int hour){
        try {
            TextView notesTitle= findViewById(R.id.notesTitle);
            TextView notesBodyText = findViewById(R.id.notesBodyText);
            HourScreen currentHour = hourScreenHashMap.get(hour); //gets the instance for that hour
            currentHour.getNotesWidget().setWidgetTitle(notesTitle.getText().toString());
            currentHour.getNotesWidget().setNotes(notesBodyText.getText().toString());//saves the notes in the class object
            Log.d("MainActivity savedNotesInformation","succeeded Save Notes");
        } catch(Exception e){
            Log.e("MainActivity savedNotesInformation","failed to Save Notes");
        }

    }

    //NOTES METHODS END HERE

}