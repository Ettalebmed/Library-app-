package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class Registration extends AppCompatActivity {
    EditText regname,regmobile,regemail,regpassword,regcin;
    Button regbutton;
    DBfunctions dBfunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //***************set the  background image acording to time *******
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.reg).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.reg).setBackgroundResource(R.drawable.good_night_img);

// *********************************


        TextView signin =findViewById(R.id.regsignin);
        String text = "You Already Have an Account ? Sign In ";
        SpannableString s =new SpannableString(text);
        ClickableSpan ck =  new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i = new Intent(Registration.this,Login.class);
                startActivity(i);
            }
        };
        s.setSpan(ck,30,38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signin.setText(s);
        signin.setMovementMethod(LinkMovementMethod.getInstance());

        //instantiation
        regname =findViewById(R.id.regname);
        regmobile=findViewById(R.id.regmobile);
        regemail =findViewById(R.id.regemail);
        regpassword=findViewById(R.id.regpassword);
        regcin=findViewById(R.id.regcin);
        regbutton=findViewById(R.id.regbutton);



        //After clicking registration button
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBfunctions= new DBfunctions(Registration.this);

                String  name= regname.getText().toString();
                long    mobile =Long.parseLong(regmobile.getText().toString());
                String  email = regemail.getText().toString();
                String  cin =regcin.getText().toString();
                String  password = regpassword.getText().toString();

                if(name.equals("")||regmobile.getText().toString().equals("")||email.equals("")||cin.equals("")||password.equals("")){
                    Toast.makeText(Registration.this,"Oops You Have to fill all the fields ",Toast.LENGTH_LONG).show();

                }
                else{
                    //email verification if it exists or not in our db
                    boolean checkemail = dBfunctions.checkemail(email);
                    if(checkemail==false){
                        boolean insertdata=dBfunctions.insertData(email,password,name,mobile,cin,"User");
                        if(insertdata){
                            Toast.makeText(Registration.this,"Donne Regisstred succefly ! Sign In now ",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Registration.this,"Something went Wrong! try again ",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Registration.this,"the Email used is already exist ",Toast.LENGTH_LONG).show();
                    }
                }

//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                startActivity(intent);
            }
        });



    }

}