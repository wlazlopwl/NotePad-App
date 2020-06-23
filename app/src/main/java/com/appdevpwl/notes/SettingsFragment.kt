package com.appdevpwl.notes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = SharedPreferences(context!!.applicationContext)
        val mFontSize= view.findViewById<LinearLayout>(R.id.font_size_option)




        mFontSize.setOnClickListener(){

            val fontSizeList = arrayOf("Small","Medium","Large")

            var checkedItem = sharedPreferences.getFontSize() -1

            val builder = AlertDialog.Builder(activity)
            builder.setSingleChoiceItems(fontSizeList,checkedItem){builder, which->
                when (which) {
                    0 ->{
                        checkedItem=0
                        sharedPreferences.setFontSize(1) // 1 - small font
                        builder.dismiss()
                        updateSizeToast(0, fontSizeList)
                    }
                    1->{
                        checkedItem=1
                        sharedPreferences.setFontSize(2) // 2 - medium font

                        updateSizeToast(1, fontSizeList)
                        builder.dismiss()
                    }
                    2->{
                        checkedItem=2
                        sharedPreferences.setFontSize(3) // large font
                        updateSizeToast(2, fontSizeList)

                        builder.dismiss()

                    }

                }
            }
            builder.setTitle(R.string.settings_font_size)
            builder.setCancelable(true)

            builder.show()

        }
    }

    private fun updateSizeToast(checkedItem:Int, list: Array<String>){
        Toast.makeText(activity,"Font changed: "+ list[checkedItem],Toast.LENGTH_SHORT).show()
    }



}




