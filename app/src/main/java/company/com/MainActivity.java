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

        //Go to Home Page

        mBtLaunchHome = (Button) findViewById(R.id.buttonLogin);

        mBtLaunchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchHome();
            }
        });


        //Go to Register Page
        mBtLaunchRegister = (Button) findViewById(R.id.buttonRegister);

        mBtLaunchRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchRegister();
            }
        });

    }

    public void launchRegister(){

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

    }
    public void launchHome(){
        Intent intent = new Intent(this, HomeConected.class);
        startActivity(intent);
    }
}
