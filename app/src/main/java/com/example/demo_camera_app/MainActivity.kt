package com.example.demo_camera_app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demo_camera_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE=2
    }

    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnCapture?.setOnClickListener {
            //check permission
            if(ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
                )==PackageManager.PERMISSION_GRANTED){
                //open camera
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                //request permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //open camera
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                //permission denied
                Toast.makeText(this@MainActivity,"Denied Permission",Toast.LENGTH_SHORT).show()
            }
        }
    }


    //automatically called after camera activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode== CAMERA_REQUEST_CODE){
            val image:Bitmap=data?.extras?.get("data") as Bitmap
            binding?.ivImage?.setImageBitmap(image as Bitmap)
            Toast.makeText(this@MainActivity,"Image Captured",Toast.LENGTH_SHORT).show()
        }
    }
}