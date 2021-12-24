package ca.josue.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ca.josue.naturecollection.MainActivity
import ca.josue.naturecollection.R

class AddPlantFragment(
    private val context : MainActivity
    ) : Fragment() {

    companion object{
        const val CODE_RETURN = 55
    }

    private lateinit var uploadedImage:ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        // Récupèrer le button pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        // mettre à jour l'aperçue de l'image
        uploadedImage = view.findViewById(R.id.preview_image)

        // intéraction pour ouvri les images du téléphones
        pickupImageButton.setOnClickListener {
            pickupImage()
        }

        return view
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

            // Récupèrer l'image selectionnée
            val selectedImage = data.data
            uploadedImage.setImageURI(selectedImage)

        }
    }

}