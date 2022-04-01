package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer) {


    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()



    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else notes.joinToString (separator = "\n") { note ->
            notes.indexOf(note).toString() + ": " + note.toString() }


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

    fun listActiveNotes(): String =
        if(numberOfArchivedNotes()==0) "No archived notes stored"
        else notes.filter {note -> !note.isNoteArchived}.joinToString(separator ="\n")
        {note -> notes.indexOf(note).toString()+": "+ note.toString()}

       /* return if (numberOfActiveNotes() == 0) {
            "No active notes stored"
        } else {
            var listOfActiveNotes = ""
            for (note in notes) {
                if (!note.isNoteArchived) {
                    listOfActiveNotes += "${notes.indexOf(note)}: $note \n"
                }
            }
            listOfActiveNotes
        }*/



    fun listArchivedNotes(): String =
        /*return if (numberOfArchivedNotes() == 0) {
            "No archived notes stored"
        } else {
            var listOfArchivedNotes = ""
            for (note in notes) {
                if (note.isNoteArchived) {
                    listOfArchivedNotes += "${notes.indexOf(note)}: $note \n"
                }
            }
            listOfArchivedNotes
        }*/
        if(numberOfArchivedNotes()==0) "No archived notes stored"
        else notes.filter {note -> note.isNoteArchived}.joinToString(separator ="\n")
        {note -> notes.indexOf(note).toString()+": "+ note.toString()}


    fun numberOfArchivedNotes(): Int {
        return notes.stream()
            .filter{note: Note -> note.isNoteArchived}
            .count()
            .toInt()
    }

    fun numberOfActiveNotes(): Int {
        return notes.stream()
            .filter{note: Note -> !note.isNoteArchived}
            .count()
            .toInt()
    }

    fun numberOfNotesByPriority(priority: Int): Int {

        return notes.stream()
            .filter{note: Note -> note.notePriority == priority}
            .count()
            .toInt()


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
    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }
    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {

        val foundNote = findNote(indexToUpdate)


        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }


        return false
    }
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes);
    }

    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }
    fun archiveNote(index: Int): Boolean {
        if (isValidIndex(index)) {
            val noteToArchive = notes[index]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }



}