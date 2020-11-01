package bulhakov.nure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bulhakov.nure.R;
import bulhakov.nure.model.Note;

public class NoteListAdapter extends ArrayAdapter<Note> {

    private final Context context;

    private final int listId;

    private final List<Note> notes;

    public NoteListAdapter(Context context, int listId, List<Note> notes){
        super(context, R.layout.note);
        this.context = context;
        this.notes = notes;
        this.listId = listId;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NoteViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.listId, parent, false);
            viewHolder = new NoteViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (NoteViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);

        //TODO set image
        viewHolder.noteTitle.setText(note.getName());
        viewHolder.noteText.setText(note.getText());
        setDatetime(viewHolder.noteDatetime, note.getCreationDate());
        setPriority(viewHolder.notePriority, note);

        return convertView;
    }

    private static class NoteViewHolder{

        ImageView noteImage;

        TextView noteTitle;

        TextView notePriority;

        TextView noteText;

        TextView noteDatetime;

        public NoteViewHolder(View view) {
            noteImage = view.findViewById(R.id.noteImage);
            noteTitle = view.findViewById(R.id.noteTitle);
            notePriority = view.findViewById(R.id.notePriority);
            noteText = view.findViewById(R.id.noteText);
            noteDatetime = view.findViewById(R.id.noteDatetime);
        }
    }

    private void setDatetime(TextView view, Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
        String dateAsString = format.format(date);
        view.setText(dateAsString);
    }

    private void setPriority(TextView view, Note note){
        switch (note.getPriority()){
            case LOW: view.setText("★☆☆"); break;
            case MEDIUM: view.setText("★★☆"); break;
            default: view.setText("★★★"); break;
        }
    }
}
