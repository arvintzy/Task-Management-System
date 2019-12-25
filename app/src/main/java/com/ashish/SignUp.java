package com.ashish;

import android.app.ProgressDialog;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference("USERS");
        progressDialog=new ProgressDialog(this);
    }

    @OnClick(R.id.signup_btn)
    public void onViewClicked() {
        String name=signup_name.getText().toString();
        String email=signup_email.getText().toString();
        String pass=signup_pass.getText().toString();
        if(!name.equalsIgnoreCase("")){
            if(!email.equalsIgnoreCase("")){
                if(!pass.equalsIgnoreCase("")){
                    registerUser(name,email,pass);
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
    public void registerUser(String name,String email,String password){
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
    }
}
