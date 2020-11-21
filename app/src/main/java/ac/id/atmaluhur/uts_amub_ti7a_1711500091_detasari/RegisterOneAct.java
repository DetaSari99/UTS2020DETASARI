package ac.id.atmaluhur.uts_amub_ti7a_1711500091_detasari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {
    Button Next;
    EditText Username, Password, Email;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_login = database.getReference("RegisterOne");

        Username = findViewById(R.id.edun);
        Password = findViewById(R.id.edpass);
        Email = findViewById(R.id.edmail);

       Next = findViewById(R.id.btnnext);

        //berpindah ke activity registertwo
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotonextregister = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                startActivity(gotonextregister);
            }
        });
        //menyimpan data ke localstorage(handphone)
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key,Username.getText().toString());//mengambil data inputan username ke variabel ursername
        editor.apply();

        //proses simopan ke database firebase
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(Username.getText().toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("username").setValue(Username.getText().toString());
                dataSnapshot.getRef().child("password").setValue(Password.getText().toString());
                dataSnapshot.getRef().child("email").setValue(Email.getText().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}