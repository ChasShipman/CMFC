package com.warbs.cmfclogin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class setUp extends AppCompatActivity {
    private EditText name, instrument, experience;
    private Button save;
    private CircleImageView profile;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private StorageReference UserProfileImageRef;



    final static int Gallery_Pick = 1;
    String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
       UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("profile Images");

        name = (EditText) findViewById(R.id.name);
        instrument = (EditText) findViewById(R.id.instrument);
        experience = (EditText) findViewById(R.id.experience);
        save = (Button)findViewById(R.id.save);
        profile = (CircleImageView) findViewById(R.id.profile);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInformation();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode ==RESULT_OK && data!=null)
        {
            Uri ImageUrl = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(setUp.this, "Image Successfully Stored", Toast.LENGTH_SHORT).show();
                            Task<Uri> downloadUrl = task.getResult().getStorage().getDownloadUrl();
                            reference.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Intent setup = new Intent(setUp.this, setUp.class);
                                                startActivity(setup );
                                                Toast.makeText(setUp.this, "Profile Image Stored Successfully", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                            {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(setUp.this, "Error Occurred:" + message , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Error Occurred: Image can't be cropped. Try Again!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveInformation() {

       String myName =  name.getText().toString();
       String myInstrument = instrument.getText().toString();
       String myExperience = experience.getText().toString();
       if(TextUtils.isEmpty(myName))
       {
           Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show();
       }
       else if(TextUtils.isEmpty(myInstrument))
       {
           Toast.makeText(this, "Please enter your instrument!", Toast.LENGTH_SHORT).show();

       }
       else if(TextUtils.isEmpty(myExperience))
       {
           Toast.makeText(this, "Please enter your experience!", Toast.LENGTH_SHORT).show();

       }
       else
       {
           HashMap userMap = new HashMap();
           userMap.put("Name", myName);
           userMap.put("Instrument", myInstrument);
           userMap.put("Experience", myExperience);
           reference.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {
                   if (task.isSuccessful()) {
                       mainActivity();
                       Toast.makeText(setUp.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       String message = task.getException().getMessage();
                       Toast.makeText(setUp.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }
    }

    private void mainActivity() {
        Intent intent = new Intent(setUp.this, MainActivity.class);
        startActivity(intent);
    }
}
