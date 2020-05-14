package com.example.amenjivar.appofthrones

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("key_id")

        if(savedInstanceState == null) {
            val fragment = DetailFragment.newInstance(id)

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit()
        }


//        button.setOnClickListener{
//            Toast.makeText(this@DetailActivity,character?.house?.words, Toast.LENGTH_SHORT).show()
//        }
    }
}
