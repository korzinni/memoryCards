package ru.id_east.gm.utils

import android.app.Activity
import android.content.IntentSender
import android.location.Location
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


object LocationUtil {
    fun locationRequest(
        activity: Activity,
        requestCode: Int,
        success: (Location) -> Unit
    ) {

        val highAccuracyLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((10 * 1000).toLong())
            .setFastestInterval((1 * 1000).toLong())

        val balancedAccuracyLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval((10 * 1000).toLong())
            .setFastestInterval((1 * 1000).toLong())

        val builder =
            LocationSettingsRequest
                .Builder()
                .addLocationRequest(highAccuracyLocationRequest)
                .addLocationRequest(balancedAccuracyLocationRequest)
                .setAlwaysShow(true)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        val result =
            LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

        result.addOnCompleteListener(object : OnCompleteListener<LocationSettingsResponse> {

            override fun onComplete(task: Task<LocationSettingsResponse>) {
                try {
                    val response = task.getResult(ApiException::class.java)

                    fusedLocationClient.requestLocationUpdates(
                        highAccuracyLocationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                for (location in locationResult!!.locations) {
                                    success(location)
                                    fusedLocationClient.removeLocationUpdates(this)
                                }
                            }
                        },
                        null
                    )


                } catch (exception: ApiException) {
                    when (exception.getStatusCode()) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                val resolvable = exception as ResolvableApiException
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                    activity,
                                    requestCode
                                )
                            } catch (e: IntentSender.SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                        }
                    }
                }
            }
        });

    }
}