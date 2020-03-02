package company.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public Button mBtLaunchRegister;
    public Button mBtLaunchHome;

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
}
