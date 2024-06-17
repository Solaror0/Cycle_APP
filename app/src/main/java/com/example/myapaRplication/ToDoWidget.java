package com.example.myapaRplication;

import android.widget.EditText;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Class Title: toDoWidget
 * Description: This class holds toDo information, e.g the contents and editText objects 
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class ToDoWidget extends HourWidgets{

    private ArrayDeque<String> toDoContents;
    private ArrayDeque<EditText> editTexts;



    private Boolean showToDo = false;


    /**
     * Instantiates a new To do widget.
     */
    public ToDoWidget(){
        toDoContents = new ArrayDeque<String>();
        editTexts = new ArrayDeque<EditText>();
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
    public ArrayDeque<String> getToDoContents() {
        return toDoContents;
    }

    /**
     * Sets to do contents.
     *
     * @param toDoContents the to do contents
     */
    public void setToDoContents(ArrayDeque<String> toDoContents) {
        this.toDoContents = toDoContents;

    }

    /**
     * Add to do contents.
     *
     * @param toDoContent the to do content
     */
    public void addToDoContents(String toDoContent) {
        this.toDoContents.addLast(toDoContent);

    }

    /**
     * Gets edit texts.
     *
     * @return the edit texts
     */
    public ArrayDeque<EditText> getEditTexts() {
        return editTexts;
    }

    /**
     * Sets edit texts.
     *
     * @param editTexts the edit texts
     */
    public void setEditTexts(ArrayDeque<EditText> editTexts) {
        this.editTexts = editTexts;
    }


}
