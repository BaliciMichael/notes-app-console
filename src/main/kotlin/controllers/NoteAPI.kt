package controllers

import models.Note

class NoteAPI {
    private var notes = ArrayList<Note>()


    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String {
        if(numberOfActiveNotes()==0){
            return "No active notes stored"
        }
        else{
            return "There are notes stored"
        }

    }

    fun listArchivedNotes(): String {
        if(numberOfArchivedNotes()==0){
            return "No archived notes stored"

        }
        else{
            return "archived notes are store"
        }
    }

    fun numberOfArchivedNotes(): Int {
        var counter = 0;
        //helper method to determine how many archived notes there are
        for(note in notes){
            if (note.isNoteArchived){
                counter++
            }
        }
        return counter
    }

    fun numberOfActiveNotes(): Int {
        var counter = 0;
        //helper method to determine how many archived notes there are
        for(note in notes){
            if (!note.isNoteArchived){
                counter++
            }
        }
        return counter
    }
    fun numberOfNotesByPriority(priority: Int): Int {

        var counter = 0
        for (note in notes) {
            if (note.notePriority == priority) {
                counter++
            }
        }
        return counter
    }
    fun listNotesBySelectedPriority(priority: Int): String {
        return if (notes.isEmpty()) {
            "there are no notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                if (notes[i].notePriority == priority) {
                    listOfNotes +=
                        """$i: ${notes[i]}
                        """.trimIndent()
                }
            }
            if (listOfNotes.equals("")) {
                "There are no notes with priority: $priority"
            } else {
                "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
            }
        }
    }

}