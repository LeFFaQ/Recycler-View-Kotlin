package com.lffq.recyclerviewtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lffq.recyclerviewtest.network.SearchProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        imageGalleryAdapter = ImageGalleryAdapter(this, SunsetPhoto.getSunsetPhotos())
    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = imageGalleryAdapter
    }


    private inner class ImageGalleryAdapter(val context: Context, val sunsetPhotos: Array<SunsetPhoto>): RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryAdapter.MyViewHolder {
            val context= parent.context
            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false) as LinearLayout
            return MyViewHolder(photoView)
        }

        @SuppressLint("CheckResult")
        override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
            val sunsetPhoto = sunsetPhotos[position]
            val imageView = holder.imageTitle
            val title = holder.textTitle
            val author = holder.textAuthor
            val date = holder.textDate

            //Загрузка данных
            val repository = SearchProvider.provideSearch()
            repository.searchUsers("ru")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    //Log.d("Result", result.toString())

                    if (result != null) {

                        title.text = result.news?.get(position)?.title
                        author.text = result.news?.get(position)?.author
                        date.text = result.news?.get(position)?.published

                        //Загрузка картинки
                        Picasso.get()
                            .load(result.news?.get(position)?.image)
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_error)
                            .into(imageView, object : Callback {
                                override fun onSuccess() {

                                }

                                override fun onError(e: Exception?) {
                                    Log.d("123", "onError: $e , Position: $position")
                                }
                            })
                    }


                }, { error ->
                    error.printStackTrace()
                })



        }

        override fun getItemCount(): Int {
            return sunsetPhotos.size
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

            var imageTitle = itemView.findViewById(R.id.iv_photo) as ImageView
            var textTitle = itemView.findViewById(R.id.iv_title) as TextView
            var textAuthor = itemView.findViewById(R.id.iv_author) as TextView
            var textDate = itemView.findViewById(R.id.iv_date) as TextView

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val sunsetPhoto = sunsetPhotos[position]
                    val intent = Intent(context, SunsetPhotoActivity::class.java).apply { putExtra(SunsetPhotoActivity.EXTRA_SUNSET_PHOTO, sunsetPhoto) }
                    //startActivity(intent)
                }
            }
        }
    }


}