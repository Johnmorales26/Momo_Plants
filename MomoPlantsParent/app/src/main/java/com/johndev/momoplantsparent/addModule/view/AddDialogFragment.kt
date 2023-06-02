package com.johndev.momoplantsparent.addModule.view

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.johndev.momoplantsparent.common.utils.EventPost
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.common.entities.PlantEntity
import com.johndev.momoplantsparent.common.utils.editable
import com.johndev.momoplantsparent.databinding.FragmentDialogAddBinding
import com.johndev.momoplantsparent.mainModule.view.MainActivity.Companion.homeViewModel

class AddDialogFragment : DialogFragment(), DialogInterface.OnShowListener {

    private var binding: FragmentDialogAddBinding? = null
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null
    private var plant: PlantEntity? = null
    private var photoSelectedUri: Uri? = null
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = it.data?.data
                binding?.let { binding ->
                    Glide.with(this)
                        .load(photoSelectedUri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.drawable.ic_broken_image)
                        .placeholder(R.drawable.ic_access_time)
                        .into(binding.imgPlantPreview)
                }
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let { activity ->
            binding = FragmentDialogAddBinding.inflate(LayoutInflater.from(context))
            binding?.let {
                val builder = AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.add_dialog_title))
                    .setPositiveButton(getString(R.string.add_dialog_btn_success), null)
                    .setNegativeButton(getString(R.string.add_dialog_btn_cancel), null)
                    .setView(it.root)
                val dialog = builder.create()
                dialog.setOnShowListener(this)
                return dialog
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialog: DialogInterface?) {
        initPlant()
        configButtons()
        val dialog = dialog as? AlertDialog
        dialog?.let {
            positiveButton = it.getButton(Dialog.BUTTON_POSITIVE)
            negativeButton = it.getButton(Dialog.BUTTON_NEGATIVE)
            positiveButton?.setOnClickListener {
                binding?.progressBar?.visibility = VISIBLE
                homeViewModel.onUploadImage(
                    plantId = plant?.plantId,
                    photoSelectedUri = photoSelectedUri,
                    context = requireContext(),
                    callback = { eventPost ->
                        if (eventPost.isSuccess) collectData(eventPost)
                    },
                    onProgressListener = { progress ->
                        it.run {
                            binding?.progressBar?.progress = progress
                            binding?.tvProgress?.text = String.format("%s%%", progress)
                        }
                    })
                negativeButton?.setOnClickListener { dismiss() }
            }
        }
    }

        private fun configButtons() {
            binding?.let {
                it.ibPlant.setOnClickListener {
                    openGallery()
                }
            }
        }

        private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        private fun initPlant() {
            plant = homeViewModel.getPlantSelected()
            plant?.let { plant ->
                binding?.let {
                    it.etName.text = plant.name.toString().editable()
                    it.etDescription.text = plant.description.toString().editable()
                    it.etOrigin.text = plant.origin.toString().editable()
                    it.etWeather.text = plant.weather.toString().editable()
                    it.etFormat.text = plant.format.toString().editable()
                    it.etQuantity.text = plant.quantity.toString().editable()
                    it.etPrice.text = plant.price.toString().editable()
                    Glide.with(this)
                        .load(plant.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.drawable.ic_broken_image)
                        .placeholder(R.drawable.ic_access_time)
                        .into(it.imgPlantPreview)
                }
            }
        }

        private fun collectData(eventPost: EventPost) {
            binding?.let {
                if (plant == null) {
                    val plantEntity = PlantEntity(
                        name = it.etName.text.toString().trim(),
                        description = it.etDescription.text.toString().trim(),
                        imageUrl = eventPost.photoUrl,
                        origin = it.etOrigin.text.toString().trim(),
                        weather = it.etWeather.text.toString().trim(),
                        format = it.etFormat.text.toString().trim(),
                        price = it.etPrice.text.toString().toDouble(),
                        quantity = it.etQuantity.text.toString().toInt(),
                    )
                    homeViewModel.onSave(plantEntity, context, eventPost.documentId!!) {
                        binding?.progressBar?.visibility = GONE
                        dismiss()
                    }
                } else {
                    plant?.apply {
                        name = it.etName.text.toString().trim()
                        description = it.etDescription.text.toString().trim()
                        imageUrl = eventPost.photoUrl
                        origin = it.etOrigin.text.toString().trim()
                        weather = it.etWeather.text.toString().trim()
                        format = it.etFormat.text.toString().trim()
                        price = it.etPrice.text.toString().toDouble()
                        quantity = it.etQuantity.text.toString().toInt()
                        homeViewModel.onUpdate(this, context) {
                            binding?.progressBar?.visibility = GONE
                            dismiss()
                        }
                    }
                }
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }

    }