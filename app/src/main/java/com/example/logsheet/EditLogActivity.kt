package com.example.logsheet

import android.content.ContentValues
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileOutputStream
import java.io.IOException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.OutputStream


class EditLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_log)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id=intent.getIntExtra("id",0)
        //val ed=EditRecords(id)
        //ed.edit()
        //val tvId=findViewById<TextView>(R.id.tvId)
        //val btnViewPDF=findViewById<Button>(R.id.btnViewPDF)
       // val imageView = findViewById<ImageView>(R.id.imageView)
        //how data for editing
        val editTextName = findViewById<TextView>(R.id.editTextName)
        val editTextLogNumber = findViewById<TextView>(R.id.editTextLogNumber)
        val editTextDate = findViewById<TextView>(R.id.editTextDate)
        val editTextStartTime = findViewById<TextView>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<TextView>(R.id.editTextEndTime)
        val editTextLocation = findViewById<TextView>(R.id.editTextLocation)
        val editTextBattery = findViewById<TextView>(R.id.editTextBattery)
        val editTextTotalFlightTime = findViewById<TextView>(R.id.editTextTotalFlightTime)
        val editTextComments = findViewById<TextView>(R.id.editTextComments)
        val checkBoxPreflight = findViewById<TextView>(R.id.checkBoxPreflight)
        val checkBoxPackaging = findViewById<TextView>(R.id.checkBoxPackaging)
        val checkBoxHomeBase = findViewById<TextView>(R.id.checkBoxHomeBase)

        val db=DatabaseHelper(this)
        val data = db.readData(id)
        var user:User=data[0]
        if (data.isNotEmpty()) {
            user = data[0]
            editTextName.text = user.name
            editTextLogNumber.text = user.logNumber.toString()
            editTextDate.text = user.date
            editTextStartTime.text = user.startTime
            editTextEndTime.text = user.endTime
            editTextLocation.text = user.location
            editTextBattery.text = user.battery
            editTextTotalFlightTime.text = user.totalFlightTime
            editTextComments.text = user.comments

            if (user.preflight=="true") {
                checkBoxPreflight.text = "Checked"
            } else {
                checkBoxPreflight.text = "Not Checked"
            }
            if (user.packaging=="true") {
                checkBoxPackaging.text = "Checked"
            } else {
                checkBoxPackaging.text = "Not Checked"
            }
            if (user.homeBase=="true") {
                checkBoxHomeBase.text = "Checked"
            } else {
                checkBoxHomeBase.text = "Not Checked"
            }


        }



        fun savePDFToDownloads(context: Context, fileName: String = "FlightLog.pdf"+System.currentTimeMillis()): Uri? {
            return try {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas: Canvas = page.canvas
                val paint = Paint()

                // Draw content on PDF
                canvas.drawRGB(255, 255, 255)
                paint.textSize = 36f
                paint.isFakeBoldText = true
                canvas.drawText("Flight Log Sheet", 150f, 100f, paint)

                paint.textSize = 20f
                paint.isFakeBoldText = false
                canvas.drawText("Log Number: ${user.logNumber} Sheet No: ${user.id}", 50f, 150f, paint)
                canvas.drawText("Mission information: Date : ${user.date}  Operation", 50f, 200f, paint)
                canvas.drawText("Battery S/NO: ${user.battery}. Mission Location: ${user.location}", 50f, 250f, paint)


                /////table start

                val textPaint = Paint().apply {
                    textSize = 18f
                    color = android.graphics.Color.BLACK
                }

                val linePaint = Paint().apply {
                    style = Paint.Style.STROKE
                    strokeWidth = 2f
                    color = android.graphics.Color.BLACK
                }

                // Table setup
                val startX = 50f
                val startY = 300f
                val cellWidth = 170f
                val cellHeight = 50f

                // Draw headers
                val headers = arrayOf("Task", "Comments", "Operator")
                for (i in headers.indices) {
                    val x = startX + (i * cellWidth)
                    canvas.drawRect(x, startY, x + cellWidth, startY + cellHeight, linePaint)
                    canvas.drawText(headers[i], x + 10, startY + cellHeight / 2 + 6, textPaint)
                }

                // Draw rows with hardcoded data
                val data = arrayOf(
                    arrayOf("Home base ops", "${if (user.homeBase=="true") "Yes" else "No"}", "${user.name}"),
                    arrayOf("Packaging", "${if (user.packaging=="true") "Checked" else "Not Checked"}", "${user.name}"),
                    arrayOf("Pre-flight prep", "${if (user.preflight=="true") "Checked" else "Not Checked"}", "${user.name}"),
                    arrayOf("Start mission time", "${user.startTime} HRS", "${user.name}"),
                    arrayOf("End mission time", "${user.endTime} HRS", "${user.name}"),
                    arrayOf("Total flight time ", "${user.totalFlightTime} MINUTES", "${user.name}")
                )

                // Draw data rows
                for (row in 0 until 6) {
                    for (col in 0..2) {
                        val x = startX + (col * cellWidth)
                        val y = startY + ((row + 1) * cellHeight)
                        canvas.drawRect(x, y, x + cellWidth, y + cellHeight, linePaint)
                        canvas.drawText(data[row][col], x + 10, y + cellHeight / 2 + 6, textPaint)
                    }
                }
                if (user.comments!=""){
                    if (user.comments.toString().length>0&&user.comments.toString().length<52){
                        canvas.drawText("Comments: "+user.comments.toString().substring(0,user.comments.toString().length), 50f, 690f, paint)

                    }else if (user.comments.toString().length>50&&user.comments.toString().length<122){
                        canvas.drawText("Comments: "+user.comments.toString().substring(0,50), 50f, 690f, paint)
                        canvas.drawText(""+user.comments.toString().substring(51,user.comments.toString().length), 50f, 740f, paint)
                        //canvas.drawText(""+user.comments.toString().substring(53,124), 50f, 740f, paint)
                        //canvas.drawText(""+user.comments.toString().substring(125,187), 50f, 790f, paint)

                    }else if (user.comments.toString().length>122&&user.comments.toString().length<185){
                        canvas.drawText("Comments: "+user.comments.toString().substring(0,50), 50f, 690f, paint)
                        canvas.drawText(""+user.comments.toString().substring(51,122), 50f, 740f, paint)
                        canvas.drawText(""+user.comments.toString().substring(123,user.comments.toString().length), 50f, 790f, paint)

                    }
                }

                ////table end
                paint.isFakeBoldText = true
                canvas.drawText("System generated", 50f, 820f, paint)

                pdfDocument.finishPage(page)

                // Define the output stream & save location
                var fileUri: Uri? = null
                val outputStream: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }
                    val contentResolver = context.contentResolver
                    fileUri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                    fileUri?.let { contentResolver.openOutputStream(it) }
                } else {
                    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(downloadsDir, fileName)
                    fileUri = Uri.fromFile(file)
                    FileOutputStream(file)
                }

                outputStream?.use { pdfDocument.writeTo(it) }
                pdfDocument.close()

                Toast.makeText(context, "PDF saved to Downloads!", Toast.LENGTH_SHORT).show()
                fileUri
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to save PDF: ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }



        //show pdf
        fun showPdf(file: File, imageView: ImageView) {
            try {
                val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                val pdfRenderer = PdfRenderer(fileDescriptor)
                val page = pdfRenderer.openPage(0)

                val bitmap = Bitmap.createBitmap(
                    page.width,
                    page.height,
                    Bitmap.Config.ARGB_8888
                )

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                imageView.setImageBitmap(bitmap)

                page.close()
                pdfRenderer.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        fun sharePDF(context: Context, pdfUri: Uri?) {
            if (pdfUri != null) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_STREAM, pdfUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(intent, "Share PDF via"))
            } else {
                Toast.makeText(context, "Error: PDF file not found!", Toast.LENGTH_SHORT).show()
            }
        }

        val btnSharePDF = findViewById<Button>(R.id.btnSharePDF)
        btnSharePDF.setOnClickListener {
            val uri = savePDFToDownloads(this)
            sharePDF(this, uri)
        }
    }
}