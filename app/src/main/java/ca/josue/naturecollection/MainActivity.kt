package ca.josue.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.josue.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // injecter le fragment dans notre boite
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.addToBackStack(null) // annuler le retour vers ce composant
        transaction.commit()
    }
}