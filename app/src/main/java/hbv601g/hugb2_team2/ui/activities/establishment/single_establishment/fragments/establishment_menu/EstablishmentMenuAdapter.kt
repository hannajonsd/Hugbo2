package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import kotlinx.coroutines.CoroutineScope

class EstablishmentMenuAdapter(private var beverages: List<Beverage>,
                               private val sessionManager: SessionManager,
                               private val coroutineScope: CoroutineScope,)
    : RecyclerView.Adapter<EstablishmentMenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.beverageName)
        val priceTextView: TextView = view.findViewById(R.id.beveragePrice)
        val volumeTextView: TextView = view.findViewById(R.id.beverageVolume)
        val editButton: Button = view.findViewById(R.id.editButton)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beverage_list_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beverage = beverages[position]
        // holder.nameTextView.text = DrinkTypeServiceProvider.getDrinkTypeService().getDrinkTypesByType(beverage.drinkType.toString()).name

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val drinkTypeName = DrinkTypeServiceProvider.getDrinkTypeService().getDrinkTypeById(beverage.drinkType.id)?.name
                withContext(Dispatchers.Main) {
                    holder.nameTextView.text = drinkTypeName
                }
            } catch (e: Exception) {
                Log.e("EstablishmentMenuAdapter", "Error fetching drink type name", e)
            }
        }

        holder.priceTextView.text = "Price: ${beverage.price.toString()} kr"
        holder.volumeTextView.text = "Volume: ${beverage.volume.toString()} ml"


        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            holder.editButton.visibility = View.VISIBLE

        } else {
            holder.editButton.visibility =  View.GONE
        }

        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            holder.deleteButton.visibility = View.VISIBLE

        } else {
            holder.deleteButton.visibility =  View.GONE
        }



    }

    override fun getItemCount() = beverages.size

    fun updateData(newData: List<Beverage>) {
        beverages = newData
        notifyDataSetChanged()
    }
}
