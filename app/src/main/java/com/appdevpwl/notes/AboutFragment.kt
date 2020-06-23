package com.appdevpwl.notes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


class AboutFragment : Fragment() {

private var myClipboard: ClipboardManager?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mEmail:TextView = view.findViewById(R.id.textView_contact_email)

        mEmail.setOnLongClickListener(){
            myClipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("email",mEmail.text)
            myClipboard!!.primaryClip=clip
            Toast.makeText(activity,"Copy email to Clipboard",Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        mEmail.setOnClickListener(){
            val sendEmail = Intent(Intent.ACTION_SENDTO)
            sendEmail.type=("text/plain")
            sendEmail.data = Uri.parse("mailto:"+mEmail.text);
            startActivity(Intent.createChooser(sendEmail,"Choose email app.."))
        }
    }


}
