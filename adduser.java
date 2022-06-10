package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class adduser extends AppCompatActivity {
    EditText regname,regmobile,regemail,regpassword,regcin;
    Button regbutton;
    DBfunctions dBfunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        //return button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //instantiation
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.adduseract).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.adduseract).setBackgroundResource(R.drawable.good_night_img);
        regname =findViewById(R.id.regnameuser);
        regmobile=findViewById(R.id.regmobileuser);
        regemail =findViewById(R.id.regemailuser);
        regpassword=findViewById(R.id.regpassworduser);
        regcin=findViewById(R.id.regcinuser);
        regbutton=findViewById(R.id.regbuttonuser);



        //After clicking registration button
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBfunctions= new DBfunctions(adduser.this);

                String  name= regname.getText().toString();
                long    mobile =Long.parseLong(regmobile.getText().toString());
                String  email = regemail.getText().toString();
                String  cin =regcin.getText().toString();
                String  password = regpassword.getText().toString();

                if(name.equals("")||regmobile.getText().toString().equals("")||email.equals("")||cin.equals("")||password.equals("")){
                    Toast.makeText(adduser.this,"Oops You Have to fill all the fields ",Toast.LENGTH_LONG).show();

                }
                else{
                    //email verification if it exists or not in our db
                    boolean checkemail = dBfunctions.checkemail(email);
                    if(checkemail==false){
                        boolean insertdata=dBfunctions.insertData(email,password,name,mobile,cin,"User");
                        if(insertdata){
                            Toast.makeText(adduser.this,"Donne Regisstred succefly ! Sign In now ",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(adduser.this,"Something went Wrong! try again ",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(adduser.this,"the Email used is already exist ",Toast.LENGTH_LONG).show();
                    }
                }

//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                startActivity(intent);
            }
        });



    }

}