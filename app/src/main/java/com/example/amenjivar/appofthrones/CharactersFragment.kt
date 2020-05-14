package com.example.amenjivar.appofthrones

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment() {
    val list: RecyclerView by lazy {

        val list: RecyclerView = view!!.findViewById(R.id.list)// con !! le aseguramos a Kotlin que jamas sera nulo
        list.layoutManager = LinearLayoutManager(context)

        list
    }

    val adapter: CharactersAdapter by lazy {
        val adapter = CharactersAdapter() { item, position ->
            clickListener.onItemClicked(item)
        }

        adapter
    }

    //lateinit var --> indico que yo como desarrollador me coomprometo que en las siguientes lineas cercanas hare la inicializacion, asi no me da error si porque puede ser null
    lateinit var clickListener: OnItemClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnItemClickListener)
            clickListener = context
        else
            throw IllegalArgumentException("Attached activity doesn't implement CharacterFragment.OnItemClickListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val characters: MutableList<Character> = CharactersRepo.characters
        adapter.setCharacters(characters)*/ //esto se hacia cuando no tenia datos reales, character era publico de CharactersRepo

        list.adapter = adapter

        btnRetry.setOnClickListener{
            retry()
        }
    }

    override fun onResume() {
        super.onResume()
        requestCharacters() // lo ejecuto aca porque hay muchas cosas de dibujado ocurriendo en onCreateView y en OnViewCreated
    }

    private fun retry(){
        layoutError.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        requestCharacters()
    }

    private fun requestCharacters() {
        CharactersRepo.requestCharacters(context!!,
                { characters ->
                    view?.let {
                        progressBar.visibility = View.INVISIBLE
                        list.visibility = View.VISIBLE
                        layoutError.visibility = View.INVISIBLE
                        adapter.setCharacters(characters)
                    }
                },
                {
                    view.let {
                        progressBar.visibility = View.INVISIBLE
                        list.visibility = View.INVISIBLE
                        layoutError.visibility = View.VISIBLE
                    }
                })
    }

    interface OnItemClickListener {
        fun onItemClicked(character: Character)
    }

}