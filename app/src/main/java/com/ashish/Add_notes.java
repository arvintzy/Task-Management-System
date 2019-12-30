package com.ashish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_notes extends AppCompatActivity {

    @BindView(R.id.tool_bar_add_note)
    Toolbar toolBarAdd;
    @BindView(R.id.editText_title)
    EditText editTextTitle;
    @BindView(R.id.editText_description)
    EditText editTextDescription;
    @BindView(R.id.button_save_note)
    Button buttonSaveNote;
    DatabaseReference databaseNote;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        ButterKnife.bind(this);
        sharedPreferences= getSharedPreferences("FIRENOTEDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Saving Notes...");
        toolBarAdd.setTitle("Add Note");
        toolBarAdd.setTitleTextColor(Color.WHITE);
        toolBarAdd.setNavigationIcon(R.drawable.back_arrow);
        String userId=sharedPreferences.getString("UID","");
        databaseNote= FirebaseDatabase.getInstance().getReference("USERNOTE").child(userId);
        toolBarAdd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add_notes.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @OnClick(R.id.button_save_note)
    public void onViewClicked() {
        String title=editTextTitle.getText().toString();
        String des=editTextDescription.getText().toString();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yy");
        Calendar calendar=Calendar.getInstance();
        String todayDate=simpleDateFormat.format(calendar.getTime());
         if(!title.equalsIgnoreCase("")){
            if(!des.equalsIgnoreCase("")){
                progressDialog.show();
                String key=databaseNote.push().getKey();
                UserNotes userNotes=new UserNotes(title,des,todayDate,key);
                databaseNote.child(key).setValue(userNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(Add_notes.this, "Note saved Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Add_notes.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Add_notes.this, "Error Saving Note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
            }
         }else{
             Toast.makeText(Add_notes.this,"Please Enter title",Toast.LENGTH_SHORT).show();

         }
    }
}
