package com.ashish;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.text_home_no_data)
    TextView textHomeNoData;
    @BindView(R.id.btn_add_note)
    FloatingActionButton btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolBar.setTitle("FireNotes");
        toolBar.setTitleTextColor(Color.WHITE);

    }

    @OnClick(R.id.btn_add_note)
    public void onViewClicked() {
        Intent intent=new Intent(MainActivity.this,Add_notes.class);
        startActivity(intent);
        finish();
    }
}
