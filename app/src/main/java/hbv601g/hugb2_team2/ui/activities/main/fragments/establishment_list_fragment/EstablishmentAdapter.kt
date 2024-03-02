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

class EstablishmentAdapter(
    private var dataSet: List<Establishment>,
    private val context: Context, // Pass Context here
    private val sessionManager: SessionManager // Pass SessionManager here
) : RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewEstablishmentName)
        val textViewAddress: TextView = view.findViewById(R.id.textViewEstablishmentAddress)
        val buttonEdit: Button = view.findViewById(R.id.buttonEditEstablishment) // Reference to your Edit button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_establishment_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val establishment = dataSet[position]
        holder.textViewName.text = establishment.name
        holder.textViewAddress.text = establishment.address

        // Set visibility and onClickListener based on user's session status
        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            holder.buttonEdit.visibility = View.VISIBLE
            holder.buttonEdit.setOnClickListener {
                // Assuming you're passing the establishment ID to edit it
                val intent = Intent(context, EditEstablishmentActivity::class.java).apply {
                    putExtra("ESTABLISHMENT_ID", establishment.id)
                }
                context.startActivity(intent)
            }
        } else {
            holder.buttonEdit.visibility = View.GONE
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<Establishment>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
