package bulhakov.nure.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import bulhakov.nure.R;
import bulhakov.nure.Util.Pair;
import bulhakov.nure.model.Note;

public class NoteListAdapter extends ArrayAdapter<Note> {

    private final Context context;

    private final int listId;

    private final List<Note> notes;

    private final Observer observer;

    public NoteListAdapter(Context context, int listId, List<Note> notes, Observer observer) {
        super(context, R.layout.note);
        this.context = context;
        this.notes = notes;
        this.listId = listId;
        this.observer = observer;
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

        if (convertView == null) {
            convertView = inflater.inflate(this.listId, parent, false);
            viewHolder = new NoteViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);
        viewHolder.init(note);
        viewHolder.addObserver(observer);

        return convertView;
    }

    private class NoteViewHolder extends Observable {

        String noteId;

        ImageView noteImage;

        TextView noteTitle;

        TextView notePriority;

        TextView noteText;

        TextView noteDatetime;

        ImageButton editButton;

        ImageButton deleteButton;

        private NoteViewHolder(View view) {
            noteImage = view.findViewById(R.id.noteImage);
            noteTitle = view.findViewById(R.id.noteTitle);
            notePriority = view.findViewById(R.id.notePriority);
            noteText = view.findViewById(R.id.noteText);
            noteDatetime = view.findViewById(R.id.noteDatetime);
            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }

        private void init(Note note) {
            //TODO set image
            noteId = note.getId();
            noteTitle.setText(note.getName());
            noteText.setText(note.getText());
            setDatetime(noteDatetime, note.getCreationDate());
            setPriority(notePriority, note);

            editButton.setTag(note.getId());
            deleteButton.setTag(note.getId());
            initEditButton();
            initDeleteButton();
        }

        private void setDatetime(TextView view, Date date) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
            String dateAsString = format.format(date);
            view.setText(dateAsString);
        }

        private void setPriority(TextView view, Note note) {
            switch (note.getPriority()) {
                case LOW:
                    view.setText("★☆☆");
                    break;
                case MEDIUM:
                    view.setText("★★☆");
                    break;
                default:
                    view.setText("★★★");
                    break;
            }
        }

        private void initEditButton() {
            editButton.setOnLongClickListener(event -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("edit");
                builder.setPositiveButton(R.string.yes, (dialog, i) -> {
                    setChanged();
                    notifyObservers(new Pair<>("update", noteId));
                });
                builder.setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
                return true;
            });
        }

        private void initDeleteButton() {
            deleteButton.setOnLongClickListener(event -> {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.sure_delete)
                        .setPositiveButton(R.string.yes, (dialog, i) -> {
                            setChanged();
                            notifyObservers(new Pair<>("delete", noteId));
                        })
                        .setNegativeButton(R.string.no, ((dialog, which) -> dialog.dismiss()))
                        .show();
                return true;
            });
        }
    }
}
