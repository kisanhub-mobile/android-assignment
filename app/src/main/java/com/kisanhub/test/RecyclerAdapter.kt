package com.kisanhub.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item_data.view.*


/**
 * Created by Bhushan on 25/04/19.
 *
 * @author Bhushan
 **/
class RecyclerAdapter(private val list: List<Rainfall>?) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

	override fun getItemCount(): Int {
		return list?.size ?: 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.rv_item_data, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
		viewHolder.loadData(list?.get(position))
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		fun loadData(data: Rainfall?) {
			itemView.tvMonth.text = getMonthString(data?.month)
			itemView.tvMetric.text = "Rainfall: "
			itemView.tvValue.text = data?.value?.toString()
		}

		private fun getMonthString(month: Int?): CharSequence? {
			return when (month) {
				1 -> "January"
				2 -> "February"
				3 -> "March"
				4 -> "April"
				5 -> "May"
				6 -> "June"
				7 -> "July"
				8 -> "August"
				9 -> "September"
				10 -> "October"
				11 -> "November"
				12 -> "December"
				else -> null
			}
		}
	}

}