package com.plan.prand.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plan.prand.R

// Usa la clase correcta: itemcarru
class carruadapter(private val items: List<itemcarru>) : RecyclerView.Adapter<carruadapter.CarouselViewHolder>() {

    // ViewHolder para cada item del carrusel
    class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.carouselImage)
        val textView: TextView = view.findViewById(R.id.carouselText)
    }

    // Crea el ViewHolder inflando el layout item_carousel.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    // Asigna los datos al ViewHolder (imagen y texto)
    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageRes)  // Carga la imagen
        holder.textView.text = item.text  // Carga el texto
    }

    // Retorna el número de items en la lista
    override fun getItemCount() = items.size
}
