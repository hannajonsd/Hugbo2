package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment;
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

class EstablishmentAdapter(
    private var dataSet: List<Establishment>,
    private val context: Context,
    private val sessionManager: SessionManager,
    private val onClick: (Establishment) -> Unit
) : RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder>() {

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
           /* holder.buttonDelete.setOnClickListener {
                onDeleteClicked(establishment)
            }*/
        } else {
            holder.buttonDelete.visibility = View.GONE
        }

    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<Establishment>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
