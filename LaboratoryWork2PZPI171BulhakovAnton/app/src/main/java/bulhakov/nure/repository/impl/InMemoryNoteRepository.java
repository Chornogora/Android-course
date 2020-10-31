package bulhakov.nure.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bulhakov.nure.model.Note;
import bulhakov.nure.repository.NoteRepository;

public class InMemoryNoteRepository implements NoteRepository {

    List<Note> notes = new ArrayList<>();

    @Override
    public List<Note> getAll() {
        return new ArrayList<>(notes);
    }

    @Override
    public Note addNote(Note note) {
        String id = UUID.randomUUID().toString();
        note.setId(id);
        notes.add(note);
        return note;
    }

    @Override
    public Note removeNote(String id) {
        Note noteFound = notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Note with such id was not found"));
        notes.remove(noteFound);
        return noteFound;
    }

    @Override
    public Note editNote(Note note) {
        Note noteFound = notes.stream()
                .filter(item -> note.getId().equals(item.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Note with such id was not found"));
        notes.remove(noteFound);
        return noteFound;
    }
}
