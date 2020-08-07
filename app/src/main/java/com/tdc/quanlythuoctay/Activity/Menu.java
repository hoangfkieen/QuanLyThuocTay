package com.tdc.quanlythuoctay.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.AccoutModel;

import java.io.File;


public class Menu extends MainActivity {
private Button btnLogout,btnQLNT,btnQLT,btnHD;
private TextView tvID;
private ImageView imgAvatar;
    private AccoutModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Ánh Xạ

        btnLogout =(Button)  findViewById(R.id.btnLogout);
        btnQLNT = (Button) findViewById(R.id.btnQLNT);
        btnQLT =(Button)  findViewById(R.id.btnQLT);
        btnHD = (Button) findViewById(R.id.btnHD);
        tvID = (TextView) findViewById(R.id.tvID);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        userModel = getUser(Menu.this);
        if(userModel.getAvatar().toUpperCase()!= "LUULINH")
        {
            tvID.setText(userModel.getUser());
            File imgFile = new  File(userModel.getAvatar());
                 if(imgFile.exists()){
                     Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                     imgAvatar.setImageBitmap(myBitmap);
                 }
        }
        setEvent();
    }

    private void setEvent() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDefaults(getApplicationContext()); //Xoá thông tin đăng nhập khi đăng xuất
                startActivity(new Intent(Menu.this, Login.class));
                finish();
            }
        });
        btnQLNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, lstPharmacy.class));
            }
        });
        btnQLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, medicines.class));
            }
        });
        btnHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Bill.class));
            }
        });
    }
}