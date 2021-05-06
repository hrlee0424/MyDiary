package polarbear.mydiary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import polarbear.mydiary.databinding.ListItemBinding

class MainListAdapter(private val list: List<WriteEntity>, val context: Context) :
    RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)*/
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    inner class ViewHolder(binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        val binding = binding
        fun bind(model:WriteEntity){
            binding.model = model
            binding.root.setOnClickListener{
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("id", model.id)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run{context.startActivity(this)}
            }
        }
    }

    /*inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var title : TextView = itemView.findViewById(R.id.title)
        var contents : TextView = itemView.findViewById(R.id.contents)
        var date : TextView = itemView.findViewById(R.id.date)

        fun bind(item: WriteEntity){
            title.text = item.title
            contents.text = item.contents
            date.text = item.date

            itemView.setOnClickListener {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("id", item.id)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run{context.startActivity(this)}
            }
        }
    }*/
}