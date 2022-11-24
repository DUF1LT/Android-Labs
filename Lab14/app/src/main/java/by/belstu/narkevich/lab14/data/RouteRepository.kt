package by.belstu.narkevich.lab14.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class RouteRepository(private val context: Context): IRouteRepository {

    override fun saveRoute(newRoute: Route) {
        var routes = getRoutes()
        if(routes.filter({ route -> route.id == newRoute.id }).count() != 0) {
            updateRoute(newRoute)
            return
        }
        routes.add(newRoute)
        saveJSON(routes)
    }

    override fun getRoutes(): ArrayList<Route> {
        val directoryPath = context.applicationInfo.dataDir + "/json"
        val filePath = "routes.json"
        val itemType = object : TypeToken<ArrayList<Route>>() {}.type

        var file = File(directoryPath + "/" + filePath)
        if (file.exists()) {
            val rawString = file.readText()
            val gson = Gson()
            return gson.fromJson(rawString, itemType)
        }
        return ArrayList()
    }

    override fun updateRoute(updatedRoute: Route) {
        val routes = getRoutes()
        var updatedRoutes: ArrayList<Route> = ArrayList(routes.filter { route -> route.id != updatedRoute.id })
        updatedRoutes.add(updatedRoute)
        saveJSON(updatedRoutes)
    }

    private fun saveJSON(cars: ArrayList<Route>) {
        val directoryPath = context.applicationInfo.dataDir + "/json"
        val filePath = "routes.json"
        try {
            val directory = File(directoryPath)
            if (!directory.exists()) {
                directory.mkdir()
            }

            PrintWriter(FileWriter(directoryPath + "/" + filePath)).use {
                val gson = Gson()
                val jsonString = gson.toJson(cars)
                it.write(jsonString)
                Log.i("Path", directoryPath + filePath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}