package ru.kondrashen.attendanciescoutapp.Presentation.fragments

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.kondrashen.attendanciescoutapp.Domain.MainViewModel
import ru.kondrashen.attendanciescoutapp.databinding.ChosedStudentInfoBinding
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddStudFile
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.net.URL
import java.util.Date
import java.util.Locale

class SecondFragmentAdmin: Fragment() {
    private var _binding: ChosedStudentInfoBinding? = null
    private lateinit var student: Student
    private lateinit var curgroup: Group
    private val formater = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT)
    private lateinit var file: File
    private val dataModel: MainViewModel by viewModels()
    private val binding get() = _binding!!


    private val startChoseFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data?.data
            println(it.data)
            val contentResolver = requireContext().contentResolver
            val mime = contentResolver.getType(data!!)
            if (mime!!.contains("image"))
                println("Тип image $mime")
            else if (mime.contains("pdf"))
                println("Тип pdf $mime")
            val inputStream = contentResolver.openInputStream(data)
            println(inputStream)
            val result = inputStream!!.bufferedReader()
            println(result)
//            val resultbites = Base64.encode(inputStream, Base64.DEFAULT)
            binding.image1.setImageBitmap(BitmapFactory.decodeStream(inputStream))
//            var uriSchema = data.scheme
//            println("Схема $uriSchema")
//            if("content".equals(uriSchema)) {
//                println("Схема $uriSchema")
//                var cursor = contentResolver.query(data,null, null, null, null)
//                if (cursor != null){
//                    val movetoFirst = cursor.moveToFirst()
//                    var columnName = ""
//                    if (movetoFirst){
//                        columnName = MediaStore.Images.Media.DATA
//                    }
//                    val index = cursor.getColumnIndex(columnName)
//                    var result = cursor.getString(index)
//                    println("Исходный файл$result")
//                }
//            }
            println("Путь к файлу: ${data}")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChosedStudentInfoBinding.inflate(inflater,container,false)
        updateUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            updateInfo.setOnClickListener {
                Toast.makeText(context, "Успешно обновлено", Toast.LENGTH_SHORT).show()
            }
            deleteInfo.setOnClickListener {
                Toast.makeText(context, "Успешно удалено", Toast.LENGTH_SHORT).show()
            }
        }
        binding.sendData.setOnClickListener {
            val allowTypes = arrayOf("image/*", "application/pdf")
            val intent = Intent()
                .setType("*/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, allowTypes)
                .setAction(Intent.ACTION_GET_CONTENT)
            val manager =parentFragmentManager
            val dialog = CreateAttendDocFragment()
            dialog.show(manager, "choseFile")
            manager.setFragmentResultListener("filename",viewLifecycleOwner){
                    requestKey, bundle ->
                val postedString = bundle.getString(requestKey).toString()
                val postedSuff = bundle.getString("suff").toString()
                val postedName = bundle.getString("name").toString()
                val postedDate1 = bundle.getString("date1").toString()
                val postedDate2 = bundle.getString("date2").toString()
                val uri = Uri.parse(postedString)
                val contentResolver = requireContext().contentResolver
                val inputStream = contentResolver.openInputStream(uri)
//                val byteArray = inputStream!!.readBytes()
//                val resultB = Base64.encodeToString(byteArray, Base64.DEFAULT)
                val body = RequestBody.create(
                    MediaType.parse("application/octet"),
                    inputStream!!.readBytes()
                )
                val date = formater.format(Date())
                val fileName = "${student.FIO}$date$postedSuff"
                val resName = RequestBody.create(MultipartBody.FORM, postedName)
                val resDate1 = RequestBody.create(MultipartBody.FORM, postedDate1)
                val resDate2 = RequestBody.create(MultipartBody.FORM, postedDate2)
                val addStudFile =  MultipartBody.Part.createFormData("file", fileName, body)
                println("Инфа по файлу: $addStudFile")
                if (postedSuff == ".jpeg")
                    binding.image1.setImageBitmap(BitmapFactory.decodeStream(inputStream))
                dataModel.postDocument(addStudFile, resName, resDate1, resDate2, student.id)
                Toast.makeText(context, "Документ  '$postedName' успешно отправлен на сервер", Toast.LENGTH_LONG).show()
            }

//                startChoseFileLauncher.launch(intent)
        }

    }
    private fun updateUI(){
        val studId = arguments?.getInt("studId")
        val attendNum = arguments?.getInt("attendSum")
        dataModel.getStudentInfo(studId!!).observe(requireActivity()){
            student = it.student
            println(it.student)
            curgroup = it.group
            binding.apply {
                editFIO.setText(student.FIO)
                editPhone.setText(student.phone)
                editEmail.setText(student.email)
                editGroup.setText(curgroup.name)
                editYear.setText(curgroup.year.toString())
                textAttendance.text = attendNum.toString()
            }
        }
    }


}