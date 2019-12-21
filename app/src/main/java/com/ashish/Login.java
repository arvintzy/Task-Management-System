package com.ashish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.email_editText)
    EditText emailEditText;
    @BindView(R.id.pass_editText)
    EditText passEditText;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.text_sign_up)
    TextView textSignUp;
    @BindView(R.id.textView2)
    TextView textView2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.login_btn, R.id.text_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                break;
            case R.id.text_sign_up:
                Intent intentSignUp=new Intent(Login.this,SignUp.class);
                startActivity(intentSignUp);
                break;

        }
    }
}
