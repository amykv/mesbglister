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

    const val SETTINGS_SELECTION: String = "Settings"

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

    fun settingTypes():ArrayList<String>{
        val list = ArrayList<String>()
//        list.add("About")
//        list.add("Privacy Policy")
        list.add("Privacy Policy\n" +
                "\n" +
                "Aras Vitkus/Avitkdev built the MESBGLister app as a Free app. This SERVICE is provided by Aras Vitkus/Avitkdev at no cost and is intended for use as is.\n" +
                "\n" +
                "This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.\n" +
                "\n" +
                "If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which are accessible at MESBGLister unless otherwise defined in this Privacy Policy.\n" +
                "\n" +
                "Information Collection and Use\n" +
                "\n" +
                "For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way.\n" +
                "\n" +
                "The app does use third-party services that may collect information used to identify you.\n" +
                "\n" +
                "Link to the privacy policy of third-party service providers used by the app\n" +
                "\n" +
                "    Google Play Services\n" +
                "\n" +
                "Log Data\n" +
                "\n" +
                "I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third-party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.\n" +
                "\n" +
                "Cookies\n" +
                "\n" +
                "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                "\n" +
                "This Service does not use these “cookies” explicitly. However, the app may use third-party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.\n" +
                "\n" +
                "Service Providers\n" +
                "\n" +
                "I may employ third-party companies and individuals due to the following reasons:\n" +
                "\n" +
                "    To facilitate our Service;\n" +
                "    To provide the Service on our behalf;\n" +
                "    To perform Service-related services; or\n" +
                "    To assist us in analyzing how our Service is used.\n" +
                "\n" +
                "I want to inform users of this Service that these third parties have access to their Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
                "\n" +
                "Security\n" +
                "\n" +
                "I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.\n" +
                "\n" +
                "Links to Other Sites\n" +
                "\n" +
                "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
                "\n" +
                "Children’s Privacy\n" +
                "\n" +
                "These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13 years of age. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do the necessary actions.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "\n" +
                "I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                "\n" +
                "This policy is effective as of 2022-08-11\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at avitkdev@gmail.com. ")
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