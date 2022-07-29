package com.example.githubapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.githubapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    ) ?: false
                } else {
                    (@Suppress("Deprecation")
                    return this.activeNetworkInfo?.isConnected ?: false)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, imageUrl: String?) {
            if (imageUrl == null) {
                imageView.setImageDrawable(null)
            } else {
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_broken_image_24)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            }
        }

        @JvmStatic
        fun formatDate(inputDate: String): String {
            val dateAfterSplit = inputDate.split("T").get(0)

            val inputPattern = Constants.INPUT_DATE_FORMAT
            val outputPattern = Constants.OUTPUT_DATE_FORMAT
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            var date: Date? = null
            var str: String? = null

            try {
                date = inputFormat.parse(dateAfterSplit)
                str = date?.let { outputFormat.format(it) }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str!!
        }

    }
}