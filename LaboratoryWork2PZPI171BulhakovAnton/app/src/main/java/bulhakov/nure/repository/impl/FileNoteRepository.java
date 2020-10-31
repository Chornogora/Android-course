package bulhakov.nure.repository.impl;

import android.content.Context;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bulhakov.nure.exception.FileCreationException;
import bulhakov.nure.exception.SerializationException;
import bulhakov.nure.model.Note;
import bulhakov.nure.repository.NoteRepository;

public class FileNoteRepository implements NoteRepository {

    private static final String FILENAME = "notes.txt";

    private final File notesFile;

    private Map<String, Note> notes;

    public FileNoteRepository(Context context) {
        File rootDirectory = context.getDataDir();
        notesFile = new File(rootDirectory, FILENAME);
        createFile(notesFile);
        readAllNotes();
    }

    @Override
    public List<Note> getAll() {
        return new ArrayList<>(notes.values());
    }

    @Override
    public Note addNote(Note note) {
        notes.put(note.getId(), note);
        serializeAll();
        return note;
    }

    @Override
    public Note removeNote(String id) {
        Note note = notes.remove(id);
        serializeAll();
        return note;
    }

    @Override
    public Note editNote(Note note) {
        Note old = notes.get(note.getId());
        notes.put(note.getId(), note);
        serializeAll();
        return old;
    }

    private void createFile(File file) {
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new FileCreationException("File was not created: " + file.getPath());
                }
            }
        } catch (IOException e) {
            throw new FileCreationException("Cannot create file due to exception", e);
        }
    }

    private void readAllNotes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(notesFile))) {
            notes = (Map<String, Note>) ois.readObject();
        } catch (EOFException e) {
            notes = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }

    private void serializeAll() {
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(notesFile))) {
            ois.writeObject(notes);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
