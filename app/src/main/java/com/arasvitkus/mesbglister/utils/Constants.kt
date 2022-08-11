package com.arasvitkus.mesbglister.utils

/**
 * This is used to define the constant values that can be used throughout the application.
 */
object Constants {

    const val ARMY_TYPE: String = "ArmyType"
    const val ARMY_FACTION: String = "ArmyFaction"
    const val ARMY_POINTS: String = "ArmyPoints"

    const val ARMY_IMAGE_SOURCE_LOCAL : String = "Local"
    //const val ARMY_IMAGE_SOURCE_ONLINE : String = "Online"

    const val EXTRA_ARMY_DETAILS: String = "ArmyDetails"

    const val ALL_ITEMS: String = "All"
    const val FILTER_SELECTION: String = "FilterSelection"

        //Variables uses for testing API Lotr
    //Might try different API.
//    const val API_ENDPOINT: String = "/v2/quote"
//    const val API_KEY: String = "apiKey"
//    const val BASE_URL = "https://the-one-api.dev/"
//
//    const val API_KEY_VALUE: String = "lalala"

    fun armyTypes():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Good")
        list.add("Evil")
        return list
    }

    //List of all army factions, might need to create separate lists for LotR/Hobbit
    fun armyFactions():ArrayList<String>{
        val list = ArrayList<String>()
        //LOTR Good factions below
        list.add("Fangorn")
        list.add("Lothlorien")
        list.add("Minas Tirith")
        list.add("Numenor")
        list.add("Rivendell")
        list.add("Rohan")
        list.add("The Dead of Dunharrow")
        list.add("The Fellowship")
        list.add("The Fiefdoms")
        list.add("The Kingdom of Khazad-Dum")
        list.add("The Misty Mountains")
        list.add("The Rangers")
        list.add("The Shire")
        list.add("Wanderers in the Wild")
        //Mixed faction for break
        list.add("Mixed Alliance")
        //Generic Legendary Legion - maybe not needed?
        list.add("Legendary Legion")
        //LOTR Evil Factions below
        list.add("Angmar")
        list.add("Barad-Dur")
        list.add("Corsairs of Umbar")
        list.add("Far Harad")
        list.add("Isengard")
        list.add("Mordor")
        list.add("Moria")
        list.add("Sharkey's Rogues")
        list.add("The Easterlings")
        list.add("The Serpent Horde")
        list.add("Variags of Khand")
        list.add("Arnor")
        //The Hobbit Good factions below
        list.add("Army of Thror")
        list.add("Erebor Reclaimed")
        list.add("Garrison of Dale")
        list.add("Halls of Thranduil")
        list.add("Radagast's Alliance")
        list.add("The Army of Lake-town")
        list.add("Thorin's Company")
        //The Hobbit Evil factions below
        list.add("Azog's Hunters")
        list.add("Azog's Legion")
        list.add("Dark Powers of Dol Guldur")
        list.add("Desolator of the North")
        list.add("Goblin Town")
        list.add("The Dark Denizens of Mirkwood")
        list.add("The Trolls")
        return list
    }

    //List holding army points value.
    fun armyPoints(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("100")
        list.add("150")
        list.add("200")
        list.add("250")
        list.add("300")
        list.add("350")
        list.add("400")
        list.add("450")
        list.add("500")
        list.add("550")
        list.add("600")
        list.add("650")
        list.add("700")
        list.add("750")
        list.add("800")
        list.add("850")
        list.add("900")
        list.add("950")
        list.add("1000")
        list.add("1050")
        list.add("1100")
        list.add("1150")
        list.add("1200")
        list.add("1250")
        list.add("1300")
        list.add("1350")
        list.add("1400")
        return list
    }
}