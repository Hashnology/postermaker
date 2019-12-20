package com.ashu.postermaker.utility

import android.R.id.edit
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import android.content.ContentUris.parseId
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.*
import java.nio.file.Files


object ImageHandler {

    private val IMAGE_DIR = "Flyer_Images"

    public fun saveToInternalSorage(bitmapImage: Bitmap,fileName: String, directoryName:String): Boolean {
        val cw = ContextWrapper(App.getContext())
        val directory = cw.getDir(directoryName, MODE_PRIVATE)
//        val directory = cw.getDir(IMAGE_DIR, MODE_PRIVATE)
        if (!directory.exists()){
            directory.mkdir()
        }
        val mypath = File(directory, ""+fileName)

        var fos: FileOutputStream? = null
        try {

            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }


    public fun getImageURIFromAppStorage(dir:String, name:String): String? {
        val cw = ContextWrapper(App.getContext())
        val directory = cw.getDir(dir, MODE_PRIVATE)
//        val directory = cw.getDir(IMAGE_DIR, MODE_PRIVATE)
        if (!directory.exists()){
            directory.mkdir()
        }
        val path = File(directory, name)
        return path.path

    }

    public fun loadAllImagesFromAppStorage(dir:String): MutableList<Bitmap> {
        val list: MutableList<Bitmap> = arrayListOf()

        try {
            val cw = ContextWrapper(App.getContext())
            val path1 = cw.getDir(dir, Context.MODE_PRIVATE)
            var files: Array<out File>? = null
            if (path1.exists()){
                files = path1.listFiles()
            }

            for (i in 0 until files?.size!!) {
                val file: File = files[i]
                val f = File(path1, file.name)
                val b = BitmapFactory.decodeStream(FileInputStream(f)) // b is bitmap
                list.add(b)
            }

//            val img = findViewById(R.id.viewImage) as ImageView
//            img.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }


        return list
    }

    public fun loadImageFromStorage(path: String) {

        try {
            val cw = ContextWrapper(App.getContext())
            val path1 = cw.getDir(IMAGE_DIR, Context.MODE_PRIVATE)
            val f = File(path1, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f)) // b is bitmap
//            val img = findViewById(R.id.viewImage) as ImageView
//            img.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }



    public fun loadImageToExternalStorage(bitmapImage: Bitmap): String? {
        return MediaStore.Images.Media.insertImage(App.getContext().contentResolver, bitmapImage, "" , "");


    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}