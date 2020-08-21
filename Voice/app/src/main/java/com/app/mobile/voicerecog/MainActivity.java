package com.app.mobile.voicerecog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button homeNext;
    private int Storage_Permission =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeNext = (Button) findViewById(R.id.nextPage);

        homeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for permission granted by the user in if, otherwise call checkpermission function.
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);
                } else {
                    //call the function for permission.
                    checkPermission();
                }
            }
        });
    }

        //It will pop up a permission for user if not granted already.
        private void checkPermission(){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Allow app to access your device microphone")
                        //if permission granted by user what will happen demonstrated here
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.RECORD_AUDIO},Storage_Permission);
                                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                            }
                        })
                        //if permission denied by user what will happen demonstrated here
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
            }else{
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},Storage_Permission);
            }

        }


}
