package com.core.funyface
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import com.google.android.gms.vision.face.Landmark.NOSE_BASE


class MainActivity : AppCompatActivity() {

    //region Variables
    lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var eyePatchBitmap: Bitmap
    //endregion

    //region Activity Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialSetup()
    }
    //endregion

    //region Initial Setup
    fun initialSetup() {

        imageView = findViewById(R.id.imageView)
        button = findViewById<Button>(R.id.button)

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inMutable = true
        eyePatchBitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.mustache,
            bitmapOptions)

        button.setOnClickListener {
            processImageAndSenditForDetection()
        }

    }

   fun processImageAndSenditForDetection() {

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inMutable = true
        val myBitmap = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.sample_image,
            bitmapOptions)

        detectFacialLandmarks(myBitmap)

   }
    //endregion

    //region Face Detection
    fun detectFacialLandmarks(bitMap:Bitmap) {

        // Get a Paint instance
        val myRectPaint = Paint()
        myRectPaint.strokeWidth = 5F
        myRectPaint.color = Color.RED
        myRectPaint.style = Paint.Style.STROKE

        // Create a canvas using the dimensions from the image's bitmap
        val tempBitmap = Bitmap.createBitmap(bitMap.width, bitMap.height, Bitmap.Config.RGB_565)
        val tempCanvas = Canvas(tempBitmap)
        tempCanvas.drawBitmap(bitMap, 0F, 0F, null)

        // Create a FaceDetector
        val faceDetector = FaceDetector.Builder(applicationContext).setTrackingEnabled(false)
            .build()
        if (!faceDetector.isOperational) {
            AlertDialog.Builder(this)
                .setMessage("Could not set up the face detector!")
                .show()
            return
        }

        // Detect the faces
        val frame = Frame.Builder().setBitmap(bitMap).build()
        val faces = faceDetector.detect(frame)

        // Mark out the identified face
        for (i in 0 until faces.size()) {

            val thisFace = faces.valueAt(i)
            val left = thisFace.position.x
            val top = thisFace.position.y
            val right = left + thisFace.width
            val bottom = top + thisFace.height
            tempCanvas.drawRoundRect(RectF(left, top, right, bottom), 2F, 2F, myRectPaint)

            for (landmark in thisFace.landmarks) {
                val x = landmark.position.x
                val y = landmark.position.y

                when (landmark.type) {
                    NOSE_BASE -> {
                        val scaledWidth =
                            eyePatchBitmap.getScaledWidth(tempCanvas)
                        val scaledHeight =
                            eyePatchBitmap.getScaledHeight(tempCanvas)
                        tempCanvas.drawBitmap(eyePatchBitmap,
                            x - scaledWidth / 2,
                            y - scaledHeight / 2,
                            null)
                    }
                }
            }

        }


        imageView.setImageDrawable(BitmapDrawable(resources, tempBitmap))

        // Release the FaceDetector
        faceDetector.release()

    }
    //endregion

}