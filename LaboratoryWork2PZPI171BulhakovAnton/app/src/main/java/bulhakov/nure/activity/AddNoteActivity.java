package bulhakov.nure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import java.util.Date;

import bulhakov.nure.Context;
import bulhakov.nure.R;
import bulhakov.nure.model.Note;
import bulhakov.nure.service.NoteService;

public class AddNoteActivity extends Activity {

    private NoteService noteService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);

        noteService = Context.getContext().getNoteService();
    }

    public void create(View view) {
        String title = findViewById(R.id.noteTitle).toString();

        RadioGroup priority = findViewById(R.id.notePriority);
        int checked = priority.getCheckedRadioButtonId();
        String priorityAsString = (String)findViewById(checked).getTag();
        Note.Priority notePriority = Note.Priority.valueOf(priorityAsString);

        String text = findViewById(R.id.noteText).toString();
        Date noteDate = new Date(System.currentTimeMillis());

        Note note = createNote(title, notePriority, text, noteDate);
        noteService.addNote(note);
    }

    private Note createNote(String title, Note.Priority notePriority, String text, Date noteDate) {
        Note note = new Note();
        note.setName(title);
        note.setText(text);
        note.setPriority(notePriority);
        note.setCreationDate(noteDate);
        return note;
    }

    public void cancel(View view) {
        finish();
    }

    public void choosePhoto(View view) {
        //TODO choose photos
    }
}
