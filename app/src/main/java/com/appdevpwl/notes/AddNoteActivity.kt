package com.appdevpwl.notes

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var actualTextTitle:String
    private lateinit var actualTextNote:String
    private lateinit var  sharedPreferences: SharedPreferences
    private var idCheckedNote:Int = 1
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        sharedPreferences= SharedPreferences(applicationContext)
        val fontSizeOption = sharedPreferences.getFontSize()

      changeAppearance(new_note_TV,fontSizeOption)
      changeAppearance(new_note_title_TV,fontSizeOption)

        dbHelper= DBHelper(context=this )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

         var bundle:Bundle?=intent.extras

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);




        if (bundle!=null) {
            idCheckedNote = bundle!!.get("id") as Int
            actualTextTitle =  bundle!!.get("titleText").toString()
            actualTextNote = bundle!!.get("noteText").toString()
            val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                window.decorView.rootView.windowToken
                , 0)



            updateActualNoteInfo()

        }
        else{
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)


        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeAppearance (textView: TextView, int: Int) {
            when(int){
                1-> textView.setTextAppearance(R.style.textAppearanceSmall)
                2->textView.setTextAppearance(R.style.textAppearanceMedium)
                3->textView.setTextAppearance(R.style.textAppearanceLarge)
            }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        var bundle:Bundle?=intent.extras
        if (bundle==null) {
            addNewNote()
            val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                window.decorView.rootView.windowToken
                , 0)
        }
        else updateNote(bundle, actualTextTitle,actualTextNote)



    }

    fun View.snack(view: View,message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, message, duration).show()
    }

    fun setToast(message: String, duration: Int=Toast.LENGTH_SHORT) {
        Toast.makeText(applicationContext,message,duration).show()

    }

private fun addNewNote(){
    var textTitle = getTextTitle()
    var textNote = getTextNote()
    var currentTime = getActualTime()
    if ((textNote.isNotEmpty())||(textTitle.isNotEmpty())){
        dbHelper.addNote(Note(textTitle,textNote,currentTime))
        setToast("Added new note")

        }
    else setToast("Not added. Note is empty.")



}
    private fun updateActualNoteInfo() {
        // set actual note info

        setActualTitle(dbHelper.getNoteById(idCheckedNote).get(0).textTitle.toString())
        setActualNote(dbHelper.getNoteById(idCheckedNote).get(0).textNote.toString())





    }
    private fun updateNote(bundle: Bundle, actualTitle: String, actualNote: String){



        var newTitle=getTextTitle()
        var newNote = getTextNote()
        var currentTime = getActualTime()
        if (newNote.isNotEmpty() || newTitle.isNotEmpty()) {
            if ((actualTitle!=newTitle)||(actualNote!=newNote)) {
                dbHelper.updateNote(idCheckedNote as Int, newTitle, newNote, currentTime)
                setToast("Note updated")
            }

        }

    }
    private fun setActualNote(newNote: String) {
        new_note_TV.setText(newNote)

    }

    private fun setActualTitle(newTitle: String) {
        new_note_title_TV.setText(newTitle)

    }


    private fun getTextNote():String{
        return new_note_TV.text.toString()
    }
    private fun getTextTitle():String{
        return new_note_title_TV.text.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getActualTime():String{
//        val currentTime=LocalDateTime.now()
//        val formatTime= DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")

        val sdf = SimpleDateFormat("HH:mm:ss dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        return currentDate.format(sdf)
    }
}
