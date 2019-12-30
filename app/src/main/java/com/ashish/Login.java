package com.ashish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    @BindView(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @BindView(R.id.tv_otp_login)
    TextView tvOtpLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sharedPreferences= getSharedPreferences("FIRENOTEDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    @OnClick({R.id.login_btn, R.id.text_sign_up, R.id.tv_forget_pass,R.id.tv_otp_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pass:
                String emailId = emailEditText.getText().toString();
                if (!emailId.equalsIgnoreCase("")) {
                    firebaseAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Please check your email for resetting password", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Login.this, "Error resending password", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.login_btn:
                String email = emailEditText.getText().toString();
                String password = passEditText.getText().toString();
                if (!email.equalsIgnoreCase("")) {
                    if (!password.equalsIgnoreCase("")) {
                        loginUser(email, password);
                    } else {
                        Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.text_sign_up:
                Intent intentSignUp = new Intent(Login.this, SignUp.class);
                startActivity(intentSignUp);
                break;

            case R.id.tv_otp_login:
                Intent intentOtp=new Intent(Login.this,OtpLogin.class);
                startActivity(intentOtp);
                finish();
                break;


        }
    }

    public void loginUser(String email, String password) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    editor.putBoolean("LOGINSTATUS",true);
                    editor.commit();
                    Toast.makeText(Login.this, "Logged in successfullt", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    editor.putBoolean("LOGINSTATUS",false);
                    editor.commit();

                    String errorMsg = task.getException().getMessage();
                    Toast.makeText(Login.this, "" + errorMsg, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
