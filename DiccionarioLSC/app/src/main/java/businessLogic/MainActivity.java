package businessLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import com.example.diccionariolsc.R;

public class MainActivity extends AppCompatActivity {

    Button bt_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_registrar = (Button) findViewById(R.id.btReg);
        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt_registrar = new Intent(MainActivity.this, Registrarse.class);
                startActivity(bt_registrar);
                MainActivity.this.startActivity(bt_registrar);
            }
        });
    }

}