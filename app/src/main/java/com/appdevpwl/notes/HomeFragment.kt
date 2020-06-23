package com.appdevpwl.notes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), MyViewHolder.onNoteLongClickListener   {
    lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dbHelper = DBHelper(context!!.applicationContext)
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return  rootView    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val fabAddNote:View = view.findViewById(R.id.fab_add_note)
        fabAddNote.setOnClickListener{
            val intent= Intent(activity, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        home_notes_recyclerView.rootView.findViewById(R.id.home_notes_recyclerView) as RecyclerView
        home_notes_recyclerView.layoutManager=LinearLayoutManager(activity)

        home_notes_recyclerView.adapter = HomeNotesAdapter(dbHelper.showAllNotes().size)

    }

    override fun onLongClick(position: Int) {


    }


}
