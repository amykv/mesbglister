package com.arasvitkus.mesbglister.utils

import android.hardware.biometrics.BiometricManager

object Constants {

    const val ARMY_TYPE: String = "ArmyType"
    const val ARMY_FACTION: String = "ArmyFaction"
    const val ARMY_POINTS: String = "ArmyPoints"

    const val ARMY_IMAGE_SOURCE_LOCAL : String = "Local"
    const val ARMY_IMAGE_SOURCE_ONLINE : String = "Online"

    const val EXTRA_ARMY_DETAILS: String = "ArmyDetails"

    const val ALL_ITEMS: String = "All"
    const val FILTER_SELECTION: String = "FilterSelection"

    fun armyTypes():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Good")
        list.add("Evil")
        return list
    }

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
        //Hobbit Good/Evil to be added later
        return list
    }

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