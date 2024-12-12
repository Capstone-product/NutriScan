package com.dicoding.nutriscan.ui.detail

 import android.os.Build
 import android.os.Bundle
 import android.widget.ImageView
 import android.widget.TextView
 import androidx.appcompat.app.AppCompatActivity
 import com.capstone.nutriscan.R
 import com.capstone.nutriscan.databinding.ActivityDictionaryDetailBinding
 import com.dicoding.nutriscan.ui.home.Fruit

class DictionaryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictionaryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruitName = findViewById<TextView>(R.id.tv_event_name)
        val fruitDescription = findViewById<TextView>(R.id.tv_event_description)
        val fruitImage = findViewById<ImageView>(R.id.img_event_logo)

        val fruit = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("key_article", Fruit::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("key_article")

        }

        if (fruit != null) {
            fruitName.text = fruit.name
            fruitDescription.text = fruit.description
            fruitImage.setImageResource(fruit.imageResource)
        }
    }
}