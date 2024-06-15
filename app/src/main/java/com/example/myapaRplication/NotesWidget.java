package com.example.myapaRplication;

/**
 * The type Notes widget.
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
