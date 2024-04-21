package com.example.hackaton_techtravel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class SolicitudPaseoAdapter(
    private val avistamientos: List<Avistamiento>
) : RecyclerView.Adapter<SolicitudPaseoAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)
        private val tipoAve: TextView = itemView.findViewById(R.id.tipo_ave)
        private val descripcion: TextView = itemView.findViewById(R.id.descripcion)
        private val fecha: TextView = itemView.findViewById(R.id.fecha)

        fun bind(avistamiento: Avistamiento) {
            profileImage.setImageResource(avistamiento.image)
            tipoAve.text = avistamiento.tipoAve
            descripcion.text = avistamiento.descripcion
            fecha.text = avistamiento.fecha
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.avistamientos_adapter, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = avistamientos[position]
        holder.bind(profile) // Llamar a la función bind para configurar el elemento
    }

    override fun getItemCount(): Int {
        return avistamientos.size
    }
}
