package hbv601g.hugb2_team2.ui.activities.establishment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.EstablishmentWithDistance

class NearbyEstablishmentsAdapter(
    private val nearbyEstablishments: List<EstablishmentWithDistance>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NearbyEstablishmentsAdapter.EstablishmentViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(establishment: EstablishmentWithDistance)
    }

    inner class EstablishmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(nearbyEstablishments[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.establishment_item, parent, false)
        return EstablishmentViewHolder(itemView)
    }

    override fun getItemCount() = nearbyEstablishments.size

    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        val currentEstablishment = nearbyEstablishments[position]

        holder.itemView.findViewById<TextView>(R.id.textViewEstablishmentName).text = currentEstablishment.establishment.name

        val distanceInKm = String.format("%.1f km", currentEstablishment.distance)
        holder.itemView.findViewById<TextView>(R.id.textViewEstablishmentDistance).text = distanceInKm
    }
}