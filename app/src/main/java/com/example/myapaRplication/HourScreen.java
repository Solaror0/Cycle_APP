package com.example.myapaRplication;

/**
 * Class Title: HourScreen
 * Description:
 * This class has the widget classes and will have 24 instances, one for each hour
 * It connects to each widget class and is used to access their contents, e.g strings, booleans, etc.
 * and the classes themselves
 *
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class HourScreen {
    private NotesWidget notesWidget;
    private ToDoWidget toDoWidget;
    private AlarmWidget alarmWidget;

    /**
     * Instantiates a new Hour screen.
     *
     * @param notesWidget the notes widget
     * @param toDoWidget  the to do widget
     * @param alarmWidget the alarm widget
     */
    public HourScreen(NotesWidget notesWidget, ToDoWidget toDoWidget,AlarmWidget alarmWidget){
        this.notesWidget = notesWidget;
        this.toDoWidget = toDoWidget;
        this.alarmWidget = alarmWidget;
    }

    private Boolean notesButtonClicked = false;

    private Boolean toDoButtonClicked = false;





    /**
     * Get to do widget
     *
     * @return the to do widget
     */
    public ToDoWidget getToDo(){
        return toDoWidget;
    }

    /**
     * Get alarm widget
     *
     * @return the alarm widget
     */
    public AlarmWidget getAlarmWidget(){return alarmWidget;}

    /**
     * Get notes widget.
     *
     * @return the notes widget
     *
     */
    public NotesWidget getNotesWidget(){
        return notesWidget;
    }

    /**
     * Set notes.
     *
     * @param title the title
     * @param input the input
     */


}
