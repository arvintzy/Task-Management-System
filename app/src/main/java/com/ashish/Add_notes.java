package com.ashish;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        ButterKnife.bind(this);
        toolBarAdd.setTitle("Add Note");
        toolBarAdd.setTitleTextColor(Color.WHITE);
        toolBarAdd.setNavigationIcon(R.drawable.back_arrow);

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

    }
}
