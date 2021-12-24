package ca.josue.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.josue.naturecollection.fragments.CollectionFragment
import ca.josue.naturecollection.fragments.HomeFragment
import ca.josue.naturecollection.repository.PlantRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // chargé notre repository
        val repository = PlantRepository()

        // mettre à jour les données
        repository.updateData {
            // injecter le fragment dans notre boite
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, CollectionFragment(this))
            transaction.addToBackStack(null) // annuler le retour vers ce composant
            transaction.commit()
        }

    }
}