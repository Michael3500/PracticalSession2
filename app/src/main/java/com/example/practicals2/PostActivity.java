package com.example.practicals2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PostActivity extends AppCompatActivity {

    EditText editTitle, editDescription, editSection;
    ImageView editImage;
    Button cancelButton;
    Button submitButton;
    public Uri imageUri;

    OutputStream outputStream;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //checking and requesting read and write access permissions
        if(ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if(ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //if button cancel is clicked, the user is redirected to the main activity without saving
        cancelButton = (Button)findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
            }
        });

        //when submit button is clicked, the text is saved to the database and the user is redirected to the main activity
        submitButton = (Button)findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View v) {

                ActiveAndroid.initialize(PostActivity.this);

                editTitle = findViewById(R.id.editTextTitle);
                editDescription = findViewById(R.id.editTextDescription);
                editSection = findViewById(R.id.editTextSection);

                String savetitle = editTitle.getText().toString();
                String savedescription = editDescription.getText().toString();
                String savesection = editSection.getText().toString();
                String saveimage = imageUri.toString();

                //sending data to the database and saving it
                PostClass pc = new PostClass(savetitle, savedescription, savesection, saveimage);
                pc.save();

                //redirection to Main Activity
                startActivity(new Intent(PostActivity.this, MainActivity.class));
            }

        });


        //going through the permissions for storage access
        editImage = findViewById(R.id.imageButtonImage);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery(); //if granted, access and select image
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });

    }

    private void submitAction(View v) throws IOException {
        ActiveAndroid.initialize(PostActivity.this);

        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        editSection = findViewById(R.id.editTextSection);
        editImage =  findViewById(R.id.imageButtonImage);

        PostClass myPost = new PostClass();
        myPost.title = editTitle.getText().toString(); //"Initial Tech";
        myPost.description = editDescription.getText().toString(); //"This is a testing post in Technology tab";
        myPost.section = editSection.getText().toString(); //"Technology";

        /*editImage.buildDrawingCache();
        Bitmap bmap = editImage.getDrawingCache();*/

        BitmapDrawable drawable = (BitmapDrawable)editImage.getDrawable();
        Bitmap bmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        myPost.imageDirectory = Base64.encodeToString(bb, Base64.DEFAULT);

        /*BitmapDrawable drawable = (BitmapDrawable)editImage.getDrawable();
        Bitmap bmap = drawable.getBitmap();
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/PostImages/");
        dir.mkdir();
        File file = new File(dir, System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        bmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getApplicationContext(), "Image Saved to Internal Storage",Toast.LENGTH_SHORT).show();
        outputStream.flush();
        outputStream.close();


        myPost.imageDirectory = dir.toString() + file.toString();*/

        //R.drawable.ic_laptop1;

        myPost.save();

        Toast.makeText(PostActivity.this, "Title: "+myPost.title+" ImageDirectory: "+myPost.imageDirectory,Toast.LENGTH_LONG).show();
    }

    //selecting the image from device
    private void pickImageFromGallery() {
        Intent intentImage = new Intent(Intent.ACTION_PICK);
        intentImage.setType("image/*");
        startActivityForResult(intentImage, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery(); //if granted, access and select image
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //storing the location of the image selected to be saved in the database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data.getData();
            editImage.setImageURI(imageUri);
        }
    }
}
