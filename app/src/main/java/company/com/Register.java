package company.com;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Register extends AppCompatActivity {
    public Button mBtLaunchRegister21;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        //Set max date
        ((DatePicker)findViewById((R.id.datePicker1))).setMaxDate(new Date().getTime());




        //Add a Spinner to show all the city available in the app
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerCityList);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        }

    public final static boolean isValidEmail(EditText target1) {
        CharSequence target = target1.getText();
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPassword(EditText target, EditText target2){
        String password = target.getText().toString();
        String password2 = target2.getText().toString();
        if(password.isEmpty() || password2.isEmpty())
            return false;
        if(password.equals(password2))
            return true;
        return false;
    }


    public void register(View view){
        boolean result = isValidEmail((EditText)findViewById(R.id.email));
        boolean result2 = isValidPassword((EditText)findViewById(R.id.password),(EditText)findViewById(R.id.passwordCheck));
        if(result==true && result2==true ) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    }
