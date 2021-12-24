package ca.josue.naturecollection.repository

import ca.josue.naturecollection.model.PlantModel
import ca.josue.naturecollection.repository.PlantRepository.Singleton.databaseRef
import ca.josue.naturecollection.repository.PlantRepository.Singleton.plantList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlantRepository {

    object Singleton{
        // Se connecter à la référence "Plants" de Firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()
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

}