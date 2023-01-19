package com.jaytaravia.newsly

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsList = findViewById<RecyclerView>(R.id.newsList)

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)



        getNews()
    }

    private fun getNews(){


        val news = NewsServices.newsInstance.getHeadlines("in", pageNum)
        news.enqueue(object:Callback<News>{

            override fun onFailure(call: retrofit2.Call<News>, t: Throwable) {
                Log.d("CHEEZYCODE","Error fetching News", t )
            }


            override fun onResponse(call: retrofit2.Call<News>, response: Response<News>) {

                val news = response.body()
                if(news!=null){
                    totalResults=news.totalResults
                    Log.d("CHEEZYCODE", news.toString())
                    this@MainActivity.articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }


        })
    }
}