package com.ashish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ThrowOnExtraProperties;

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


    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @OnClick({R.id.login_btn, R.id.text_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String email=emailEditText.getText().toString();
                String password=passEditText.getText().toString();
                if(!email.equalsIgnoreCase("")){
                    if(!password.equalsIgnoreCase("")){
                        loginUser(email,password);
                    }else {
                        Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.text_sign_up:
                Intent intentSignUp=new Intent(Login.this,SignUp.class);
                startActivity(intentSignUp);
                break;

        }
    }
    public void loginUser(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      Toast.makeText(Login.this,"Logged in successfullt",Toast.LENGTH_SHORT).show();
                      Intent intent=new Intent(Login.this,MainActivity.class);
                      startActivity(intent);
                      finish();
                  }else {
                    String errorMsg=task.getException().getMessage();
                    Toast.makeText(Login.this,""+errorMsg,Toast.LENGTH_SHORT).show();

                  }
            }
        });
    }
}
