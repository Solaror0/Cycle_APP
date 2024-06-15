package com.example.myapaRplication;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * The type To do widget.
 */
public class ToDoWidget extends HourWidgets{

    private ArrayList<String> toDoContents;
    private ArrayList<EditText> editTexts;

    /**
     * Instantiates a new To do widget.
     */
    public ToDoWidget(){
        toDoContents = new ArrayList<String>();
        editTexts = new ArrayList<EditText>();
    }


    /**
     * Gets to do contents.
     *
     * @return the to do contents
     */
    public ArrayList<String> getToDoContents() {
        return toDoContents;
    }

    /**
     * Sets to do contents.
     *
     * @param toDoContents the to do contents
     */
    public void setToDoContents(ArrayList<String> toDoContents) {
        this.toDoContents = toDoContents;

    }

    /**
     * Add to do contents.
     *
     * @param toDoContent the to do content
     */
    public void addToDoContents(String toDoContent) {
        this.toDoContents.add(toDoContent);

    }

    /**
     * Gets edit texts.
     *
     * @return the edit texts
     */
    public ArrayList<EditText> getEditTexts() {
        return editTexts;
    }

    /**
     * Sets edit texts.
     *
     * @param editTexts the edit texts
     */
    public void setEditTexts(ArrayList<EditText> editTexts) {
        this.editTexts = editTexts;
    }


}
