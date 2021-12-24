package ca.josue.naturecollection.model

class PlantModel(
    val id : String = "plant0",
    val name : String = "Tulipe",
    val description : String = "Petite description",
    val imageUrl : String = "http://graven.yt/plante.jpg",
    val fileName: String = "image",
    var liked : Boolean = false,
    val grow: String = "Moyenne",
    val water : String = "Faible"
)