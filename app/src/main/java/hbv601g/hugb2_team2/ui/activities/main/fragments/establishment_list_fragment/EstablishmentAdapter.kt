package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Establishment

class EstablishmentAdapter(private var dataSet: List<Establishment>) :
    RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewEstablishmentName)
        val textViewAddress: TextView? = view.findViewById(R.id.textViewEstablishmentAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_establishment_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val establishment = dataSet[position]
        holder.textViewName.text = establishment.name
        holder.textViewAddress?.text = establishment.address
    }


    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<Establishment>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}
