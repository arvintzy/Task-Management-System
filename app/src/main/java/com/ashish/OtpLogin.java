package com.ashish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpLogin extends AppCompatActivity {

    @BindView(R.id.btn_login_otp)
    Button btnLoginOtp;
    ProgressDialog progressDialog;
    @BindView(R.id.editText)
    EditText editText;
    FirebaseAuth firebaseAuth;
    String mobile="";

    PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Verifying phone number...");
        firebaseAuth=FirebaseAuth.getInstance();
        onVerificationStateChangedCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
               signInWithMobile(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(OtpLogin.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };

    }

    @OnClick(R.id.btn_login_otp)
    public void onViewClicked() {

         mobile="+91"+editText.getText().toString();
        if(!mobile.equalsIgnoreCase("")){
            verifyMobile(mobile);
        }else {
            Toast.makeText(OtpLogin.this,"Otp failed to sent!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void verifyMobile(String mobile){
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,60, TimeUnit.SECONDS,this,onVerificationStateChangedCallbacks);
    }
    public void signInWithMobile(PhoneAuthCredential phoneAuthCredential){
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();

                    Toast.makeText(OtpLogin.this,"Verification successfull",Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser=task.getResult().getUser();
                    String uid=currentUser.getUid();

                    Intent intent=new Intent(OtpLogin.this,SignUp.class);
                    intent.putExtra("MOBILE",mobile);
                    intent.putExtra("UID",uid);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(OtpLogin.this,"Error using otp login",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
