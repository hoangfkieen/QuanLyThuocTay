package com.tdc.quanlythuoctay.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tdc.quanlythuoctay.R;

public class splashscreen extends MainActivity {
    private ImageView mImageView;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mImageView = (ImageView) findViewById(R.id.image2);
        startAnimation();
    }

    private void startAnimation() {
        //Animation rotate = AnimationUtils.loadAnimation(this,  R.anim.rotate);
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RESTART, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotate.setDuration(200);
        rotate.setRepeatCount(-1);

        mImageView.setAnimation(rotate);


        mImageView.setAnimation(rotate);

        mThread = new Thread() { //Tạo và khởi chạy Luồng mới
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 3500) { // nếu biến waited < 3500 thực hiện :
                    try {
                        sleep(100); // Tạm ngưng thead trong 100 mili
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;  // + thêm 100 cho biến waited
                }
                // sau 35 lần chạy trong vòng lặp thì thoát : tổng cộng 3500 milisecon = 3.5 giây
                splashscreen.this.finish(); // Kết thúc màn hình loading
                Intent intent = new Intent(splashscreen.this, Login.class); // chuyển sang màn hình Login
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        };
        mThread.start();
    }
}
