package company.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //This method allows to start the Register Activity
    public void launchRegister(View view){

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    //This method allows to start the HomeConected Activity
    public void launchHome(View view){
        Intent intent = new Intent(this, HomeConected.class);
        startActivity(intent);
    }

    public void launchSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
