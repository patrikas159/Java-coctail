package com.corona.coronazp20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);

        Button login = findViewById(R.id.login);

        final CheckBox rememberme = (CheckBox) findViewById(R.id.rememberMe);
        //bus konstruojamas vartotojo objektas perduodant context'a (langa kuriame esame)
        final User user=new User(LoginActivity.this);
        //patikriname, ar paskutini karta buvo pazymetas checkbox remember me
        rememberme.setChecked(user.isRememberedForLogin());

        //Aprasoma prisiminti mane checkbox logika
        if (rememberme.isChecked()){//jeigu checkbox buvo pazymetas-vartotojas pageidavo, kad informacija buvo issaugota
            username.setText(user.getUsernameForLogin(),TextView.BufferType.EDITABLE);//setText-uzpildysime user informacija, editable - suteiksim galimybe paredaguoti duomenis
            password.setText(user.getPasswordForLogin(),TextView.BufferType.EDITABLE);
        } else {//jeigu checkbox buvo nepazymetas-vartotojas nenorejo, kad informacija buvo issaugota
            username.setText("", TextView.BufferType.EDITABLE);
            password.setText("", TextView.BufferType.EDITABLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // čia bus vykdomas kodas, kai paspaudžiamas mygtukas

                username.setError(null);
                if(Validation.isValidUsername(username.getText().toString()) && Validation.isValidPassword(password.getText().toString())) {
                    user.setUsernameForLogin(username.getText().toString());
                    user.setPasswordForLogin(password.getText().toString());
                    if (rememberme.isChecked()){
                        user.setRemembermeKeyForLogin(true);
                    } else {
                        user.setRemembermeKeyForLogin(false);
                    }
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
                } else { // jeigu username arba password neteisingas
                    username.setError("Error! Wrong username or/ and password");
                    username.requestFocus();
                }
            }
        });

    }

}
