package by.belstu.narkevich.contact_cards.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import by.belstu.narkevich.contact_cards.R
import by.belstu.narkevich.contact_cards.databinding.FragmentAddEditBinding
import by.belstu.narkevich.contact_cards.helpers.FileService
import by.belstu.narkevich.contact_cards.helpers.ImageService
import by.belstu.narkevich.contact_cards.models.ContactCard
import by.belstu.narkevich.contact_cards.viewmodels.CardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class AddEditFragment : Fragment() {
    private lateinit var _binding: FragmentAddEditBinding
    private lateinit var _viewModel: CardViewModel

    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    var onMovieAddOrEdit: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_fast_start)
        exitTransition = inflater.inflateTransition(R.transition.slide_fast_start)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(requireActivity())[CardViewModel::class.java]

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel.bindObservables()
        _binding.cardViewModel = _viewModel

        val isEdit = _viewModel.isEditMode.get() ?: false

        if(isEdit) {
            ImageService.loadImageFromStorage(_viewModel.image.get()!!, _binding.photo)
        }

        _binding.photo.setOnClickListener {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }

        _binding.buttonToSave.setOnClickListener {
            val isEdit = _viewModel.isEditMode.get() ?: false

            val newCard = ContactCard(
                null,
                _viewModel.name.get()!!,
                _viewModel.address.get()!!,
                _viewModel.phoneNumber.get()!!,
                _viewModel.website.get()!!,
            )

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(requireContext(), selectedImageUri)
                newCard.Image = ImageService.saveImageToInternalStorageAndGetImagePath(requireContext(), selectedImageBitmap!!, imageName)
            }

            CoroutineScope(Dispatchers.IO).launch {
                if(isEdit) {
                    newCard.Id = _viewModel.contactCard.value?.Id
                    _viewModel.update(newCard)
                } else {
                    _viewModel.add(newCard)
                }
            }

            onMovieAddOrEdit?.invoke()
        }


        _viewModel.bindObservables()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data?.data
                try {
                    selectedImageBitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (null != selectedImageBitmap) {
                    _binding.photo.setImageBitmap(selectedImageBitmap)
                }
            }
        }
    }
}