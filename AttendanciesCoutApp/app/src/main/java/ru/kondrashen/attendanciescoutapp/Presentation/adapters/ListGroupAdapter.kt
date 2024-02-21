package ru.kondrashen.attendanciescoutapp.Presentation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.databinding.ListYearItemBinding

class ListGroupAdapter(group: MutableList<Group>): RecyclerView.Adapter<ListGroupAdapter.ViewHolder>() {
    private var groups: MutableList<Group> = mutableListOf()
    init {
        this.groups = group
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListYearItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return groups.size
    }
    inner class ViewHolder(private val item: ListYearItemBinding) : RecyclerView.ViewHolder(item.root), View.OnClickListener{
        private lateinit var group: Group


        fun bindItem(group: Group){
            this.group = group
            item.listItemYearTextView.text = group.year.toString()
            item.listItemYearEndTextView.text =group.name
            item.root.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val activity = v!!.context as Activity
            val con = item.root.context
            println("Нажатие работает: " + item.listItemYearEndTextView.text)
//            val intent =
//            activity.startActivity()


        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.bindItem(group)
    }
}
