package com.example.myapaRplication;

/**
 * Class Title: NotesWidget
 * Description: This class holds notes information
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class NotesWidget extends HourWidgets {

    private String notesBody;

    /**
     * Set notes.
     *
     * @param input the input string
     */
    public void setNotes(String input){
        this.notesBody = input;
    }

    /**
     * Get the body of the notes
     *
     * @return the string
     */
    public String getNotes(){
        return notesBody;
    }


}
