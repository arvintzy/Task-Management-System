package com.ashish;

import android.app.ProgressDialog;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
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
    PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Verifying phone number...");
        onVerificationStateChangedCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.dismiss();
                Toast.makeText(OtpLogin.this,"Verification successfull",Toast.LENGTH_SHORT).show();

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

        String mobile="+91"+editText.getText().toString();
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
}
