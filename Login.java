    package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

    public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText loginemail,loginpassword;
    Button loginbutton;
    DBfunctions dBfunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.login).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.login).setBackgroundResource(R.drawable.good_night_img);


        //filling the Spinner Data
        Spinner spine = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.profile, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spine.setAdapter(adapter);
        spine.setOnItemSelectedListener(this);


        //making a text as a link to registre activity
        TextView signup =findViewById(R.id.signup);
        String text = "You don't have an Account ? Sign Up now";
        SpannableString s =new SpannableString(text);
        ClickableSpan ck =  new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i = new Intent(Login.this,Registration.class);
                startActivity(i);
            }
        };
        s.setSpan(ck,27,35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setText(s);
        signup.setMovementMethod(LinkMovementMethod.getInstance());





        //Login with database sqlLite
        loginbutton = findViewById(R.id.loginbutton);
        loginemail  = findViewById(R.id.loginemail);
        loginpassword  = findViewById(R.id.loginpassword);

        dBfunctions= new DBfunctions(this);


        //Listner for Button sign in
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToast = spine.getSelectedItem().toString();

                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();
                if(email.equals("")| password.equals("")){

                    if( TextUtils.isEmpty(loginemail.getText())){

                        loginemail.setError( "Email is required!" );
                }
                if(TextUtils.isEmpty(loginpassword.getText())){
                    loginpassword.setError( "Password is required!" );
                }


                }
                else{
                    if(textToast.equals("User")){
                    boolean checkemailpass= dBfunctions.checkusernamepassword(email,password);
                    if(checkemailpass){
                        Toast.makeText(Login.this,"Sign in Succefly ",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this,dashbord.class);
                        startActivity(intent);}
                    else{
                        Toast.makeText(Login.this,"Something went Wrong! try again ",Toast.LENGTH_LONG).show();
                    }}
                    else {
                        boolean checkemailpass1= dBfunctions.checkusernamepassword(email,password);
                        if(checkemailpass1){
                            Toast.makeText(Login.this,"Sign in Succefly ",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);}
                        else{
                            Toast.makeText(Login.this,"Something went Wrong! try again ",Toast.LENGTH_LONG).show();
                        }
                    }

                    }
                }

        });





    }
//for spinner ItemSelected displaaying a Toast
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          String textToast = "Profile Selected is "+adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),textToast,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




}