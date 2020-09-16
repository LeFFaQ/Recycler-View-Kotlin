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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lffq.recyclerviewtest.network.Latest
import com.lffq.recyclerviewtest.network.SearchProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    /**
     * MainActivity
     * Здесь инициализируется Recyclerview сразу
     */


    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_images)
        recyclerView.setHasFixedSize(true)
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        imageGalleryAdapter = ImageGalleryAdapter(this)

        if (ImageGalleryAdapter(this).newsResult == null) {
            //Загрузка данных
            val repository = SearchProvider.provideSearch()
            repository.searchUsers("ru")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("Загрузчик:", "Запрос прошёл (статус: ${result.status})")
                    recyclerView.adapter = imageGalleryAdapter
                    imageGalleryAdapter.newsResult = result
                }, { error ->
                    error.printStackTrace()
                })
        } else {
            Log.d("Загрузчик:", "Запрос уже был выполнен")}

    }


    private inner class ImageGalleryAdapter(val context: Context): RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

        var newsResult: Latest? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryAdapter.MyViewHolder {
            val context= parent.context
            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false) as LinearLayout
            return MyViewHolder(photoView)
        }

        override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
            val imageView = holder.imageTitle

            //Вывод текста на экран
            holder.textTitle.apply { text = newsResult?.news?.get(position)?.title }
            holder.textAuthor.apply { text = newsResult?.news?.get(position)?.author }
            holder.textDate.apply { text = newsResult?.news?.get(position)?.published }

            //проверка если нету изображения и загрузка изображения
            if (newsResult?.news?.get(position)?.image != "None") {
                //Загрузка картинки
                Picasso.get()
                    .load(newsResult?.news?.get(position)?.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            Log.d("Picasso", "onSuccess: the image in the position $position has been successfully loaded")
                        }
                        override fun onError(e: Exception?) {
                            Log.d("Picasso", "onError: $e , Position: $position")
                        }
                    })
            }
        }
            override fun getItemCount(): Int {
                //Метод, возвращающий кол-во полученных элементов (новостей)
                return newsResult?.news?.size!!
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

            //Элементы интерфейса
            var imageTitle = itemView.findViewById(R.id.iv_photo) as ImageView
            var textTitle = itemView.findViewById(R.id.iv_title) as TextView
            var textAuthor = itemView.findViewById(R.id.iv_author) as TextView
            var textDate = itemView.findViewById(R.id.iv_date) as TextView

            init { itemView.setOnClickListener(this) }

            override fun onClick(view: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, SunsetPhotoActivity::class.java).apply {
                        //Передача данных в детальный активити
                        putExtra(SunsetPhotoActivity.EXTRA_POSITION, position)
                        putExtra(SunsetPhotoActivity.EXTRA_INFO, newsResult)
                    }
                    startActivity(intent)
                    overridePendingTransition(R.anim.to_left, R.anim.alpha) //Анимация изменения активити
                }
            }
        }
    }


}