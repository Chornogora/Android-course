package bulhakov.nure.service.impl;

import java.util.List;
import java.util.UUID;

import bulhakov.nure.model.Note;
import bulhakov.nure.repository.NoteRepository;
import bulhakov.nure.service.NoteService;

public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.getAll();
    }

    @Override
    public Note addNote(Note note) {
        if(note.getId() == null){
            String id = UUID.randomUUID().toString();
            note.setId(id);
        }
        return noteRepository.addNote(note);
    }

    @Override
    public Note removeNote(String id) {
        return noteRepository.removeNote(id);
    }

    @Override
    public Note editNote(Note note) {
        return noteRepository.editNote(note);
    }
}
