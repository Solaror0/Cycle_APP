package com.example.myapaRplication;

/**
 * The type Hour screen.
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
    private Boolean setAlarmButtonClicked = false;
    private Boolean showAlarmButtonClicked = false;

    /**
     * Gets show alarm button's clicked state
     *
     * @return the show alarm button clicked
     */
    public Boolean getShowAlarmButtonClicked() {
        return showAlarmButtonClicked;
    }

    /**
     * Sets show alarm button's clicked state
     *
     * @param showAlarmButtonClicked the show alarm button clicked
     */
    public void setShowAlarmButtonClicked(Boolean showAlarmButtonClicked) {
        this.showAlarmButtonClicked = showAlarmButtonClicked;
    }


    /**
     * Gets notes button's clicked state
     *
     * @return the notes button's clicked state
     */
    public Boolean getNotesButtonClicked() {
        return notesButtonClicked;
    }

    /**
     * Sets notes button's clicked state
     *
     * @param notesButtonClicked the notes button clicked state
     */
    public void setNotesButtonClicked(Boolean notesButtonClicked) {
        this.notesButtonClicked = notesButtonClicked;
    }

    /**
     * Gets alarm button's clicked state
     *
     * @return the alarm button clicked state
     */
    public Boolean getAlarmButtonClicked() {
        return setAlarmButtonClicked;
    }

    /**
     * Sets alarm button's clicked state
     *
     * @param alarmButtonClicked the alarm button clicked state
     */
    public void setAlarmButtonClicked(Boolean alarmButtonClicked) {
        this.setAlarmButtonClicked = alarmButtonClicked;
    }


    /**
     * Gets to do button clicked state
     *
     * @return the to do button clicked state
     */
    public Boolean getToDoButtonClicked() {
        return toDoButtonClicked;
    }

    /**
     * Sets to do button clicked.
     *
     * @param toDoButtonClicked the state of the todobutton
     */
    public void setToDoButtonClicked(Boolean toDoButtonClicked) {
        this.toDoButtonClicked = toDoButtonClicked;
    }

    /**
     * Get to do to do widget.
     *
     * @return the to do widget
     */
    public ToDoWidget getToDo(){
        return toDoWidget;
    }

    /**
     * Get alarm widget alarm widget.
     *
     * @return the alarm widget
     */
    public AlarmWidget getAlarmWidget(){return alarmWidget;}

    /**
     * Get notes body string.
     *
     * @return the string
     */
    public String getNotesBody(){
        return notesWidget.getNotes();
    }

    /**
     * Get notes title string.
     *
     * @return the string
     */
    public String getNotesTitle(){
        return notesWidget.getWidgetTitle();
    }

    /**
     * Set notes.
     *
     * @param title the title
     * @param input the input
     */
    public void setNotes(String title, String input){
        notesWidget.setWidgetTitle(title);
        notesWidget.setNotes(input);
    }


}
