package ru.kondrashen.attendanciescoutapp.Presentation.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.kondrashen.attendanciescoutapp.R
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.databinding.ListGroupItemBinding

class ListGroupAdapter(group: MutableList<Group>, findNavController: NavController): RecyclerView.Adapter<ListGroupAdapter.ViewHolder>() {
    private var groups: MutableList<Group> = mutableListOf()
    private var navController: NavController

    init {
        this.groups = group
        this.navController = findNavController

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListGroupItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return groups.size
    }
    inner class ViewHolder(private val item: ListGroupItemBinding) : RecyclerView.ViewHolder(item.root), View.OnClickListener{
        private lateinit var group: Group


        fun bindItem(group: Group){
            this.group = group
            item.listItemGroupIdView.text = group.id.toString()
            item.listItemGroupNameView.text =group.name
            item.root.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val bundle = Bundle()
            bundle.putInt("groupId", item.listItemGroupIdView.text.toString().toInt())
            navController.navigate(R.id.action_FirstFragment_to_StudentFragment, bundle)
//            println("Нажатие работает: " + item.listItemGroupIdView.text)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.bindItem(group)
    }
}
