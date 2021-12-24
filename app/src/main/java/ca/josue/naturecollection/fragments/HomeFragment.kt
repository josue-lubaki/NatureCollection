package ca.josue.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ca.josue.naturecollection.R
import ca.josue.naturecollection.adapter.PlantAdapter
import ca.josue.naturecollection.adapter.PlantItemDecoration

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        // récupérer le RecyclerView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerview)
        horizontalRecyclerView.adapter = PlantAdapter(R.layout.item_horizontal_plant)

        // récupérer le second recycleview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerview)
        verticalRecyclerView.adapter = PlantAdapter(R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())



        return view
    }



}