package com.ashu.postermaker.activities

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.utility.ImageHandler
import com.ashu.postermaker.utility.PermissionHelper
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import com.google.android.material.snackbar.Snackbar
import android.widget.*
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlin.collections.MutableList as MutableList1
import android.provider.Settings
import android.text.InputType
import androidx.core.content.ContextCompat
import android.text.Layout
import android.view.MotionEvent
import android.view.animation.*
import androidx.recyclerview.widget.RecyclerView
import com.ashu.postermaker.Constants
import com.ashu.postermaker.utility.Util
import com.theartofdev.edmodo.cropper.CropImage
import com.xiaopo.flying.sticker.DrawableSticker
import com.xiaopo.flying.sticker.Sticker
import com.xiaopo.flying.sticker.StickerView
import com.xiaopo.flying.sticker.TextSticker
import java.io.IOException
import android.view.animation.Animation.AnimationListener as AnimationListener1


class EditImageActivity : BaseActivity() {

    override fun getClassName(): String {
        return "EditImageActivity"
    }


    private val IMAGE_PICK_CODE = 123
    private val IMAGE_CAMERA_CODE = 122
    private val EXPORT_IMAGE_CODE = 1122


    private lateinit var rel_layer: LinearLayout
    private lateinit var drawer_rel: RelativeLayout
    private lateinit var layer_recycler: RecyclerView
    private lateinit var layer_side_open_btn: ImageView

    private lateinit var sticker_view: StickerView
    private lateinit var sticker_image_view: ImageView
    private lateinit var image: ImageView
    private lateinit var rootView: RelativeLayout
    private lateinit var lastTextSticker: TextSticker

    private lateinit var undoList: ArrayList<Sticker>
    private lateinit var redoList: ArrayList<Sticker>
    private lateinit var latestSticker: Sticker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_image)


        init()
        btnLock?.setOnClickListener{
              lockUnlockSticker()
      }

        btnUndo?.setOnClickListener{
            undoSticker()
        }

        btnRedo?.setOnClickListener{
            redoSticker()
        }




        btnExport!!.setOnClickListener(View.OnClickListener {
            if (!PermissionHelper.isPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PermissionHelper.requestThesePermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, EXPORT_IMAGE_CODE)
                return@OnClickListener
            }
            ImageHandler.saveToInternalSorage(App.instance.editingImageBitmap, "" + System.currentTimeMillis(), Constants.USER_EDIT_IMAGE_DIR)
            startActivity(Intent(this, EditCompletionActivity::class.java))

        })


    }



    private fun init(){
        showBackButton()
        hideSetting()
        hideTitle()
        showEditImageViews()
        image = findViewById(R.id.temp_view)
        rootView = findViewById(R.id.rootView)
        sticker_view = findViewById(R.id.sticker_view)
        sticker_image_view = findViewById(R.id.main_bg)
        sticker_image_view.setImageBitmap(App.instance.editingImageBitmap)

        undoList = ArrayList()
        redoList = ArrayList()

        rel_layer = findViewById(R.id.rel_layer)
        drawer_rel = findViewById(R.id.drawer_rel)
        layer_recycler = findViewById(R.id.layer_recycler)
        layer_side_open_btn = findViewById(R.id.layer_side_open_btn)

        layer_side_open_btn.setOnClickListener {


            onDrawerIconClick()
        }

    }

    private fun onDrawerIconClick() {
        var animation:Animation
        var animationListener: Animation.AnimationListener

        if(drawer_rel.visibility == View.GONE){
            animation = inFromLeftAnimation()
            animationListener = object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation) {
                    drawer_rel.visibility = View.VISIBLE
                }
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationStart(animation: Animation) {}
            }

        }else{
            animation = inFromRightAnimation()
            animationListener = object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation) {
                    drawer_rel.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}

                override fun onAnimationStart(animation: Animation) {}
            }

        }

        animation.setAnimationListener(animationListener)
        drawer_rel.startAnimation(animation);
        layer_side_open_btn.startAnimation(animation);




    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            EXPORT_IMAGE_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                    ImageHandler.saveToInternalSorage(App.instance.editingImageBitmap, "" + System.currentTimeMillis(), Constants.USER_EDIT_IMAGE_DIR)
                    startActivity(Intent(this, EditCompletionActivity::class.java))
                }
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_CAMERA_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap;
                    val fileName = "" + System.currentTimeMillis()
                    var imageURI:Uri = ImageHandler.getImageUri(this,photo)
                    CropImage.activity(imageURI)
                            .start(this);

                }

            }

            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        val contentURI = data!!.data
                        try {
                            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                            val fileName = "" + System.currentTimeMillis()
      //                    ImageHandler.saveToInternalSorage(photo, fileName, Constants.CAPTURE_IMAGE_DIR)
                            var imageURI:Uri = ImageHandler.getImageUri(this,bitmap)
                            CropImage.activity(imageURI)
                                    .start(this);

                        }
                        catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(this@EditImageActivity, "Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ->{
                val result:CropImage.ActivityResult = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    val resultUri:Uri  = result.uri;
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                    val d:Drawable  = BitmapDrawable(resources, bitmap);
                    sticker_view.addSticker(DrawableSticker(d))

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error:Exception = result.error
                    error.printStackTrace()
                    showSnackbar("","",null)
                }
            }


        }


    }


    public fun onAddTextClick(v: View) {
        val alert = AlertDialog.Builder(this)

        val edittext = EditText(this)
        edittext.hint = "" + getString(R.string.enter_text)

        var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        val margin = Util.convertDpToPixel(25F, this)
        lp.setMargins(margin.toInt(), margin.toInt(), margin.toInt(),0);
        edittext.layoutParams = lp;


        alert.setView(edittext)

        alert.setPositiveButton(R.string.add_text, DialogInterface.OnClickListener { dialog, whichButton ->
            val text = edittext.text.toString()

            lastTextSticker = TextSticker(this)
            lastTextSticker.drawable = ContextCompat.getDrawable(applicationContext,
                    R.drawable.sticker_transparent_background)!!
            lastTextSticker.text = text
            lastTextSticker.setTextColor(Color.BLACK)
            lastTextSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER)
            lastTextSticker.resizeText()

            sticker_view.addSticker(lastTextSticker)

        })
        alert.setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, whichButton ->

        })
        alert.show()

    }

    public fun onAddImageClick(v: View) {

        if (!isStorageAndCameraPermissionGranted()) {
            showSnackbar(getString(R.string.runtime_permissions_txt), getString(R.string.enable), View.OnClickListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            })

            return
        }
        val alert = AlertDialog.Builder(this).create()
        val view = layoutInflater.inflate(R.layout.view_popup_pick_image, null)
        alert.setView(view)

        val btnCancel = view.findViewById<ImageButton>(R.id.btnCancel)
        val btnCamera = view.findViewById<ImageButton>(R.id.imgBtnCamera)
        val btnChooseImage = view.findViewById<ImageButton>(R.id.imgBtnChooseImage)
        btnCancel.setOnClickListener(View.OnClickListener {
            alert.dismiss();
        })

        btnCamera.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, IMAGE_CAMERA_CODE)
            alert.dismiss();
        }

        btnChooseImage.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
            alert.dismiss();
        })

        alert.show()


    }

    private fun isStorageAndCameraPermissionGranted(): Boolean {
        val list: ArrayList<String> = arrayListOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return PermissionHelper.isAllPermissionGranted(this, list)
    }

    public fun onPickColorClick(v: View) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle(R.string.select_color)
                .initialColor(R.color.default_color_font)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(10)
                .setOnColorSelectedListener { selectedColor ->
                }
                .setPositiveButton(R.string.ok) { dialog, selectedColor,
                                                  allColors ->
                    //                   changeBackgroundColor(selectedColor)

                    if ( ::lastTextSticker.isInitialized) {
                        lastTextSticker.setTextColor(selectedColor)
                    }
                }
                .setNegativeButton(R.string.cancel) { dialog, which ->

                }
                .build()
                .show()

    }

    fun onFontChange(v:View){
        val d:Drawable  = BitmapDrawable(resources, App.instance.editingImageBitmap);
        sticker_view.addSticker(DrawableSticker(d))

    }
    public fun onTemplateColorClick(v: View) {

        val intent = Intent(this, SubCategoryActivity::class.java)
//        intent.putExtra("categoryID", model.id)
//        intent.putExtra("title", model.name)
        startActivity(Intent(this, SubCategoryActivity::class.java))
    }


    private fun showSnackbar(message: String, txtButton: String, listener: View.OnClickListener?) {
        var snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
        listener?.let{
            txtButton?.let{
                snackbar.setAction(txtButton, listener)
            }
        }
        snackbar.show()

    }


    private fun lockUnlockSticker() {
        if(sticker_view.isLocked){
            btnLock.setImageResource(R.drawable.ic_unlock);
        }else{
            btnLock.setImageResource(R.drawable.ic_lock);
        }
        sticker_view.isLocked = !sticker_view.isLocked
    }



    private fun redoSticker() {
        if(undoList.size <= 0){
            Toast.makeText(this,"can't redo",Toast.LENGTH_SHORT).show()
            return
        }
        val latestSticker:Sticker = undoList.last()
        sticker_view.addSticker(latestSticker)
        undoList.dropLast(1)
    }

    private fun undoSticker() {

        var remove = false
        val latestSticker: Sticker? = sticker_view.currentSticker
        latestSticker?.let {
            remove = sticker_view.removeCurrentSticker()
            if (remove){
                undoList.add(latestSticker)
            }

        }
        if(remove){
            Toast.makeText(this,"can't undo",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onVisibleClick() {

    }


    /* renamed from: E */
    private fun inFromLeftAnimation(): Animation {
        val translateAnimation = TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f)
        translateAnimation.duration = 500
        translateAnimation.interpolator = LinearInterpolator()
        return translateAnimation
    }

    /* renamed from: F */
    private fun inFromRightAnimation(): Animation {
        val translateAnimation = TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f)
        translateAnimation.duration = 500
        translateAnimation.interpolator = LinearInterpolator()
        return translateAnimation
    }


}


