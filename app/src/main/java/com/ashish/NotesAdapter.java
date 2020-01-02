package com.ashish;

import android.app.Dialog;
import android.content.Context;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {
    Context context;
    ArrayList<UserNotes> dataList = new ArrayList<>();
    String noteId="",noteDate="";
    UpdateInterface updateInterface;
    public NotesAdapter(Context con, ArrayList<UserNotes> list) {
        context = con;
        dataList = list;
        updateInterface=(UpdateInterface)context;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recycler_all_notes, parent, false);
        NotesHolder notesHolder = new NotesHolder(view);
        return notesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        UserNotes userNotes=dataList.get(position);
        String title=userNotes.getNoteTitle();
        String desc=userNotes.getNoteDes();
        String date=userNotes.getNoteDate();

        holder.textRowTitle.setText(title);
        holder.textRowDescription.setText(desc);
        holder.textRowDate.setText(date);

        holder.imageRowEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserNotes userNotes1=dataList.get(position);


                showDialog(userNotes1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_row_title)
        TextView textRowTitle;
        @BindView(R.id.text_row_Description)
        TextView textRowDescription;
        @BindView(R.id.textView6)
        TextView textRowDate;
        @BindView(R.id.image_row_edit)
        ImageView imageRowEdit;
        @BindView(R.id.image_row_delete)
        ImageView imageRowDelete;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void showDialog(UserNotes notesObj){


        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_note);
        dialog.show();
        EditText editTitle=(EditText)dialog.findViewById(R.id.editText_update_title);
        EditText editDesc=(EditText)dialog.findViewById(R.id.editText_update_description);

        editTitle.setText(notesObj.getNoteTitle());
        editDesc.setText(notesObj.getNoteDes());
        noteId=notesObj.getNoteId();



        Button buttonUpdate=(Button)dialog.findViewById(R.id.button_update_note);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar= Calendar.getInstance();
                noteDate=simpleDateFormat.format(calendar.getTime());

                String title=editTitle.getText().toString();
                String desc=editDesc.getText().toString();

                UserNotes userNotes=new UserNotes(title,desc,noteDate,noteId);
                updateInterface.updateUserNote(userNotes);
            }
        });
    }
}
