package com.tdc.quanlythuoctay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tdc.quanlythuoctay.Database.DatabaseHandler;
import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.AccoutModel;

import java.io.File;
import java.util.Objects;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class Account extends AppCompatActivity {
private Button btn_register,btnBackout;
private EditText input_email,input_password,input_repass;
private AccoutModel usermodel;
private ImageButton imageAvatar;
private EasyImage easyImage;
DatabaseHandler db;
private static final int GALLERY_REQUEST_CODE = 7502;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
         db = new DatabaseHandler(Account.this);
        usermodel = new AccoutModel();
        easyImage = new EasyImage.Builder(Account.this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserTitle("Chọn Hình Đại Diện")
                .allowMultiple(false)
                .build();
        mapping();
        setEvent();
    }

 private void mapping()
 {
     btn_register = (Button) findViewById(R.id.btn_register);
     btnBackout = (Button) findViewById(R.id.btnBackout);
     input_email =(EditText) findViewById(R.id.input_email);
     input_password =(EditText) findViewById(R.id.input_password);
     input_repass =(EditText) findViewById(R.id.input_repass);
     imageAvatar=(ImageButton)findViewById(R.id.imageAvatar);



 }
 private void setEvent()
 {
     btnBackout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent login = new Intent(Account.this, Login.class);
             startActivity(login);
             // close splash activity
             finish();
         }
     });
     imageAvatar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
             if (arePermissionsGranted(necessaryPermissions)) {
                 easyImage.openGallery(Account.this);
             } else {
                 requestPermissionsCompat(necessaryPermissions, GALLERY_REQUEST_CODE);
             }
         }
     });
     btn_register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(usermodel.getAvatar() == null ||usermodel.getAvatar().isEmpty())
             {
                 Toast.makeText(Account.this,"Vui lòng chọn ảnh đại diện !",Toast.LENGTH_LONG).show();
             }
             else if(input_email.getText().toString().trim().length() == 0 || input_password.getText().toString().trim().length() == 0 || input_repass.getText().toString().trim().length() == 0 )
             {
                 Toast.makeText(Account.this,"Vui lòng nhập đầy đủ thông tin  !",Toast.LENGTH_LONG).show();
             }
             else if(!Objects.equals(input_password.getText().toString().trim(), input_repass.getText().toString().trim()) )
             {
                 Toast.makeText(Account.this,"Hai mật khẩu không trùng khớp  !",Toast.LENGTH_LONG).show();
             }
             else
             {
//                 testmodel = db.getUser("Takasi3");
//                 File imgFile = new  File(testmodel.getAvatar());
//                 if(imgFile.exists()){
//                     Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                     imageAvatar.setImageBitmap(myBitmap);
//                 }
                 usermodel.setUser(input_email.getText().toString().trim());
                 usermodel.setPass(input_password.getText().toString().trim());
                 db.addUser(usermodel);
                 Toast.makeText(Account.this,"TẠO TÀI KHOẢN THÀNH CÔNG !",Toast.LENGTH_LONG).show();
             }

         }
     });
 }
    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(Account.this, permissions, requestCode);
    }
    private void onPhotosReturned(@NonNull MediaFile[] returnedPhotos) {
        usermodel.setAvatar(returnedPhotos[0].getFile().getPath());
        Picasso.get()
                .load(returnedPhotos[0].getFile())
                .fit()
                .centerCrop()
                .into(imageAvatar);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                onPhotosReturned(imageFiles);

            }
            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }

}