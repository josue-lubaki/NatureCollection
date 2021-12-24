package ca.josue.naturecollection.repository

import android.net.Uri
import ca.josue.naturecollection.model.PlantModel
import ca.josue.naturecollection.repository.PlantRepository.Singleton.databaseRef
import ca.josue.naturecollection.repository.PlantRepository.Singleton.downloadUri
import ca.josue.naturecollection.repository.PlantRepository.Singleton.fileName
import ca.josue.naturecollection.repository.PlantRepository.Singleton.plantList
import ca.josue.naturecollection.repository.PlantRepository.Singleton.storageRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PlantRepository {

    object Singleton{
        // donner le lien pour acceder au bucket
        private const val BUCKET_URL: String = "gs://naturecollection-7a6b8.appspot.com"

        // Se connecter à l'espace de stockage
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Se connecter à la référence "Plants" de Firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        lateinit var downloadUri : Uri
        lateinit var fileName : String
    }

    /**
     * Methode qui permet de mettre à jour les données en temps réel
     * @param callback une fonction qui sera éxecutée à un moment bien précise
     * */
    fun updateData(callback: () -> Unit){
        // absorber les données depuis la databaseRef
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retirer les anciennes plantes
                plantList.clear()

                // recolter la liste
                for (dataSnapshot in snapshot.children){
                    // construire un object plantModel
                    val plant = dataSnapshot.getValue(PlantModel::class.java)

                    // vérifier si la plante a bien été chargé
                    if(plant != null){
                        plantList.add(plant)
                    }
                }

                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /**
     * Function qui permet de mettre à jour une plante en particulier
     * @param plant : la plante à mettre à jour
     * */
    fun updatePlant(plant : PlantModel) = databaseRef.child(plant.id).setValue(plant)

    /**
     * Function qui permet d'inserer une plante
     * @param plant : la plante à insérer
     * */
    fun insertPlant(plant : PlantModel) = databaseRef.child(plant.id).setValue(plant)

    /**
     * Function qui permet de supprimer une plante en particulier
     * @param plant : la plante à supprimer
     * */
    fun deletePlant(plant : PlantModel){
        deleteImage(plant)
        databaseRef.child(plant.id).removeValue()
    }

    /**
     * Methode qui permet d'evoyer le fichier dans l'espace de stockage
     * @param file le path où se trouve l'image dans le telephone
     * @param callback l'Action à éxecuter après la reussite de la sauvergarde
     * */
    fun uploadImage(file: Uri, callback: () -> Unit){
        fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storageRef.child(fileName)
        ref.putFile(file).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                downloadUri = it
                callback()
            }
        }.addOnFailureListener {
                print(it.message)
            }
    }

    /**
     * Methode qui permet de supprimer l'image d'une plante
     * */
    private fun deleteImage(plantTodDelete: PlantModel){
        val fileName = plantTodDelete.fileName
        storageRef.child(fileName).delete()
    }

}

