package com.ashu.postermaker.activities

import android.os.Bundle
import android.view.View
import com.ashu.postermaker.R
import android.content.Intent
import androidx.core.content.FileProvider
import android.graphics.Bitmap
import com.google.android.material.snackbar.Snackbar
import android.widget.RelativeLayout
import com.ashu.postermaker.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.os.Environment
import android.widget.ImageView
import com.ashu.postermaker.App
import java.util.*


class EditCompletionActivity : BaseActivity() {
    override fun getClassName(): String {
//        val name = EditCompletionActivity::class.java.name
        return "EditCompletionActivity"
    }


    private var needToRemoveImageFromCache:Boolean = false
    private var filePathNeedToRemove:String = ""

    lateinit var rootView:RelativeLayout
    lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ashu.postermaker.R.layout.activity_edit_completion)

        setMyTitle("Share")
        showBackButton()

        image = findViewById(R.id.image)
        image.setImageBitmap(App.instance.editingImageBitmap)
        rootView = findViewById(com.ashu.postermaker.R.id.rootView)


    }

    fun onHomeClick(v: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // call this to finish the current activity
    }

    fun onSaveImageClick(v: View){
        saveImageToExternalStorage(App.instance.editingImageBitmap)
    }


    fun onShareClick(v: View){
        saveFile(App.instance.editingImageBitmap)
    }


    private fun saveFile(bitmap: Bitmap?) {
        try {
            if (bitmap == null) {
                showAlertMessage("An Error occur, Please try again")
                return
            }
            val cachePath = File(this.cacheDir, "images")
            if (!cachePath.exists()){
                cachePath.mkdirs() // don't forget to make the directory
            }
            val path:String = cachePath.path +"/image.png"
            val stream = FileOutputStream(path) // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

        } catch (e: IOException) {
            showAlertMessage("An Error occur, Please try again")
            return
        }

        shareFileNow()
    }

    private fun showAlertMessage(s: String) {

        Snackbar.make(
                rootView, // Parent view
                ""+s, // Message to show
                Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()

    }

    private fun shareFileNow() {
        val imagePath = File(this.getCacheDir(), "images")
        val newFile = File(imagePath, "image.png")
        val contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile)
        if (contentUri != null) {
            filePathNeedToRemove = contentUri.path
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, this.getContentResolver().getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
            needToRemoveImageFromCache = true
        } else {
            showAlertMessage("An Error occur, Please try again")
        }
    }


    private fun saveImageToExternalStorage(bitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/"+getString(com.ashu.postermaker.R.string.app_name))
        if (!myDir.exists()){
            myDir.mkdirs()
        }
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fname = "Image-$n.jpg"
        val file = File(myDir, fname)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 1000, out)
            out.flush()
            out.close()
            showAlertMessage("Successfully Save Image in Gallery")
        } catch (e: Exception) {
            e.printStackTrace()
            showAlertMessage("Failed to Save Image in Gallery")
        }
    }

    override fun onDestroy() {
        if (needToRemoveImageFromCache){
            val file = File(filePathNeedToRemove)
            if (file.exists()){
                file.delete()
            }
        }

        super.onDestroy()
    }

}
