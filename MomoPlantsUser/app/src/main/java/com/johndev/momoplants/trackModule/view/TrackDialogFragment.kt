package com.johndev.momoplants.trackModule.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.databinding.FragmentDialogTrackBinding
import com.johndev.momoplants.mainModule.view.MainActivity

class TrackDialogFragment : DialogFragment(), DialogInterface.OnShowListener {

    private var binding: FragmentDialogTrackBinding? = null
    private var positiveButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let { activity ->
            binding = FragmentDialogTrackBinding.inflate(LayoutInflater.from(context))
            binding?.let {
                val builder = AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.traking_dialog_title))
                    .setPositiveButton(getString(R.string.add_dialog_btn_success), null)
                    .setView(it.root)
                val dialog = builder.create()
                dialog.setOnShowListener(this)
                return dialog
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialog: DialogInterface?) {
        initTrack()
        val dialog = dialog as? AlertDialog
        dialog?.let {
            positiveButton = it.getButton(Dialog.BUTTON_POSITIVE)
            positiveButton?.setOnClickListener { dismiss() }
        }
    }

    private fun initTrack() {
        MainActivity.trackViewModel.onGetOrder(context = requireContext())
        MainActivity.trackViewModel.getOrderInRealtime()
    }

    private fun updateIU(orderEntity: OrderEntity) {
        binding?.let {
            it.progressBar.progress = orderEntity.status * (100 / 3) - 15
            it.cbOrdered.isChecked = orderEntity.status > 0
            it.cbPreparing.isChecked = orderEntity.status > 1
            it.cbSend.isChecked = orderEntity.status > 2
            it.cbDelivered.isChecked = orderEntity.status > 3
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        MainActivity.trackViewModel.orderEntity.observe(viewLifecycleOwner) {
            it?.let { orderEntity ->
                updateIU(orderEntity)
            }
        }
    }
}