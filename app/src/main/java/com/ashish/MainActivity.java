package com.ashish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements  UpdateInterface {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.text_home_no_data)
    TextView textHomeNoData;
    @BindView(R.id.btn_add_note)
    FloatingActionButton btnAddNote;
    @BindView(R.id.recycler_all_notes)
    RecyclerView recyclerAllNotes;
    ArrayList<UserNotes> allNotesList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    DatabaseReference databaseNotes;

    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Reading Notes...");
        sharedPreferences=getSharedPreferences("FIRENOTEDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String uid=sharedPreferences.getString("UID","");
        databaseNotes= FirebaseDatabase.getInstance().getReference("USERNOTE").child(uid);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerAllNotes.setLayoutManager(linearLayoutManager);
        toolBar.setTitle("FireNotes");
        toolBar.setTitleTextColor(Color.WHITE);




    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllNotes();
    }

    @OnClick(R.id.btn_add_note)
    public void onViewClicked() {
        Intent intent=new Intent(MainActivity.this,Add_notes.class);
        startActivity(intent);
        finish();
    }
    public void readAllNotes(){


        allNotesList.clear();

        progressDialog.show();

        databaseNotes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UserNotes userNotes=snapshot.getValue(UserNotes.class);
                    allNotesList.add(userNotes);


                }
                progressDialog.dismiss();
                NotesAdapter notesAdapter=new NotesAdapter(MainActivity.this,allNotesList);
                recyclerAllNotes.setAdapter(notesAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void updateUserNote(UserNotes userNotes) {
        databaseNotes.child(userNotes.getNoteId()).setValue(userNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                    readAllNotes();
                }
            }
        });
        
    }

    @Override
    public void deleteNote(UserNotes userNotes) {
        databaseNotes.child(userNotes.getNoteId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Note Deleted successfully", Toast.LENGTH_SHORT).show();
                    readAllNotes();
                }
            }
        });
    }
}
