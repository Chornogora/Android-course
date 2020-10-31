package bulhakov.nure.activity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import bulhakov.nure.Context;
import bulhakov.nure.R;
import bulhakov.nure.adapter.NoteListAdapter;
import bulhakov.nure.model.Note;
import bulhakov.nure.service.NoteService;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = Context.getContext().getNoteService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> notes = noteService.getAll();
        listView = findViewById(R.id.note_list);
        ListAdapter adapter = new NoteListAdapter(this, R.id.note_list, notes);
        listView.setAdapter(adapter);
    }
}