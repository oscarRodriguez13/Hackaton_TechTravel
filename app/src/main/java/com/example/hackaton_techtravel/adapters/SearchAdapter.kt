package com.example.hackaton_techtravel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackaton_techtravel.data.Avistamiento
import com.example.hackaton_techtravel.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Locale

class SearchAdapter(
    private var avistamientos: List<Avistamiento>,
    private val onClick: (Avistamiento) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ProfileViewHolder>() {

    private var filteredAvistamientos = avistamientos

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage1: CircleImageView = itemView.findViewById(R.id.profile_image1)
        private val titulo1: TextView = itemView.findViewById(R.id.titulo1)
        private val descripcion1: TextView = itemView.findViewById(R.id.descripcion1)
        private val fecha1: TextView = itemView.findViewById(R.id.fecha1)

        fun bind(avistamiento: Avistamiento) {
            profileImage1.setImageResource(avistamiento.image)
            titulo1.text = avistamiento.tipoAve
            descripcion1.text = avistamiento.descripcion
            fecha1.text = avistamiento.fecha

            itemView.setOnClickListener { onClick(avistamiento) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_adapter, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(filteredAvistamientos[position])
    }

    override fun getItemCount(): Int {
        return filteredAvistamientos.size
    }

    fun filter(text: String) {
        if (text.isEmpty()) {
            filteredAvistamientos = avistamientos
        } else {
            filteredAvistamientos = avistamientos.filter {
                it.tipoAve.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
            }
        }
        notifyDataSetChanged()
    }
}
