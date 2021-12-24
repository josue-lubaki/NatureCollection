package ca.josue.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.josue.naturecollection.fragments.AddPlantFragment
import ca.josue.naturecollection.fragments.CollectionFragment
import ca.josue.naturecollection.fragments.HomeFragment
import ca.josue.naturecollection.repository.PlantRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragment par defaut
        loadFragment(HomeFragment(this), R.string.home_page_title)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_page -> {
                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    /**
     * Méthode qui permet de changer les fragments
     * @param fragment le fragment à replacer
     * @param titlePage l'identifiant de la textView qui fait reference au nom de la page
     * */
    private fun loadFragment(fragment: Fragment, titlePage : Int) {
        // chargé notre repository
        val repository = PlantRepository()

        // actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(titlePage)

        // mettre à jour les données
        repository.updateData {
            // injecter le fragment dans notre boite
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null) // annuler le retour vers ce composant
            transaction.commit()
        }
    }
}