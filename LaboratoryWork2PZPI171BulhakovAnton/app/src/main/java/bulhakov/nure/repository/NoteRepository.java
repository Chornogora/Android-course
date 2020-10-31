package bulhakov.nure.repository;

import java.util.List;

import bulhakov.nure.model.Note;

public interface NoteRepository {

    List<Note> getAll();

    Note addNote(Note note);

    Note removeNote(String id);

    Note editNote(Note note);
}
