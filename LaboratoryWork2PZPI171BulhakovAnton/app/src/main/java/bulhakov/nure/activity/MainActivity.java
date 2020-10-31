package bulhakov.nure.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import bulhakov.nure.Context;
import bulhakov.nure.R;
import bulhakov.nure.model.Note;
import bulhakov.nure.service.NoteService;

public class MainActivity extends AppCompatActivity {

    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = Context.getContext().getNoteService();
        noteService.addNote(new Note());
    }
}