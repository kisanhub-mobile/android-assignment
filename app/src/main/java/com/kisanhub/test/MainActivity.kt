package com.kisanhub.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		rvData.setHasFixedSize(true)
		rvData.layoutManager = LinearLayoutManager(this)

		loadData()
	}

	private fun loadData() {
		val baseUrl = "https://s3.eu-west-2.amazonaws.com/interview-question-data/metoffice/"

		val okHttpClientBuilder = OkHttpClient.Builder()
		val retrofitBuilder = Retrofit.Builder()
			.client(okHttpClientBuilder.build())
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(baseUrl)
		val retrofitService = retrofitBuilder.build().create(RetrofitService::class.java)

		retrofitService.getData("Rainfall", "UK").enqueue(object : Callback<List<Rainfall>> {

			override fun onResponse(call: Call<List<Rainfall>>, response: Response<List<Rainfall>>) {
				response.body()?.let { rainfallList ->
					rvData.adapter = RecyclerAdapter(rainfallList.filter { it.year == 1910 })
				}
			}

			override fun onFailure(call: Call<List<Rainfall>>, t: Throwable) {
				val view: View = this@MainActivity.findViewById(android.R.id.content)
				val message = "Unable to get rainfall data"
				Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
			}

		})
	}

}

interface RetrofitService {

	@GET("{metric}-{location}.json")
	fun getData(@Path("metric") metric: String, @Path("location") location: String): Call<List<Rainfall>>

}
