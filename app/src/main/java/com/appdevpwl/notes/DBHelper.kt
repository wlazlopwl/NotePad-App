package com.appdevpwl.notes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){

    companion object{
        val DB_NAME = "allNotes.db"
        val DB_VERSION = 2
        val TABLE_NAME ="Notes"
        val KEY_ID = "id"
        val KEY_TITLE="title"
        val KEY_TEXT="text"
        val KEY_DATE="date"
        val KEY_COLOR="color"

        private val SQL_CERATE_ENTRIES ="CREATE TABLE "+ TABLE_NAME+"("+ KEY_ID+ " INTEGER PRIMARY KEY,"+ KEY_TEXT+" TEXT,"+ KEY_TITLE+" TEXT," + KEY_DATE+ " TEXT"+")"

        private val SQL_DELETE_ENTRIES="DROP TABLE IF EXIST" + TABLE_NAME
        private val SQL_UPDATE_NOTE="DROP TABLE IF EXIST" + TABLE_NAME
        private val SQL_UPGRADE_DB_1_TO_2="ALTER TABLE "+ TABLE_NAME+" ADD COLUMN "+ KEY_COLOR+" TEXT DEFAULT '#FFFFFF'"
    }

    override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL(SQL_CERATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL(SQL_DELETE_ENTRIES)
//        onCreate(db)
        if(newVersion==2){
            db?.execSQL(SQL_UPGRADE_DB_1_TO_2)
        }
    }
    fun deleteNote(id:Int){
        val db=this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME+" where id='"+id+"'")

    }

    fun updateNote(id:Int,title:String,text:String,date:String){


        val values =ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_TEXT,text)
        values.put(KEY_DATE, date)
        val db=this.writableDatabase
        db.update(TABLE_NAME,values,"id='"+id+"'",null)
        db.close()


    }
    fun updateNoteColor(id:Int,RGBcolor:String) {
        val values = ContentValues()
        values.put(KEY_COLOR,RGBcolor)
        val db=this.writableDatabase
        db.update(TABLE_NAME,values,"id='"+id+"'",null)

    }
    fun countAllNotes():Int{
        val cursor : Cursor
        val db=readableDatabase
        cursor = db.rawQuery("select count(*) from Notes", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

     fun addNote(note: Note){
        val values =ContentValues()
        values.put(KEY_TITLE, note.textTitle)
        values.put(KEY_TEXT, note.textNote)
        values.put(KEY_DATE,note.updateDate)
         values.put(KEY_COLOR,note.noteColor)
        val db= this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getNoteById(id:Int): ArrayList<Note>{
        val note = ArrayList<Note>()
        val db=writableDatabase
        var cursor: Cursor

        try {
            cursor=db.rawQuery("select * from "+ TABLE_NAME+" WHERE id='"+id+"'", null)
        }
        catch (e:SQLException){
            db.execSQL(SQL_CERATE_ENTRIES)
            return ArrayList()

        }
        var id: Int
        var textTitle: String
        var textNote: String
        var updateDate: String
        var noteColor :String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast){
                id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                textTitle=cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                textNote=cursor.getString(cursor.getColumnIndex(KEY_TEXT))
                updateDate=cursor.getString(cursor.getColumnIndex(KEY_DATE))
                noteColor=cursor.getString(cursor.getColumnIndex(KEY_COLOR))
                note.add(Note(id,textTitle,textNote,updateDate, noteColor))
                cursor.moveToNext()
            }

        }

        return note
    }
    fun showAllNotes(): ArrayList<Note>{
        val notes = ArrayList<Note>()
        val db=writableDatabase
        var cursor: Cursor

        try {
            cursor=db.rawQuery("select * from "+ TABLE_NAME+" order by "+ KEY_ID+" desc", null)
        }
        catch (e:SQLException){
            db.execSQL(SQL_CERATE_ENTRIES)
            return ArrayList()

        }
        var id: Int
        var textTitle: String
        var textNote: String
        var updateDate: String
        var noteColor :String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast){
                id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                textTitle=cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                textNote=cursor.getString(cursor.getColumnIndex(KEY_TEXT))
                updateDate=cursor.getString(cursor.getColumnIndex(KEY_DATE))
                noteColor=cursor.getString(cursor.getColumnIndex(KEY_COLOR))
                notes.add(Note(id,textTitle,textNote,updateDate,noteColor))
                cursor.moveToNext()
            }

        }

        return notes
    }




}