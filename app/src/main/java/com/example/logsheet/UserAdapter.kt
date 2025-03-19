package com.example.logsheet

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<User>,private val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tvOperator)
        val logNumberText: TextView = itemView.findViewById(R.id.tvLogNumber)
        val dateText: TextView = itemView.findViewById(R.id.tvDate)
        val locationText: TextView = itemView.findViewById(R.id.tvLocation)
        val editButton: Button = itemView.findViewById(R.id.btnEdit)
        //val deleteButton: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.nameText.text = user.name
        holder.logNumberText.text = user.logNumber.toString()
        holder.dateText.text = user.date
        holder.locationText.text = user.location
        holder.editButton.text="Edit"
       // holder.deleteButton.text="Del"
        holder.editButton.setOnClickListener {
            val intent = Intent(context, EditLogActivity::class.java)
            intent.putExtra("id", user.id)
            context.startActivity(intent)
        }
//        holder.deleteButton.setOnClickListener {
//            val ed=EditRecords(user)
//            ed.delete()
//        }
    }

    override fun getItemCount(): Int = userList.size
}