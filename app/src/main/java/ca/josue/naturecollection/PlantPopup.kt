package ca.josue.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import ca.josue.naturecollection.adapter.PlantAdapter
import ca.josue.naturecollection.model.PlantModel
import ca.josue.naturecollection.repository.PlantRepository
import com.bumptech.glide.Glide

class PlantPopup(
    private val adapter : PlantAdapter,
    private val currentPlant: PlantModel
    ) : Dialog(adapter.context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // sur la fenetre popup, on ne veut pas de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plant_details)
        setupComponent()
        setupCloseButton()
        setupDeletePlant()
        setupStartButton()
    }

    /**
     * Methode qui permet de mettre à jour le button dépendament de état
     * @param button : L"imageView sur lequel affecter le changement
     * */
    private fun updatestart(button : ImageView){
        if(currentPlant.liked){
            button.setImageResource(R.drawable.ic_star)
        }
        else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    /**
     * Methode qui permet de mettre à jour l'état du button Like sur le popup
     * */
    private fun setupStartButton() {
        val startButton = findViewById<ImageView>(R.id.star_button)
        updatestart(startButton)

        startButton.setOnClickListener {
            currentPlant.liked = !currentPlant.liked
            val repository = PlantRepository()
            repository.updatePlant(currentPlant)
            updatestart(startButton)
        }

    }

    /**
     * Methode qui permet de supprimer une plante lorsqu'on clique sur le button delete du popup
     * */
    private fun setupDeletePlant() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            // supprimer la plante de la base de données
            val repository = PlantRepository()
            repository.deletPlant(currentPlant)
            dismiss()
        }
    }

    /**
     * Methode qui permet de fermer le popup lorsqu'on clique sur le close
     * */
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            dismiss()
        }
    }

    /**
     * Methode qui permet de mettre à jour les informations de la plante sur le popup
     * */
    private fun setupComponent() {
        // actualiser les informations de la plante au niveau du popup
        val plantImage : ImageView = findViewById(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description
        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text = currentPlant.grow
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.water
    }

}