package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.transition.Visibility
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.databinding.DialogAttendFileBinding
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Base64
import java.util.Date
import java.util.Locale


class CreateAttendDocFragment: DialogFragment() {
    private var _binding: DialogAttendFileBinding? = null
    private val binding get() = _binding!!
    private var suff = ""
    private var choseResult: String =""
    private val formater = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    private val startChoseFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data?.data
            choseResult = data.toString()
            val contentResolver = requireContext().contentResolver

            val file2 = File.createTempFile("Image", suff)
            var outPut = FileOutputStream(file2)
            val inputStream = contentResolver.openInputStream(data!!)
//            val byteArray = inputStream!!.readBytes()
//            val byte = Base64.getEncoder().encodeToString(byteArray)
//            var result = Base64.getDecoder().decode(byte)
//            val newStream = ByteArrayInputStream(result)
            val mime = contentResolver.getType(data!!)
            binding.hintText.visibility = VISIBLE
//            binding.scrollParent.setOnTouchListener{ v, event ->
//                binding.pdf1.parent.requestDisallowInterceptTouchEvent(false)
//
//                false
//            }
//            binding.pdf1.setOnTouchListener { v, event ->
//                v!!.parent.requestDisallowInterceptTouchEvent(true)
//
//                false
//            }
            if (mime!!.contains("image")){
                suff =".jpeg"
                binding.image1.setImageBitmap(BitmapFactory.decodeStream(inputStream))
                binding.scrollParent.visibility = VISIBLE
                binding.pdf1.visibility = GONE
            }
            else {
                suff = ".pdf"
                binding.pdf1.fromUri(data)
                    .defaultPage(0)
                    .load()
                binding.scrollParent.visibility = GONE
                binding.pdf1.visibility = VISIBLE
            }


            println("ну или чуть позже: $data")
            inputStream.use { input ->
                outPut.use { out ->
                    input!!.copyTo(out)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAttendFileBinding.inflate(layoutInflater)
        binding.button.setOnClickListener {
            val allowTypes = arrayOf("image/*", "application/pdf")
            val intent = Intent()
                .setType("*/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, allowTypes)
                .setAction(Intent.ACTION_GET_CONTENT)
            startChoseFileLauncher.launch(intent)
        }
        return AlertDialog.Builder(
            requireActivity())
            .setView(binding.root)

            .setTitle(R.string.file_info)
            .setPositiveButton(android.R.string.ok){
                    item: DialogInterface, id: Int ->
                val date1 = binding.dateFirstResult?.text ?: formater.format(Date())
                val date2 = binding.dateSecondResult?.text ?: formater.format(Date())
                val name = binding.fileName.text.toString()
                sendData("filename", choseResult, "suff", suff,"date1",date1.toString(),"date2",date2.toString(), "name", name)
            }
            .setNegativeButton(android.R.string.cancel){
                    item: DialogInterface, id: Int ->

                dismiss()
            }
            .create()

    }
    private fun sendData(keyFileUri: String, fileUri: String, keySuff: String, suff: String, keyDate1: String,date1: String, keyDate2: String, date2: String, keyName: String, name: String){
        val bundle = Bundle()
        bundle.putString(keyFileUri, fileUri)
        bundle.putString(keySuff, suff)
        bundle.putString(keyDate1, date1)
        bundle.putString(keyDate2, date2)
        bundle.putString(keyName, name)
        setFragmentResult(keyFileUri, bundle)
    }

}


//    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
//        val schema = uri.scheme
//        var result =""
//        if ("content".equals(schema)){
//            println("тут все верно")
//            getImageRealPath(context, uri, null)
//        }
//        return schema
//    }
//    fun getImageRealPath(context: Context, uri: Uri, whereClause: String?){
//        var res =""
//        var cursor: Cursor? = null
//        cursor = context.contentResolver.query(uri, null, whereClause, null, null)
//        if (cursor != null) {
//            val move = cursor.moveToFirst()
////            println("Ну как: $move")
//            var columnName = MediaStore.Images.Media.DATA;
//            when (uri) {
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI -> {
//                    columnName = MediaStore.Images.Media.DATA;
//                }
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI -> {
//                    columnName = MediaStore.Audio.Media.DATA;
//                }
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI -> {
//                    columnName = MediaStore.Video.Media.DATA;
//                }
//            }
//            var i = cursor.getColumnIndex(columnName)
//            res = cursor.getColumnName(i)
//            println("Индекс $res")
//        }
//
//    }