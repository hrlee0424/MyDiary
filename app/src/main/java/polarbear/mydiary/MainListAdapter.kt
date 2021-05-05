package polarbear.mydiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter(val list: List<WriteEntity>, val context: Context) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!list.isEmpty()){
            holder.title.setText(list[position].title)
            holder.contents.setText(list[position].contents)
            holder.date.setText(list[position].date)
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var title : TextView = itemView.findViewById(R.id.title)
        var contents : TextView = itemView.findViewById(R.id.contents)
        var date : TextView = itemView.findViewById(R.id.date)

    }

}