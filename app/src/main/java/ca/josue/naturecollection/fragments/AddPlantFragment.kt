package ca.josue.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ca.josue.naturecollection.MainActivity
import ca.josue.naturecollection.R
import ca.josue.naturecollection.model.PlantModel
import ca.josue.naturecollection.repository.PlantRepository
import ca.josue.naturecollection.repository.PlantRepository.Singleton.downloadUri
import java.util.*

class AddPlantFragment(
    private val context : MainActivity
    ) : Fragment() {

    companion object{
        const val CODE_RETURN = 55
    }

    private lateinit var uploadedImage:ImageView
    private lateinit var file : Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        // Récupèrer des composants
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        uploadedImage = view.findViewById(R.id.preview_image)

        // intéraction pour ouvrir les images du téléphones
        pickupImageButton.setOnClickListener {
            pickupImage()
        }

        // Récupèrer le button pour confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            sendForm(view)
        }

        return view
    }

    /**
     * Methode qui permet d'envoyer le formulaire pour la sauvergarde d'une plante
     * @param view la vue sur laquelle récupèrer les composants
     * */
    private fun sendForm(view: View) {
        // Récupèrer le repository
        val repository = PlantRepository()
        repository.uploadImage(file){
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadUrl = downloadUri.toString()

            // créer un nouvel object de type plantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadUrl,
                false,
                grow,
                water
            )

            // envoyer à la base de données
            repository.insertPlant(plant)
        }
    }

    /**
     * Methode qui permet d'ouvrir une action d'ouverture de la gallerie
     * */
    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE_RETURN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_RETURN && resultCode == Activity.RESULT_OK ){
            // vérifier si les données sont nulles
            if(data == null || data.data == null)
                return

            // Récupèrer l'image selectionnée (ne sera jamais NULL)
            file = data.data!!
            uploadedImage.setImageURI(file)
        }
    }

}