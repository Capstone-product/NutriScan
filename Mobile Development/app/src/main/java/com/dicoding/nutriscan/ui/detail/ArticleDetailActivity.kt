package com.dicoding.nutriscan.ui.detail

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.nutriscan.R
import com.capstone.nutriscan.databinding.ActivityArticelDetailBinding
import com.dicoding.nutriscan.ui.home.Fruit


class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleName = findViewById<TextView>(R.id.tv_event_name)
        val articleDescription = findViewById<TextView>(R.id.tv_event_description)
        val articleImage = findViewById<ImageView>(R.id.img_event_logo)

        val article = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("key_article", Fruit::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("key_article")
        }

        if (article != null) {
            articleName.text = article.name
            articleDescription.text = article.description
            articleImage.setImageResource(article.imageResource)
        }
    }
}

