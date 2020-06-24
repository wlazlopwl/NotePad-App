package com.appdevpwl.notes

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.layout_change_color.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var actualTextTitle: String
    private lateinit var actualTextNote: String
    private lateinit var sharedPreferences: SharedPreferences
    private var colorBeforeSave: String = "#FFFFFF"

    private var idCheckedNote: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        sharedPreferences = SharedPreferences(applicationContext)
        val fontSizeOption = sharedPreferences.getFontSize()

        changeAppearance(new_note_TV, fontSizeOption)
        changeAppearance(new_note_title_TV, fontSizeOption)

        dbHelper = DBHelper(context = this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        var bundle: Bundle? = intent.extras

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)



        if (bundleExist()) {
            idCheckedNote = bundle!!.get("id") as Int
            actualTextTitle = bundle.get("titleText").toString()
            actualTextNote = bundle.get("noteText").toString()
            Log.d("id", idCheckedNote.toString())
            val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                window.decorView.rootView.windowToken, 0
            )



            updateActualNoteInfo()

        } else {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.new_note_change_color -> {

                val dialog = Dialog(this)
                val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_change_color, null)
                dialog.setContentView(dialogView)

//                dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                val someView = findViewById<View>(R.id.idd)
                val root = someView.rootView
                var rgbColor: String
                dialogView.color1.setOnClickListener {

                    rgbColor = resources.getString(R.color.color1)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color2.setOnClickListener {
                    rgbColor = resources.getString(R.color.color2)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color3.setOnClickListener {
                    rgbColor = resources.getString(R.color.color3)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color4.setOnClickListener {
                    rgbColor = resources.getString(R.color.color4)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color5.setOnClickListener {
                    rgbColor = resources.getString(R.color.color5)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color6.setOnClickListener {
                    rgbColor = resources.getString(R.color.color6)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color7.setOnClickListener {
                    rgbColor = resources.getString(R.color.color7)
                    onClickChangeColor(root, rgbColor, dialog)
                }
                dialogView.color8.setOnClickListener {
                    rgbColor = resources.getString(R.color.color8)
                    onClickChangeColor(root, rgbColor, dialog)
                }



                dialog.show()

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickChangeColor(root: View, rgbColor: String, dialog: Dialog) {
        root.setBackgroundColor(Color.parseColor(rgbColor))
        dbHelper.updateNoteColor(idCheckedNote, rgbColor)
        colorBeforeSave = temporaryColorForNewNote(rgbColor)
        dialog.dismiss()
    }


    private fun bundleExist(): Boolean {
        var bundle: Bundle? = intent.extras
        return bundle != null

    }

    private fun temporaryColorForNewNote(color: String): String {
        return if (!bundleExist()) {
            color
        } else "#FFFFFF"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeAppearance(textView: TextView, int: Int) {
        when (int) {
            1 -> textView.setTextAppearance(R.style.textAppearanceSmall)
            2 -> textView.setTextAppearance(R.style.textAppearanceMedium)
            3 -> textView.setTextAppearance(R.style.textAppearanceLarge)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        var bundle: Bundle? = intent.extras
        if (bundle == null) {
            addNewNote()
            val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                window.decorView.rootView.windowToken
                , 0
            )
        } else updateNote(bundle, actualTextTitle, actualTextNote)


    }


    private fun setToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(applicationContext, message, duration).show()

    }

    private fun addNewNote() {
        var textTitle = getTextTitle()
        var textNote = getTextNote()
        var currentTime = getActualTime()

        if ((textNote.isNotEmpty()) || (textTitle.isNotEmpty())) {
            dbHelper.addNote(Note(textTitle, textNote, currentTime, colorBeforeSave))
            setToast("Added new note")

        } else setToast("Not added. Note is empty.")


    }

    private fun updateActualNoteInfo() {

        setActualTitle(dbHelper.getNoteById(idCheckedNote)[0].textTitle.toString())
        setActualNote(dbHelper.getNoteById(idCheckedNote)[0].textNote.toString())
        val someView = findViewById<View>(R.id.idd)
        val root = someView.rootView
        val rgbColor = dbHelper.getNoteById(idCheckedNote)[0].noteColor
        root.setBackgroundColor(Color.parseColor(rgbColor))


    }

    private fun updateNote(bundle: Bundle, actualTitle: String, actualNote: String) {


        var newTitle = getTextTitle()
        var newNote = getTextNote()
        var currentTime = getActualTime()
        if (newNote.isNotEmpty() || newTitle.isNotEmpty()) {
            if ((actualTitle != newTitle) || (actualNote != newNote)) {
                dbHelper.updateNote(idCheckedNote, newTitle, newNote, currentTime)
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


    private fun getTextNote(): String {
        return new_note_TV.text.toString()
    }

    private fun getTextTitle(): String {
        return new_note_title_TV.text.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getActualTime(): String {
        val now = Date()
        return SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(now)
    }
}
