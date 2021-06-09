package com.dicoding.kabar.ui.classify

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.kabar.R
import com.dicoding.kabar.databinding.FragmentClassifyBinding
import com.dicoding.kabar.utils.ApiKey
import com.dicoding.kabar.utils.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class ClassifyFragment : Fragment() {

    private lateinit var binding: FragmentClassifyBinding
    private  var path: Uri? = null
    private var bitmap: Bitmap? = null
    private var fragmentClassifyViewModel = ClassifyViewModel()
    private val REQUEST_CODE_GALLERY = 100
    private val REQUEST_IMAGE_CAPTURE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentClassifyBinding.inflate(layoutInflater, container, false)

        fragmentClassifyViewModel = activity?.let {
            ViewModelProvider(it, ViewModelProvider.NewInstanceFactory())
                .get(ClassifyViewModel::class.java)
        }!!
        Log.d("URIWOIIII", fragmentClassifyViewModel.getImg().toString())

        if(fragmentClassifyViewModel.getImg() != null){
            Log.d("URIMASUKKKK", fragmentClassifyViewModel.getImg().toString())
            Glide.with(this)
                .load(fragmentClassifyViewModel.getImg())
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_baseline_loading)
                        .error(R.drawable.ic_baseline_broken_image)
                )
                .into(binding.classifyImage)
            showImage()
        }else{
            showNoImage()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_use_camera, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        actionTakePicture(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GALLERY){
            path = data?.data
            val byteImg: InputStream? = path?.let {
                context?.getContentResolver()?.openInputStream(
                    it
                )
            }
            if (byteImg != null) {
                getBytes(byteImg)?.let { uploadImage(it) }
            }
            bitmap = getBitmap(context?.contentResolver, path)
            fragmentClassifyViewModel.setImg(bitmap)

        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            path = data?.data
            bitmap = getBitmap(context?.contentResolver, path)
            fragmentClassifyViewModel.setImg(bitmap)
        }
    }

    private fun actionTakePicture(selectedMode: Int) {
        when (selectedMode) {
            R.id.album -> {
                openGalleryForImage()
            }
            R.id.camera -> {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.flags =
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun showNoImage(){
        binding.classifyEmptyImage.visibility = View.VISIBLE
        binding.textNoImage.visibility = View.VISIBLE
        binding.classifyImage.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.textDetectedItem.visibility = View.GONE
        binding.btnSearchRecommendation.visibility = View.GONE
    }

    private fun showImage(){
        binding.classifyEmptyImage.visibility = View.GONE
        binding.textNoImage.visibility = View.GONE
        binding.classifyImage.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.textDetectedItem.visibility = View.VISIBLE
        binding.btnSearchRecommendation.visibility = View.VISIBLE
    }

    fun uploadImage(imageBytes: ByteArray){

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiKey.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitInterface: ApiService = retrofit.create(ApiService::class.java)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
        val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
        val call = retrofitInterface.uploadImage(body)
        call.enqueue(object : Callback<com.dicoding.kabar.utils.Response> {
            override fun onResponse(
                call: Call<com.dicoding.kabar.utils.Response>,
                response: Response<com.dicoding.kabar.utils.Response>
            ) {
                if (response.isSuccessful) {
                    val jsonObject = JSONObject(response.body().toString())
                    val message = jsonObject.getString("status")
                    Log.d("Success", message)
                } else {
                    val errorMesaage = response.errorBody()!!.string()
                    Log.d("Success", errorMesaage)
                }

            }

            override fun onFailure(call: Call<com.dicoding.kabar.utils.Response>, t: Throwable) {
                val message = retrofitInterface.getResponse("status")
                Log.d("Success", message.toString())
            }

        })
    }

    @Throws(IOException::class)
    fun getBytes(`is`: InputStream): ByteArray? {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 1024
        val buff = ByteArray(buffSize)
        var len = 0
        while (`is`.read(buff).also { len = it } != -1) {
            byteBuff.write(buff, 0, len)
        }
        return byteBuff.toByteArray()
    }

}