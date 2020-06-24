package com.appdevpwl.notes

import android.graphics.Color

class Note {
    var id = 0
    var textTitle: String?= null
    var textNote: String?= null
    var updateDate: String? = null;
    var noteColor:String? = null

    constructor(textTitle: String, textNote: String, updateDate: String,noteColor: String){
        this.textTitle=textTitle
        this.textNote = textNote
        this.updateDate=updateDate
        this.noteColor=noteColor
    }

    constructor(id: Int,textTitle: String,textNote: String, updateDate: String,noteColor: String){
        this.id = id
        this.textTitle=textTitle
        this.textNote = textNote
        this.updateDate=updateDate
        this.noteColor=noteColor
    }

}