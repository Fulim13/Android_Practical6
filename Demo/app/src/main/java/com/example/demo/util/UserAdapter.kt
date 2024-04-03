package com.example.demo.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.data.User
import com.example.demo.databinding.ItemUserBinding

class UserAdapter (
    val fn: (ViewHolder, User) -> Unit = { _, _ -> }
) : ListAdapter<User, UserAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(a: User, b: User) = a.email == b.email
        override fun areContentsTheSame(a: User, b: User) = a == b
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.imgPhoto.setImageBlob(user.photo)
        holder.binding.txtName.text = user.name
        holder.binding.txtEmail.text = user.email
        holder.binding.txtPassword.text = user.password
        fn(holder, user)
    }

}
