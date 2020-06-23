package com.appdevpwl.notes

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_long_click_note.view.*
import kotlinx.android.synthetic.main.simple_note_horizontal.view.*

class HomeNotesAdapter(countNotes:Int) : RecyclerView.Adapter<MyViewHolder>()   {
    lateinit var dbHelper: DBHelper
    lateinit var context:Context
    lateinit var addNoteActivity: AddNoteActivity
    lateinit var mLongListener: MyViewHolder.onNoteLongClickListener
    lateinit var mOnItemClickListener: OnItemClickListener
    var countNotes=countNotes

    interface OnItemClickListener{
        fun onItemClick(position: Int) {
            
        }
    }

    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener ) {

        mOnItemClickListener=OnItemClickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        dbHelper = DBHelper(context)
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cardViewNote = layoutInflater.inflate(R.layout.simple_note_horizontal, parent, false)
        return MyViewHolder(cardViewNote)
    }

    override fun getItemCount(): Int {

        return countNotes
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)  {
        context=holder.itemView.context
        dbHelper= DBHelper(this.context)
        val title = holder.view.simple_note_title
         val note = holder.view.simple_note_text
         val dateTimeNote = holder.view.simple_note_dateTime
         val card = holder.view.simple_note_card_id
        val id:Int = dbHelper.showAllNotes()[position].id

        title.text = dbHelper.showAllNotes()[position].textTitle

        note.text = dbHelper.showAllNotes()[position].textNote
        dateTimeNote.text = dbHelper.showAllNotes()[position].updateDate

        holder.view.setOnLongClickListener(){

//            card.cardElevation= 25F

            val dialogView=LayoutInflater.from(context).inflate(R.layout.dialog_long_click_note,null)
            val builder = AlertDialog.Builder(context).setView(dialogView)
            builder.setCancelable(true)
            builder.create()
            val mDialog=builder.show()

            val delete = dialogView.dialog_delete
            val copy=dialogView.dialog_copy
            val share=dialogView.dialog_share
            val duplicate= dialogView.dialog_duplicate

            duplicate.setOnClickListener(){
                addNoteActivity= AddNoteActivity()
                val dNote= dbHelper.getNoteById(id)
                val duplicateNote: Note = Note(dNote.get(0).textTitle.toString(),dNote.get(0).textNote.toString(), addNoteActivity.getActualTime())

                dbHelper.addNote(duplicateNote)
                countNotes += 1
                notifyDataSetChanged()

                mDialog.dismiss()

            }

            delete.setOnClickListener(){
                dbHelper.deleteNote(id)
                countNotes -= 1
                notifyItemRemoved( position )
                notifyItemRangeChanged(position, 1)
                card.cardElevation= 0F
                mDialog.dismiss()
            }
            copy.setOnClickListener(){
                var clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clip = ClipData.newPlainText("label",note.text)
                clipboard.primaryClip = clip
                Toast.makeText(context,R.string.copyToast,Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
                mDialog.dismiss()

            }
            share.setOnClickListener(){
                val shareNote=Intent(Intent.ACTION_SEND)
                shareNote.type = "text/plain"
                shareNote.putExtra(Intent.EXTRA_TEXT,note.text.toString())
                it.context.startActivity(Intent.createChooser(shareNote,"Note"))
                mDialog.dismiss()


            }

        true

        }

        holder.view.setOnClickListener(){
            val titleText: String = title.text.toString()
            val noteText: String = note.text.toString()

            val intent = Intent(context, AddNoteActivity::class.java)
            intent.putExtra("titleText", titleText)
            intent.putExtra("noteText", noteText)
            intent.putExtra("id", id)
            startActivity(context,intent, null)

        }




    }

}

class MyViewHolder( val view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {

    init {
        view.setOnLongClickListener(this)
        view.setOnClickListener(this)

    }


    override fun onLongClick(v: View?): Boolean {

        return true
    }

    override fun onClick(v: View?) {


    }

     interface onNoteLongClickListener{
        fun onLongClick(position: Int)

    }

}