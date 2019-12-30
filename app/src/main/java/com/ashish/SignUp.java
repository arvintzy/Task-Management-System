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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.signup_name)
    EditText signup_name;
    @BindView(R.id.signup_email)
    EditText signup_email;
    @BindView(R.id.signup_pass)
    EditText signup_pass;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.textView3)
    TextView textView3;


    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseUser;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        sharedPreferences= getSharedPreferences("FIRENOTEDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference("USERS");
        progressDialog=new ProgressDialog(this);
        if(getIntent().hasExtra("MOBILE")){
            signup_pass.setVisibility(View.GONE);
        }else {
            signup_pass.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.signup_btn)
    public void onViewClicked() {
        String name=signup_name.getText().toString();
        String email=signup_email.getText().toString();
        String pass="";
        if(getIntent().hasExtra("MOBILE")){
            if(!name.equalsIgnoreCase("")){
                if(!email.equalsIgnoreCase("")){
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    String mobile=getIntent().getExtras().getString("MOBILE");
                    String uid=getIntent().getExtras().getString("UID");
                    UserInfo userInfo=new UserInfo(name,email,mobile);
                    databaseUser.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                editor.putString("UID",uid);
                                Toast.makeText(SignUp.this,"User is registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignUp.this,"Error registering user",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else{
                    Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show();
            }
        }else {
            pass=signup_pass.getText().toString();
            registerUser(name,email,pass);
        }

    }
    public void registerUser(String name,String email,String password){
        if(!name.equalsIgnoreCase("")){
            if(!email.equalsIgnoreCase("")){
                if(!password.equalsIgnoreCase("")){
                    progressDialog.setMessage("Please wait....");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                                String uid=currentUser.getUid();
                                UserInfo userinfo=new UserInfo(name,email,"");


                                databaseUser.child(uid).setValue(userinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if(task.isSuccessful()){
                                            editor.putString("UID",uid);
                                            editor.commit();
                                            Toast.makeText(SignUp.this,"User is registered Successfully",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                                Toast.makeText(SignUp.this,"user is registered",Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(SignUp.this,"Error registering user\n"+name+" "+email+" "+password,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Please enter valid email",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show();
        }

    }
}
