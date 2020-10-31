package bulhakov.nure.repository.impl;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        return null;
    }

    @Override
    public Note addNote(Note note) {
        return null;
    }

    @Override
    public Note removeNote(String id) {
        return null;
    }

    @Override
    public Note editNote(Note note) {
        return null;
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
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }
}
