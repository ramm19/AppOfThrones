package com.example.amenjivar.appofthrones

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import java.io.Serializable
import kotlinx.android.synthetic.main.dialog_house.view.*

class HouseDialog: DialogFragment() {

    companion object {
        fun newInstance(house: House): HouseDialog{
            val arguments = Bundle()
            arguments.putSerializable("key_house", house as Serializable)

            val dialog = HouseDialog()
            dialog.arguments = arguments

            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_house, null, false)
        val house = arguments?.getSerializable("key_house") as House

        with(house) {
            dialogView.labelName.text = name
            dialogView.labelRegion.text = region
            dialogView.labelWords.text = words
            dialogView.layoutDialog.background = ContextCompat.getDrawable(context!!, House.getBaseColor(name))
        }

        Picasso.get()
                .load(house.img)
                .into(dialogView.imgHouse)

        return AlertDialog.Builder(context!!)
                .setView(dialogView)
                .setPositiveButton(R.string.label_accept, { _,_ -> dismiss()})
                .create()

    }
}