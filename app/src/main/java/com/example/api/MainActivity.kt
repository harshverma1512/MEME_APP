package com.example.api

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.api.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        lateinit var binding : ActivityMainBinding
        var currentimageurl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadrequest()
        nextmeme()
        Sharememe()
     }
    fun loadrequest(){

        val imageView = findViewById<ImageView>(R.id.imageView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentimageurl = response.getString("url")


                Glide.with(this).load(currentimageurl)
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false}

            }).into(imageView)
            },
            {
                Toast.makeText(this,"check your internet connection",Toast.LENGTH_LONG).show()
            })
        // Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }
    fun nextmeme(){
        val nextmeme = findViewById<Button>(R.id.next)
        nextmeme.setOnClickListener(){
            loadrequest()
        }
    }
    fun Sharememe(){
        val Sharememe = findViewById<Button>(R.id.share)
        Sharememe.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, currentimageurl)
            startActivity(Intent.createChooser(intent, "Hey Check out this Meme"))
        }
    }
}

