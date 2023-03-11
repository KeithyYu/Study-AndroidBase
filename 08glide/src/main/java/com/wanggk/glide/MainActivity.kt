package com.wanggk.glide

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val url = "https://image.baidu.com/search/detail?z=0&word=%E5%9F%8E%E5%B8%82%E5%BB%BA%E7%AD%91%E6%91%84%E5%BD%B1%E4%B8%93%E9%A2%98&hs=0&pn=5&spn=0&di=&pi=3982&rn=&tn=baiduimagedetail&is=&ie=utf-8&oe=utf-8&cs=2511982910%2C2454873241&os=&simid=&adpicid=0&lpn=0&fr=albumsdetail&fm=&ic=0&sme=&cg=&bdtype=&oriquery=&objurl=https%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D2511982910%2C2454873241%26fm%3D193%26f%3DGIF&fromurl=ipprf_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bev2_z%26e3Bv54AzdH3Fv6jwptejAzdH3Fb8almb0n8&gsm=&islist=&querylist=&album_tab=%E5%BB%BA%E7%AD%91&album_id=7"
        val url2 = "https://image.baidu.com/search/detail?z=0&word=%E5%9F%8E%E5%B8%82%E5%BB%BA%E7%AD%91%E6%91%84%E5%BD%B1%E4%B8%93%E9%A2%98&hs=0&pn=1&spn=0&di=&pi=3978&rn=&tn=baiduimagedetail&is=&ie=utf-8&oe=utf-8&cs=4198287529%2C2774471735&os=&simid=&adpicid=0&lpn=0&fr=albumsdetail&fm=&ic=0&sme=&cg=&bdtype=&oriquery=&objurl=https%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D4198287529%2C2774471735%26fm%3D193%26f%3DGIF&fromurl=ipprf_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bejj6_z%26e3Bv54AzdH3Fri5p5AzdH3F890n80nmb%3F7p4_f576vj%3Dkwt17%267p4_4j1t74%3Dt4w2jfjw6vi%26vit1%3Dlad&gsm=&islist=&querylist=&album_tab=%E5%BB%BA%E7%AD%91&album_id=7"
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageView)
        swipeRefreshLayout.setOnRefreshListener {
            if (!swipeRefreshLayout.isRefreshing) {
                Glide.with(this)
                    .load(url2)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView)
            }
        }
    }
}