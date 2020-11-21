package ac.id.atmaluhur.uts_amub_ti7a_1711500091_detasari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button BtnLogin, BtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisiasi tombol
        BtnLogin = findViewById(R.id.btnlogin);
        BtnRegister = findViewById(R.id.btnreg);

        //toast
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Akun Anda Belum Terdaftar !", Toast.LENGTH_SHORT).show();
            }
        });

        //intent
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerone = new Intent(MainActivity.this, RegisterOneAct.class);
                startActivity(registerone);

            }
        });
    }
}
