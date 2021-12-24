package ca.josue.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.josue.naturecollection.MainActivity
import ca.josue.naturecollection.PlantPopup
import ca.josue.naturecollection.R
import ca.josue.naturecollection.model.PlantModel
import ca.josue.naturecollection.repository.PlantRepository
import com.bumptech.glide.Glide

class PlantAdapter(
    val context: MainActivity,
    private val plantList : List<PlantModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<PlantAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupèrer le repository
        val repository  = PlantRepository()
        val currentPlant = plantList[position]

        // utiliser glide pour l'affichage des images
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)
        holder.plantName?.text = currentPlant.name
        holder.plantDescription?.text = currentPlant.description

        if(currentPlant.liked)
            holder.starIcon.setImageResource(R.drawable.ic_star)
        else
            holder.starIcon.setImageResource(R.drawable.ic_unstar)

        // rajouter une intéaction sur l'étoile
        holder.starIcon.setOnClickListener {
            // Si le button est liked, il enleve le like, sinon il like
            currentPlant.liked = !currentPlant.liked

            // mettre à jour l'objet plant
            repository.updatePlant(currentPlant)
        }

        // interaction lors du clic sur une plante
        holder.itemView.setOnClickListener{
            // afficher la popup
            PlantPopup(this).show()
        }
    }

    override fun getItemCount(): Int = plantList.size

    // Boite pour ranger tous les composants à controller
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.image_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val plantDescription: TextView? = view.findViewById(R.id.description_item)
        val starIcon: ImageView = view.findViewById(R.id.star_icon)
    }

}