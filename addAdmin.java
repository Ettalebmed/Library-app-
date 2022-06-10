package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class addAdmin extends AppCompatActivity {
    EditText regname,regmobile,regemail,regpassword,regcin;
    Button regbutton;
    DBfunctions dBfunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.regnameAA).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.regnameAA).setBackgroundResource(R.drawable.good_night_img);
        //button de retour
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        regname =findViewById(R.id.regnameA);
        regmobile=findViewById(R.id.regmobileA);
        regemail =findViewById(R.id.regemailA);
        regpassword=findViewById(R.id.regpasswordA);
        regcin=findViewById(R.id.regcinA);
        regbutton=findViewById(R.id.addadmin);

        dBfunctions= new DBfunctions(this);


        //After clicking registration button
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  name= regname.getText().toString();
                long    mobile =Long.parseLong(regmobile.getText().toString());
                String  email = regemail.getText().toString();
                String  cin =regcin.getText().toString();
                String  password = regpassword.getText().toString();

                if(name.equals("")||regmobile.getText().toString().equals("")||email.equals("")||cin.equals("")||password.equals("")){
                    Toast.makeText(addAdmin.this,"Oops You Have to fill all the fields ",Toast.LENGTH_LONG).show();

                }
                else{
                    //email verification if it exists or not in our db
                    boolean checkemail = dBfunctions.checkemail(email);
                    if(checkemail==false){
                        boolean insertdata=dBfunctions.insertData(email,password,name,mobile,cin,"Admin");
                        if(insertdata){
                            Toast.makeText(addAdmin.this,"Admine Regisstred succefly ! Sign In now ",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(addAdmin.this,"Something went Wrong! try again ",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(addAdmin.this,"the Email used is already exist ",Toast.LENGTH_LONG).show();
                    }
                }

//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                startActivity(intent);
            }
        });



    }

}
