package ac.id.atmaluhur.uts_amub_ti7a_1711500091_detasari;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {
        Button register,addfoto;
        ImageView uploadphoto;
        EditText Hobi,alamat;


        Uri photo_loc;
        Integer photo_max = 1;

        DatabaseReference ref;
        StorageReference stor;

        String USERNAME_KEY ="usernamekey";
        String username_key ="";
        String username_key_new ="";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register_two);

            getusernamelocal();
            uploadphoto = findViewById(R.id.imgaddfoto);
            addfoto = findViewById(R.id.btnaddfoto);
            register = findViewById(R.id.btnregis);
            Hobi = findViewById(R.id.edhobi);
            alamat = findViewById(R.id.edalamat);

            uploadphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findPhoto();
                }

            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ref = FirebaseDatabase.getInstance().getReference().child("user").child(username_key_new);
                    stor = FirebaseStorage.getInstance().getReference().child("PhotoUsers").child(username_key_new);

                    //falidasi file photo
                    if (photo_loc != null) {
                        StorageReference stor1 = stor.child(System.currentTimeMillis() + "." + getFileExtension(photo_loc));
                        stor1.putFile(photo_loc).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String uri_photo = taskSnapshot.getDownloadUrl().toString();
                                ref.getRef().child("url_photo").setValue(uri_photo);
                                ref.getRef().child("hobi").setValue(Hobi.getText().toString());
                                ref.getRef().child("alamat").setValue(alamat.getText().toString());
                            }
                        });
                    }

                }
            });
        }
        String getFileExtension ( Uri uri) {
            ContentResolver cr = getContentResolver();
            MimeTypeMap mtm = MimeTypeMap.getSingleton();
            return mtm.getExtensionFromMimeType(cr.getType(uri));
        }
        public void findPhoto() {
            Intent pic = new Intent();
            pic.setType("image/* ");
            pic.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(pic, photo_max);
        }
        public void  getusernamelocal(){
            SharedPreferences sp = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
            username_key_new = sp.getString(username_key,"");

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode== photo_max && resultCode == RESULT_OK && data !=null && data.getData() !=null){
                photo_loc= data.getData();

                Picasso.with(this).load(photo_loc).centerCrop().fit().into(uploadphoto);
            }
        }
    }
