package by.belstu.narkevich.lab14

import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import by.belstu.narkevich.lab14.data.Route

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import by.belstu.narkevich.lab14.databinding.ActivityMapsBinding
import by.belstu.narkevich.lab14.location.LocationService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationService: LocationService
    private lateinit var currentLocationTask: Task<Location>
    private lateinit var locationCallback: LocationCallback

    private var route: Route = Route(startLocation =  null, finishLocation =  null)
    private var currentLocationMarker: Marker? = null
    private var updateMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationService = LocationService()
        locationService.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationService.client = LocationServices.getSettingsClient(this)

        locationCallback = object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val locations = p0.locations
                val location = locations.get(0)
                Log.v("Updated Location", "${location.latitude}  ${location.longitude}")
                processLocation(location)
            }
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                when {
                    permissions.getOrDefault(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        false
                    ) -> {
                        currentLocationTask = locationService.getFineLocationTask(false)
                        locationService.updateLocationTask(locationCallback)
                    }
                    permissions.getOrDefault(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                    ) -> {
                        currentLocationTask = locationService.getCoarseLocationTask(false)
                        locationService.updateLocationTask(locationCallback)
                    }
                    else -> {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            1
                        )
                    }
                }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION))

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager4.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        currentLocationTask.addOnSuccessListener {
            if(currentLocationMarker == null) {
                val currentLocation = LatLng(it.latitude, it.longitude)
                currentLocationMarker = mMap.addMarker(
                    MarkerOptions().position(currentLocation).title("First position")
                )
                route.startLocation = it
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                Log.v("First Location", "${currentLocation.latitude}  ${currentLocation.longitude}")
            }
        }
    }

    private fun processLocation(location: Location) {
        val currentLocation = LatLng(location.latitude, location.longitude)
        binding.speedText.setText(location.speed.toString())

        if(updateMarker == null) {
            updateMarker = mMap.addMarker(
                MarkerOptions().position(currentLocation).title("Updated position")
            )

            return
        }
        updateMarker?.position = currentLocation
    }
}