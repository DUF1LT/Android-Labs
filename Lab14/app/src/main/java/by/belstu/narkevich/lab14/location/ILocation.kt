package by.belstu.narkevich.lab14.location

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.tasks.Task

interface ILocation {
    fun getFineLocationTask(runOnce: Boolean): Task<Location>
    fun getCoarseLocationTask(runOnce: Boolean): Task<Location>
    fun updateLocationTask(locationCallback: LocationCallback)
    fun stopLocationUpdates(locationCallback: LocationCallback)
}