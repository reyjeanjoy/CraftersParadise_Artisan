package com.craftersparadise.artisans

import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.craftersparadise.artisans.databinding.ActivityCustomizeBinding

class CustomizeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomizeBinding
    private var baseBitmap: Bitmap? = null
    private var drawingBitmap: Bitmap? = null
    private var imageBitmap: Bitmap? = null
    private var drawMode = false
    private var eraseMode = false
    private var textMode = false
    private var lastX = 0f
    private var lastY = 0f
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private val eraserPaint = Paint().apply {
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        style = Paint.Style.STROKE
        strokeWidth = 50f
    }
    private val texts = mutableListOf<TextObject>()
    private var activeText: TextObject? = null
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { addImageToCanvas(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        binding.backButton.setOnClickListener { finish() }
        binding.drawButton.setOnClickListener {
            changeButtonColor(binding.drawButton)
            startDrawing()
        }
        binding.textButton.setOnClickListener {
            changeButtonColor(binding.textButton)
            addTextToCanvas()
        }
        binding.eraseButton.setOnClickListener {
            changeButtonColor(binding.eraseButton)
            startErasing()
        }
        binding.addImageButton.setOnClickListener {
            changeButtonColor(binding.addImageButton)
            selectImage()
        }

        initializeTouchHandling()
    }

    private fun changeButtonColor(button: View) {
        binding.drawButton.setBackgroundResource(R.drawable.circle_background)
        binding.textButton.setBackgroundResource(R.drawable.circle_background)
        binding.eraseButton.setBackgroundResource(R.drawable.circle_background)
        binding.addImageButton.setBackgroundResource(R.drawable.circle_background)
        button.setBackgroundResource(R.drawable.selected_circle_background)
    }

    private fun startDrawing() {
        drawMode = true
        eraseMode = false
        textMode = false
        initializeDrawingLayer()

        binding.customCanvas.setOnTouchListener { _, event ->
            if (drawMode) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val canvas = Canvas(drawingBitmap!!)
                        canvas.drawLine(lastX, lastY, event.x, event.y, paint)
                        lastX = event.x
                        lastY = event.y
                        mergeLayers()
                    }
                }
            }
            true
        }
    }

    private fun initializeDrawingLayer() {
        if (baseBitmap == null) {
            baseBitmap = Bitmap.createBitmap(
                binding.customCanvas.width,
                binding.customCanvas.height,
                Bitmap.Config.ARGB_8888
            )
        }
        if (drawingBitmap == null) {
            drawingBitmap = Bitmap.createBitmap(
                binding.customCanvas.width,
                binding.customCanvas.height,
                Bitmap.Config.ARGB_8888
            )
        }
        mergeLayers()
    }

    private fun startErasing() {
        drawMode = false
        eraseMode = true
        textMode = false
        initializeDrawingLayer()

        binding.customCanvas.setOnTouchListener { _, event ->
            if (eraseMode) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val canvas = Canvas(drawingBitmap!!)
                        canvas.drawLine(lastX, lastY, event.x, event.y, eraserPaint)
                        lastX = event.x
                        lastY = event.y
                        mergeLayers()
                    }
                }
            }
            true
        }
    }

    private fun addTextToCanvas() {
        val editText = EditText(this)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add Text")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                val text = editText.text.toString()
                val newText = TextObject(text, 100f, 100f, 1f)
                texts.add(newText)
                activeText = newText
                textMode = true
                drawText()
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun drawText() {
        initializeDrawingLayer()

        binding.customCanvas.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)

            if (textMode) {
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        activeText?.let { text ->
                            if (text.isMoving) {
                                text.x += event.x - lastX
                                text.y += event.y - lastY
                                lastX = event.x
                                lastY = event.y
                                mergeLayers()
                            }
                        }
                    }
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                        activeText?.let { text ->
                            if (isTouchedText(event.x, event.y, text)) {
                                text.isMoving = true
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        activeText?.let { text -> text.isMoving = false }
                    }
                }
            }
            true
        }
        mergeLayers()
    }

    private fun isTouchedText(x: Float, y: Float, text: TextObject): Boolean {
        val textPaint = Paint().apply {
            textSize = 80f * text.scale
            isAntiAlias = true
        }
        val bounds = Rect()
        textPaint.getTextBounds(text.text, 0, text.text.length, bounds)
        return x >= text.x && x <= text.x + bounds.width() && y >= text.y - bounds.height() && y <= text.y
    }

    private fun selectImage() {
        selectImageLauncher.launch("image/*")
    }

    private fun addImageToCanvas(uri: Uri) {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        initializeDrawingLayer()
        imageBitmap = bitmap
        mergeLayers()
    }

    private fun mergeLayers() {
        val mergedBitmap = Bitmap.createBitmap(
            binding.customCanvas.width,
            binding.customCanvas.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(mergedBitmap)
        baseBitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }
        drawingBitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }

        texts.forEach { text ->
            val textPaint = Paint().apply {
                color = Color.BLACK
                textSize = 80f * text.scale
                isAntiAlias = true
            }
            canvas.drawText(text.text, text.x, text.y, textPaint)
        }

        binding.customCanvas.setImageBitmap(mergedBitmap)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            activeText?.let {
                it.scale *= detector.scaleFactor
                it.scale = it.scale.coerceIn(0.5f, 3.0f)
                mergeLayers()
            }
            return true
        }
    }

    private fun initializeTouchHandling() {
        binding.customCanvas.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    data class TextObject(var text: String, var x: Float, var y: Float, var scale: Float, var isMoving: Boolean = false)
}
