package by.belstu.narkevich.lab14.location

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task

class LocationService() : Service(), ILocation {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var client: SettingsClient

    private val cancellationSource = CancellationTokenSource()
    private var locationRequest: LocationRequest
    private val locationSettingsRequest: LocationSettingsRequest

    init {
        locationRequest = LocationRequest.create()
        locationRequest.setInterval(100)
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun getFineLocationTask(runOnce: Boolean): Task<Location> {
        if (runOnce) {
            return fusedLocationClient.getLastLocation()
        }
        return fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationSource.token
        )
    }

    override fun getCoarseLocationTask(runOnce: Boolean): Task<Location> {
        if (runOnce) {
            return fusedLocationClient.getLastLocation()
        }
        return fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            cancellationSource.token
        )
    }

    override fun updateLocationTask(locationCallback: LocationCallback) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }
}