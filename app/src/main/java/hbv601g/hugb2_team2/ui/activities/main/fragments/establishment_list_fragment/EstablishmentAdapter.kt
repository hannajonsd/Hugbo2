package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment;
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import hbv601g.hugb2_team2.entities.Establishment;
import hbv601g.hugb2_team2.session.SessionManager;
import hbv601g.hugb2_team2.ui.activities.establishment.EditEstablishmentActivity;
import hbv601g.hugb2_team2.R;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity;
import android.util.Log;
import android.widget.Toast
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstablishmentAdapter(
    private var dataSet: List<Establishment>,
    private val context: Context,
    private val sessionManager: SessionManager,
    private val onClick: (Establishment) -> Unit
) : RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder>() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewEstablishmentName)
        val textViewAddress: TextView? = view.findViewById(R.id.textViewEstablishmentAddress)
        val buttonEdit: Button = view.findViewById(R.id.buttonEditEstablishment)
        val buttonDelete: Button = view.findViewById(R.id.buttonDeleteEstablishment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_establishment_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val establishment = dataSet[position]
        holder.textViewName.text = establishment.name
        // holder.itemView.setOnClickListener { onClick(establishment) }
        holder.textViewAddress?.text = establishment.address

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleEstablishmentActivity::class.java).apply {
                putExtra("ESTABLISHMENT_ID", establishment.id)
                Log.d("EstablishmentAdapter", "Navigating to SingleEstablishmentActivity with ID: $establishment.id")

            }
            context.startActivity(intent)
        }

        // Set visibility and onClickListener based on user's session status
        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            holder.buttonEdit.visibility = View.VISIBLE
            holder.buttonEdit.setOnClickListener {
                val intent = Intent(context, EditEstablishmentActivity::class.java).apply {
                    putExtra("ESTABLISHMENT_ID", establishment.id)
                }
                context.startActivity(intent)
            }
        } else {
            holder.buttonEdit.visibility = View.GONE
        }

        // Delete button visibility and action
        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonDelete.setOnClickListener {
                showDeleteConfirmation(context, establishment.id)
            }
        } else {
            holder.buttonDelete.visibility = View.GONE
        }

    }

    fun showDeleteConfirmation(context: Context, establishmentID: Long) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete establishment?")
        builder.setMessage("Are you sure you want to delete this establishment?")
        builder.setPositiveButton("Yes") { _, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    establishmentService.deleteEstablishment(establishmentID)
                    Toast.makeText(context, "Establishment deleted", Toast.LENGTH_SHORT).show()

                    dataSet = dataSet.filter { it.id != establishmentID }
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    Log.e("EstablishmentAdapter", "Error deleting drink", e)
                    Toast.makeText(context, "failed to delete establishment", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<Establishment>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
