package bulhakov.nure;

import android.app.Application;

import bulhakov.nure.repository.NoteRepository;
import bulhakov.nure.repository.impl.FileNoteRepository;
import bulhakov.nure.service.NoteService;
import bulhakov.nure.service.impl.NoteServiceImpl;

public class Context extends Application {

    private static Context context;

    private NoteService noteService;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        context = this;
    }

    private void init(){
        NoteRepository noteRepository = new FileNoteRepository(getApplicationContext());
        noteService = new NoteServiceImpl(noteRepository);
    }

    public NoteService getNoteService() {
        return noteService;
    }
}
