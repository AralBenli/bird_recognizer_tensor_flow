package com.example.birdRecognizer.app.ui.pages.home


import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.app.ui.base.BaseFragment
import com.example.birdRecognizer.databinding.FragmentHomeBinding
import com.example.birdRecognizer.ml.BirdsModel
import com.google.firebase.auth.FirebaseAuth
import org.tensorflow.lite.support.image.TensorImage
import java.io.IOException

private const val REQUESTED_CODE = 123

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth
    // When you want to call content resolver to fragment , you need to initialize it , in override onAttach
    private lateinit var resolver: ContentResolver

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)


    override fun initViews() {
        firebaseAuth = FirebaseAuth.getInstance()
        tasks()
        logout()
    }

    private fun logout() {
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.homeToLogin)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resolver = requireActivity().contentResolver
    }

    private fun tasks() {
        binding.btnCaptureImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                takePicturePreview.launch(null)
            } else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }

        }
        binding.btnLoadImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/jpeg", "image/png")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onResult.launch(intent)
            } else {
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }

        binding.outPutDynamic.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=${binding.outPutDynamic.text}")
            )
            startActivity(intent)
        }

        //to download image when longPress on image view
        binding.imageView.setOnLongClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return@setOnLongClickListener true

        }
    }

    // request camera permission
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                showCustomToast(
                    Status.Failed,
                    "Permission Denied, Try Again",
                    this@HomeFragment
                )
            }
        }

    //launch camera and take picture
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.imageView.setImageBitmap(bitmap)
                outputGenerator(bitmap)
            }
        }

    //to get image from gallery
    private val onResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("TAG", "This is the result: ${result.data} ${result.resultCode}")
            onResultReceived(REQUESTED_CODE, result)
        }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            REQUESTED_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i("TAG", "OnResultReceived : $uri")
                        val bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri))
                        binding.imageView.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                } else {
                    Log.e("TAG", "onActivityResult : error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        //declaring tensor flow lite model variable
        val birdsModel = BirdsModel.newInstance(requireContext())

        //converting bitmap into tensor flow image
        val newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val tfImage = TensorImage.fromBitmap(newBitmap)

        // process the image using trained model and sort it in descending order
        val outputs = birdsModel.process(tfImage)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score }
            }

        //getting result having high probability
        val highProbabilityOutput = outputs[0]

        //setting output text
        binding.outPutDynamic.text = highProbabilityOutput.label
        Log.i("TAG ", "outputGenerator : $highProbabilityOutput")

    }

    //to download image
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                AlertDialog.Builder(requireContext()).setTitle("Download Image?")
                    .setMessage("Do you want to download this image to your device?")
                    .setPositiveButton("Yes") { _, _ ->
                        val drawable: BitmapDrawable = binding.imageView.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        downloadImage(bitmap)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please allow permission to download",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    // fun that takes a bitmap and store to user's device
    private fun downloadImage(mBitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "Birds_Images" + System.currentTimeMillis() / 1000
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        }
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        if (uri != null) {
            resolver.insert(uri, contentValues)?.also {
                resolver.openOutputStream(it).use { outputStream ->
                    if (!mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                        throw IOException("Couldn't save the bitmap")
                    } else {
                        Toast.makeText(requireContext(), "Image Saved", Toast.LENGTH_LONG).show()

                    }
                }
                return it
            }
        }
        return null
    }

}






