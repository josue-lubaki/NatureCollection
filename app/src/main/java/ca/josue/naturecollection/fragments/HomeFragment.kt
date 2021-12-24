package ca.josue.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ca.josue.naturecollection.MainActivity
import ca.josue.naturecollection.R
import ca.josue.naturecollection.adapter.PlantAdapter
import ca.josue.naturecollection.adapter.PlantItemDecoration
import ca.josue.naturecollection.model.PlantModel

class HomeFragment(private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        // créer une liste qui stocke les plantes
        val plantList = arrayListOf<PlantModel>()
        plantList.add(
            PlantModel(
                "Pissenlit",
                "jaune soleil",
                "https://cdn.pixabay.com/photo/2012/08/07/11/21/flower-53800_960_720.jpg",
                false
            ))

        plantList.add(
            PlantModel(
                "Rose",
                "ça pique un peu",
                "https://cdn.pixabay.com/photo/2018/11/08/12/02/rose-3802424_960_720.jpg",
                true
            ))

        plantList.add(
            PlantModel(
                "Cactus",
                "ça pique beaucoup",
                "https://cdn.stocksnap.io/img-thumbs/960w/cactus-plant_BWY5CBN6F9.jpg",
                false
            ))

        plantList.add(
            PlantModel(
                "Tulipe",
                "c'est beau",
                "https://cdn.pixabay.com/photo/2017/02/15/13/40/tulips-2068692_960_720.jpg",
                true
            ))

        // récupérer le RecyclerView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerview)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_horizontal_plant)

        // récupérer le second recycleview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerview)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())



        return view
    }



}