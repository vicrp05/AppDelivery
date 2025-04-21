package com.victor.app33.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.app33.R
import com.victor.app33.activities.client.home.ClientHomeActivity
import com.victor.app33.activities.client.products.list.ClientProductsListActivity
import com.victor.app33.activities.delivery.home.DeliveryHomeActivity
import com.victor.app33.activities.restaurant.home.RestaurantHomeActivity
import com.victor.app33.models.Category
import com.victor.app33.models.Rol
import com.victor.app33.routes.utils.SharedPref

class CategoriesAdapter(val context :Activity, val categories: ArrayList<Category>): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


    val sharedPref = SharedPref(context)
    //Instancia la Vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_categories, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position] //Obtenemos cada una de las categorias

        holder.textViewCategory.text = category.name
        Glide.with(context).load(category.image).into(holder.imageViewCategory)

        holder.itemView.setOnClickListener {goToProducts(category) }
    }

    private fun goToProducts(category: Category) {

            val i = Intent(context, ClientProductsListActivity::class.java)
            i.putExtra("idCategory", category.id)
           context.startActivity(i)
    }




    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCategory: TextView
        val imageViewCategory: ImageView

        init {
            textViewCategory = view.findViewById<TextView>(R.id.textview_category)
            imageViewCategory = view.findViewById<ImageView>(R.id.imageview_category)
        }
    }
}