package com.example.hackaton_techtravel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(
    private val avistamientos: List<Avistamiento>
) : RecyclerView.Adapter<SearchAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage1: CircleImageView = itemView.findViewById(R.id.profile_image1)
        private val titulo1: TextView = itemView.findViewById(R.id.titulo1)
        private val descripcion1: TextView = itemView.findViewById(R.id.descripcion1)
        private val fecha1: TextView = itemView.findViewById(R.id.fecha1)

        private val profileImage2: CircleImageView = itemView.findViewById(R.id.profile_image2)
        private val titulo2: TextView = itemView.findViewById(R.id.titulo2)
        private val descripcion2: TextView = itemView.findViewById(R.id.descripcion2)
        private val fecha2: TextView = itemView.findViewById(R.id.fecha2)

        private val profileImage3: CircleImageView = itemView.findViewById(R.id.profile_image3)
        private val titulo3: TextView = itemView.findViewById(R.id.titulo3)
        private val descripcion3: TextView = itemView.findViewById(R.id.descripcion3)
        private val fecha3: TextView = itemView.findViewById(R.id.fecha3)

        fun bind(avistamientos: List<Avistamiento>) {
            if (avistamientos.size >= 1) {
                profileImage1.setImageResource(avistamientos[0].image)
                titulo1.text = avistamientos[0].tipoAve
                descripcion1.text = avistamientos[0].descripcion
                fecha1.text = avistamientos[0].fecha
            }

            if (avistamientos.size >= 2) {
                profileImage2.setImageResource(avistamientos[1].image)
                titulo2.text = avistamientos[1].tipoAve
                descripcion2.text = avistamientos[1].descripcion
                fecha2.text = avistamientos[1].fecha
            }

            if (avistamientos.size >= 3) {
                profileImage3.setImageResource(avistamientos[2].image)
                titulo3.text = avistamientos[2].tipoAve
                descripcion3.text = avistamientos[2].descripcion
                fecha3.text = avistamientos[2].fecha
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_adapter, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val sublist = avistamientos.subList(position * 3, minOf(position * 3 + 3, avistamientos.size))
        holder.bind(sublist)
    }

    override fun getItemCount(): Int {
        return (avistamientos.size + 2) / 3
    }
}
