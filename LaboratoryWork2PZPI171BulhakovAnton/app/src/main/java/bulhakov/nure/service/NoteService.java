package bulhakov.nure.service;

import java.util.List;

import bulhakov.nure.model.Note;

public interface NoteService {

    List<Note> getAll();

    Note addNote(Note note);

    Note removeNote(String id);

    Note editNote(Note note);
}
