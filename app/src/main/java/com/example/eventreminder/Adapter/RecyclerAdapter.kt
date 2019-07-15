package com.example.eventreminder.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eventreminder.Model.Events.Data
import com.example.eventreminder.Model.WeatherResponse.X
import com.example.eventreminder.R
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecyclerAdapter(
    var context: Context,
    internal var eventsData: ArrayList<Data>,
    internal var weatherData: List<X>
) : RecyclerView.Adapter<RecyclerAdapter.myViewHolder>() {

    /**
     * Here Using RecyclerAdapter for binding data from Data SOurce with RecyclerView in Layout
     *
     * */


    val list = arrayListOf<Data>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.myViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
//        list.add(data)
//        Log.i("date", list.size.toString())
//        for (li in list) {
//            print(li.startTime)
//            Log.i("list date", li.startTime)
//        }



        try {
            var data = eventsData[position]
            var weather = weatherData[position]
            // var date = data.timeStamp.substring(0, 10)
            holder.status.text = "Status of Meeting  : " + data.rsvpStatus
            holder.eventName.text = "Event Name is :  " + data.name
            holder.temprature.text = "Temprautre is : " + weather.main.temp.toString()
        }catch (e:Exception){
            Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
        }

    }


    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var status: TextView = itemView.findViewById(R.id.statusEvent)
        var eventName: TextView = itemView.findViewById(R.id.event_name)
        var temprature: TextView = itemView.findViewById(R.id.temprature)

    }
}
