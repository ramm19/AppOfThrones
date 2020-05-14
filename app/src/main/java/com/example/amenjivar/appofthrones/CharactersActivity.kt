package com.example.amenjivar.appofthrones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity(), CharactersFragment.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        if (savedInstanceState == null) {
            val fragment = CharactersFragment()
            this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.listContainer, fragment)
                    .commit()
        }
        //Log.i("CharactersActivity", "${characters.size}")

/*        val button: Button = findViewById(R.id.button_character)
        button.setOnClickListener{
            val intent: Intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        } // se resuelve con la funcion showDetails y haciendo referencia a la propiedad on click en el xml
        */

    }

    override fun onItemClicked(character: Character) {
        if (isDetailViewAvailable())
            showFragmentDetails(character.id)
        else
            launchDetailActivity(character.id)
    }

    //fun isDatailViewAvailable() = findViewById<FrameLayout>(R.id.detailContainer) != null
    private fun isDetailViewAvailable() = detailContainer != null

    private fun showFragmentDetails(characterId: String) {
        val detailFragment = DetailFragment.newInstance(characterId)



        //val instance = DetailFragment()
        //val args = Bundle()
        //args.putString("key_id", characterId)

        //instance.arguments = args



        supportFragmentManager
                .beginTransaction()
                .replace(R.id.detailContainer, detailFragment)
                .commit()
    }

    private fun launchDetailActivity(characterId: String){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key_id", characterId)
        startActivity(intent)
    }

// solo para comprobar y entender el ciclo de vida de una actividad

//    override fun onStart() {
//        super.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//    }
}