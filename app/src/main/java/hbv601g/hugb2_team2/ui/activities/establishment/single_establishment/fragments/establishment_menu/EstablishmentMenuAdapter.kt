package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.Establishment


class EstablishmentMenuAdapter(private var beverages: List<Beverage>) : RecyclerView.Adapter<EstablishmentMenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.beverageName)
        val priceTextView: TextView = view.findViewById(R.id.beveragePrice)
        val volumeTextView: TextView = view.findViewById(R.id.beverageVolume)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beverage_list_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beverage = beverages[position]
        holder.nameTextView.text = beverage.id.toString()

        holder.priceTextView.text = "Price: ${beverage.price.toString()}kr"
        holder.volumeTextView.text = "Volume: ${beverage.volume.toString()}ml"

    }

    override fun getItemCount() = beverages.size

    fun updateData(newData: List<Beverage>) {
        beverages = newData
        notifyDataSetChanged()
    }
}
