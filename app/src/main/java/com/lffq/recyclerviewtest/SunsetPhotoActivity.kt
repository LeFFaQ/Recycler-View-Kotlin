package com.lffq.recyclerviewtest

import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.ImageView
import android.widget.TextView
import com.lffq.recyclerviewtest.network.Latest
import com.squareup.picasso.Picasso
import java.text.FieldPosition

class SunsetPhotoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUNSET_PHOTO = "SunsetPhotoActivity.EXTRA_SUNSET_PHOTO"
        const val EXTRA_INFO = "SunsetPhotoActivity.EXTRA_INFO"
        const val EXTRA_POSITION = "SunsetPhotoActivity.EXTRA_POSITION"
    }

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var sunsetPhoto: Latest
    var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunset_photo)

        sunsetPhoto = intent.getParcelableExtra(EXTRA_INFO)!!
        position = intent.extras?.getInt(EXTRA_POSITION)

        imageView = findViewById(R.id.image)
        titleView = findViewById(R.id.title_item)
        descriptionView = findViewById(R.id.description_item)

    }

    override fun onStart() {
        super.onStart()

        titleView.text = sunsetPhoto.news?.get(position!!)?.title
        descriptionView.text = sunsetPhoto.news?.get(position!!)?.description

        Picasso.get()
            .load(sunsetPhoto.news?.get(position!!)?.image)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.to_right, R.anim.alpha)
    }
}