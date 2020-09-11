package com.lffq.recyclerviewtest

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SunsetPhoto(val url: String): Parcelable {

    companion object {
        fun getSunsetPhotos(): Array<SunsetPhoto> {
            return arrayOf<SunsetPhoto>(
                SunsetPhoto("https://i.pinimg.com/originals/59/b4/5f/59b45f41f8453797686765921ed8ab28.jpg"),
                SunsetPhoto("https://is2-ssl.mzstatic.com/image/thumb/Purple123/v4/5e/a6/d2/5ea6d2fe-6d22-8d18-8f33-2d410cd2fbf6/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/246x0w.png"),
                SunsetPhoto("https://goo.gl/U7XXdF"),
                SunsetPhoto("https://goo.gl/ghVPFq"),
                SunsetPhoto("https://androidinsider.ru/wp-content/uploads/2017/10/Google-Wallpaper-Good-Full-HD-750x422.png"),
                SunsetPhoto("https://s4827.pcdn.co/wp-content/uploads/2020/03/wallpapersden.com_child-yoda-4k_2932x2932-scaled.jpg")
            )
        }
    }
}