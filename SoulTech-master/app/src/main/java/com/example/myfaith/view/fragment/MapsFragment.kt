package com.example.myfaith.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myfaith.model.utils.LocationHelper
import com.example.myfaith.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*
import com.google.android.material.appbar.AppBarLayout

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationHelper: LocationHelper
    private lateinit var placesClient: PlacesClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        locationHelper = LocationHelper(requireContext())
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        Places.initialize(requireContext(), getString(R.string.google_maps_api_key))
        placesClient = Places.createClient(requireContext())
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val almaty = LatLng(43.2565, 76.9284)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(almaty, 12f))

        if (locationHelper.hasLocationPermission()) {
            enableMyLocation()
        } else {
            locationHelper.requestLocationPermission(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun enableMyLocation() {
        if (locationHelper.hasLocationPermission()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            googleMap.isMyLocationEnabled = true
            locationHelper.getCurrentLocation { location ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
                    searchForPrayerPlaces(listOf("Мешіт", "намаз", "мечеть"), userLatLng)
                }
            }
        }
    }

    private fun searchForPrayerPlaces(queries: List<String>, location: LatLng) {
        googleMap.clear()
        queries.forEach { query ->
            val radius = 500.0
            val northEast = LatLng(location.latitude + radius / 111000.0, location.longitude + radius / (111000.0 * Math.cos(location.latitude * Math.PI / 180.0)))
            val southWest = LatLng(location.latitude - radius / 111000.0, location.longitude - radius / (111000.0 * Math.cos(location.latitude * Math.PI / 180.0)))
            val bounds = RectangularBounds.newInstance(southWest, northEast)

            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setLocationBias(bounds)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    response.autocompletePredictions.forEach { prediction ->
                        val fetchPlaceRequest = FetchPlaceRequest.newInstance(
                            prediction.placeId,
                            listOf(Place.Field.NAME, Place.Field.LAT_LNG)
                        )
                        placesClient.fetchPlace(fetchPlaceRequest)
                            .addOnSuccessListener { placeResponse ->
                                placeResponse.place.latLng?.let { latLng ->
                                    googleMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(placeResponse.place.name)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                    )
                                }
                            }
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(requireView(), "Failed to search prayer places.", Snackbar.LENGTH_LONG).show()
                }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

}
