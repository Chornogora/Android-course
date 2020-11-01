package bulhakov.nure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import bulhakov.nure.Context;
import bulhakov.nure.R;
import bulhakov.nure.Util.Pair;
import bulhakov.nure.adapter.NoteListAdapter;
import bulhakov.nure.model.Note;
import bulhakov.nure.service.NoteService;

public class MainActivity extends AppCompatActivity implements Observer {

    private ListView listView;

    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = Context.getContext().getNoteService();
        //addNote();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> notes = noteService.getAll();
        listView = findViewById(R.id.note_list);
        NoteListAdapter adapter = new NoteListAdapter(this, R.layout.note, notes, this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void addNote(MenuItem item) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        Pair<String, ?> pair = (Pair) arg;
        switch (pair.getKey()) {
            case "delete":
                String noteId = (String) pair.getValue();
                noteService.removeNote(noteId);
                break;
            case "update":
                Note note = (Note) pair.getValue();
                break;
        }
        recreate();
    }

    void addNote() {
        Note note = new Note();
        note.setId("1232132");
        note.setText("text");
        note.setName("example");
        note.setCreationDate(new Date());
        note.setPriority(Note.Priority.MEDIUM);
        note.setImagePath("...");

        noteService.addNote(note);
    }
}