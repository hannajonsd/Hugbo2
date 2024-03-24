package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment;
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.beverage.BeverageListActivity
import hbv601g.hugb2_team2.ui.activities.establishment.EditEstablishmentActivity
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.ui.activities.drinktype.EditDrinkTypeActivity


class DrinkTypeAdapter(private var dataSet: List<DrinkType>,
                       private val context: Context,
                       private val sessionManager: SessionManager,
                       private val onClick: (DrinkType) -> Unit

) : RecyclerView.Adapter<DrinkTypeAdapter.DrinkTypeViewHolder>() {

        private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
        class DrinkTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
                val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
                val subtypeTextView: TextView = itemView.findViewById(R.id.subtypeTextView)
                val percentageTextView: TextView = itemView.findViewById(R.id.percentageTextView)
                val buttonEdit: Button = itemView.findViewById(R.id.buttonEditDrinkType)
                val buttonDelete: Button = itemView.findViewById(R.id.buttonDeleteDrinkType)
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkTypeViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_drinktype_list, parent, false)
                return DrinkTypeViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: DrinkTypeViewHolder, position: Int) {
                val currentItem = dataSet[position]
                holder.nameTextView.text = currentItem.name
                holder.typeTextView.text = currentItem.type
                holder.subtypeTextView.text = currentItem.subType
                holder.percentageTextView.text = currentItem.percentage.toString()

                holder.itemView.setOnClickListener {
                        val intent = Intent(context, BeverageListActivity::class.java).apply {
                                putExtra("DRINKTYPE_ID", currentItem.id)
                                Log.d("EstablishmentAdapter", "Navigating to beveragelist with ID: $currentItem.id")
                        }
                        context.startActivity(intent)
                }

                // Set visibility and onClickListener based on user's session status
                if (sessionManager.isLoggedIn() && sessionManager.isAdmin()) {
                        holder.buttonEdit.visibility = View.VISIBLE
                        holder.buttonEdit.setOnClickListener {
                                val intent = Intent(context, EditDrinkTypeActivity::class.java).apply {
                                        putExtra("DRINKTYPE_ID", currentItem.id)
                                }
                                context.startActivity(intent)
                        }
                } else {
                        holder.buttonEdit.visibility = View.GONE
                }

                // Delete button visibility and action
                if (sessionManager.isLoggedIn() && sessionManager.isAdmin()) {
                        holder.buttonDelete.visibility = View.VISIBLE
                        holder.buttonDelete.setOnClickListener {
                                showDeleteConfirmation(context, currentItem.id)
                        }
                } else {
                        holder.buttonDelete.visibility = View.GONE
                }
        }

        override fun getItemCount() = dataSet.size

        fun updateData(newData: List<DrinkType>) {
                dataSet = newData
                notifyDataSetChanged()
        }
        @SuppressLint("NotifyDataSetChanged")
        fun showDeleteConfirmation(context: Context, drinkTypeId: Long) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete DrinkType?")
                builder.setMessage("Are you sure you want to delete this drinktype?")
                builder.setPositiveButton("Yes") { _, _ ->
                        CoroutineScope(Dispatchers.Main).launch {
                                try {
                                        drinkTypeService.deleteDrinkType(drinkTypeId)
                                        Toast.makeText(context, "DrinkType deleted", Toast.LENGTH_SHORT).show()

                                        dataSet = dataSet.filter { it.id != drinkTypeId }
                                        notifyDataSetChanged()
                                } catch (e: Exception) {
                                        Log.e("Drinktypeactivity", "Error deleting drinktype", e)
                                        Toast.makeText(context, "failed to delete drink", Toast.LENGTH_SHORT).show()
                                }
                        }
                }
                builder.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
        }

}
