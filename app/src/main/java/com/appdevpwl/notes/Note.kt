package com.appdevpwl.notes

class Note {
    var id = 0
    var textTitle: String?= null
    var textNote: String?= null
    var updateDate: String? = null;

    constructor(textTitle: String, textNote: String, updateDate: String){
        this.textTitle=textTitle
        this.textNote = textNote
        this.updateDate=updateDate
    }

    constructor(id: Int,textTitle: String,textNote: String, updateDate: String){
        this.id = id
        this.textTitle=textTitle
        this.textNote = textNote
        this.updateDate=updateDate
    }

}