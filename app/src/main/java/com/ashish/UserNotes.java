package com.ashish;

import java.io.Serializable;

public class UserNotes implements Serializable {
    String noteTitle="",noteDes="",noteDate="",noteId="";

    public UserNotes(String noteTitle, String noteDes, String noteDate, String noteId) {
        this.noteTitle = noteTitle;
        this.noteDes = noteDes;
        this.noteDate = noteDate;
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDes() {
        return noteDes;
    }

    public void setNoteDes(String noteDes) {
        this.noteDes = noteDes;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
