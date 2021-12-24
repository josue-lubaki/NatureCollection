package ca.josue.naturecollection

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import ca.josue.naturecollection.adapter.PlantAdapter

class PlantPopup(
    private val adapter : PlantAdapter
    ) : Dialog(adapter.context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // sur la fenetre popup, on ne veut pas de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plant_details)
    }

}