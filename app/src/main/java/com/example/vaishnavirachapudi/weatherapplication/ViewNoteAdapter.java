package com.example.vaishnavirachapudi.weatherapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ViewNoteAdapter extends ArrayAdapter{

    Context context;
    ArrayList<Notes> notes;
    TextView noteContent , date;
    public ViewNoteAdapter(Context context, ArrayList<Notes> notes) {
        super(context, R.layout.note_line_layout, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.note_line_layout , parent,false);
        }

        Notes note = notes.get(position);
        noteContent = (TextView) convertView.findViewById(R.id.textView_noteContent);
        date = (TextView) convertView.findViewById(R.id.textView_date);

        String content = note.getNote();
        String time = note.getDate();
        if(content != null){
            noteContent.setText(content);
        }
        if(time != null){
            date.setText(time);
        }

        return convertView;
    }
}
