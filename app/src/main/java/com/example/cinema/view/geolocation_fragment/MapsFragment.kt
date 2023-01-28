package com.example.cinema.view.geolocation_fragment

import android.Manifest
import android.R.attr.radius
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMapsBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding?=null
    private val binding get() = _binding!!
    private lateinit var  map: GoogleMap
    private val markers: ArrayList<Marker> =arrayListOf()


    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val initialPlace = LatLng(52.52, 13.404)
        googleMap.addMarker(MarkerOptions().position(initialPlace).title(getString(R.string.marker_start)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        googleMap.setOnMapLongClickListener {latLng ->
           getAdreessAsync(latLng)
            addMarkerToArray(latLng)
            drawLine()
        }
    }

    private fun drawLine() {
        val last: Int = markers.size -1
        if(last>=1){
            val previous: LatLng = markers[last-1].position
            val current: LatLng =markers[last].position
            map.addPolyline(PolylineOptions().add(previous, current).color(Color.RED).width(5f))
        }
    }

    private fun addMarkerToArray(location: LatLng) {
val marker = setMarker(location, markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun setMarker(location: LatLng, searchText: String, resourceId: Int): Marker {
return map.addMarker(
    MarkerOptions()
        .position(location)
        .title(searchText)
        .icon(BitmapDescriptorFactory.fromResource(resourceId))
)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding=FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var country = ""
        country = arguments?.getString(COUNTRY_EXTRA) ?: String()
        if(country != "") {
            binding.searchAddress.setText(country)}
        checkPermission(country)
    }

    private fun initSearchByAddress(country: String) {

        binding.buttonSearch.setOnClickListener{
            val geoCoder = Geocoder(it.context)
            var searchText = binding.searchAddress.text.toString()
            Thread{
                try{
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses!!.size>0){
                        gotoAddress(addresses, it, searchText)

                    }
                }catch (e: IOException){
                    e.printStackTrace()

                }
            }.start()
        }
    }

    private fun initSearchByCoordinates() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.buttonSearchCoordinate.setOnClickListener{

            val geoCoder = Geocoder(it.context)
            val searchLatitude = binding.searchLatitude.text.toString().toDouble()
            val searchLongitude = binding.searchLongitude.text.toString().toDouble()

            Thread{
                try{
                    val addresses = geoCoder.getFromLocation(searchLatitude, searchLongitude, 1)
                    if (addresses!!.size>0){
                        var strLine = " "
                        for(i in 0..addresses[0].maxAddressLineIndex ){
                            strLine=strLine+addresses[0].getAddressLine(i).toString()
                        }
                        gotoAddress(addresses, it, strLine)

                    }
                }catch (e: IOException){
                    e.printStackTrace()

                }
            }.start()
        }
    }

    private fun gotoAddress(addresses: List<Address>, view : View?, searchText: String) {
val location = LatLng(addresses[0].latitude,addresses[0].longitude)
        view?.post{
            setMarker(location, searchText, R.drawable.ic_map_marker)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }

    }

    private fun getAdreessAsync(location: LatLng){
context?.let{
    val geoCooder = Geocoder(it)
    Thread{
        try{
        val addresses = geoCooder.getFromLocation(location.latitude, location.longitude, 1)
binding.apply {
    textAddress.post{
        textAddress.text = addresses!![0].getAddressLine(0)
    }
}

    }catch (e: IOException){
        e.printStackTrace()
    }
    }.start()
}

}
    companion object {
        const val COUNTRY_EXTRA = "COUNTRY"
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun checkPermission(country: String) {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation(country)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {

                    showRationaleDialog(country)
                }
                else -> {
                    requestPermission(country)
                }
            }
        }
    }

    private fun requestPermission(country: String) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
                isGranted : Boolean ->
            if (isGranted){
                getLocation(country)
            }else{
                showDialog(
                    getString(R.string.dialog_title_no_gps) ,
                    getString(R.string.dialog_message_no_gps)
                )
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }



    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun getLocation(country: String) {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mapFragment?.getMapAsync(callback)
                    initSearchByAddress(country)
                    initSearchByCoordinates()

                } else {
                    showDialog(
                        getString(R.string.dialog_title_gps_turned_off),
                        getString(R.string.dialog_message_last_location_unknown)
                    )
                }
            } else {
                showRationaleDialog(country)
            }
        }
    }

    private fun showRationaleDialog(country: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_rationale_title))
                .setMessage(getString(R.string.dialog_rationale_meaasge))
                .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                    requestPermission(country)
                }
                .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }



}