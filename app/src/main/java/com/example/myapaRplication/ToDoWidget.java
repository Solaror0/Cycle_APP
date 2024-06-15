package com.example.myapaRplication;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Class Title: toDoWidget
 * Description: This class holds toDo information, e.g the contents and editText objects 
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class ToDoWidget extends HourWidgets{

    private ArrayList<String> toDoContents;
    private ArrayList<EditText> editTexts;



    private Boolean showToDo = false;


    /**
     * Instantiates a new To do widget.
     */
    public ToDoWidget(){
        toDoContents = new ArrayList<String>();
        editTexts = new ArrayList<EditText>();
    }

    /**
     * Gets to do button clicked state
     *
     * @return the to do button clicked state
     */
    public Boolean getShowToDo() {
        return showToDo;
    }
    /**
     * Sets to do button clicked.
     *
     * @param showToDo the state of the todobutton
     */
    public void setShowToDo(Boolean showToDo) {
        this.showToDo = showToDo;
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
