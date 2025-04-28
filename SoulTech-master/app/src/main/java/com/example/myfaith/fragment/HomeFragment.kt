package com.example.myfaith.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myfaith.datasource.ApiSource
import com.example.myfaith.receiver.NamazAlarmReceiver
import com.example.myfaith.utils.LocationHelper
import com.example.myfaith.utils.NamazTimeStorage
import com.example.mynavigationapp.R
import com.example.mynavigationapp.databinding.HomePageFragmentBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class HomeFragment : Fragment() {

    private var _binding: HomePageFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationHelper: LocationHelper
    private lateinit var namazStorage: NamazTimeStorage

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomePageFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        locationHelper = LocationHelper(requireContext())

        val quranButton = view.findViewById<Button>(R.id.quran_button)
        val quoteButton = view.findViewById<Button>(R.id.quote_button)
        val zikrButton = view.findViewById<Button>(R.id.zikr_button)
        val booksButton = view.findViewById<Button>(R.id.books_button)
        val compassButton = view.findViewById<Button>(R.id.compass_button)
        namazStorage = NamazTimeStorage(requireContext())

        quranButton.setOnClickListener {
            findNavController().navigate(R.id.quranFragment)
        }

        quoteButton.setOnClickListener {
            findNavController().navigate(R.id.quoteFragment)
        }

        zikrButton.setOnClickListener {
            findNavController().navigate(R.id.zikrFragment)
        }

        booksButton.setOnClickListener {
//            findNavController().navigate(R.id.booksFragment)
        }

        compassButton.setOnClickListener {
            findNavController().navigate(R.id.compassFragment)
        }
        val savedDate = namazStorage.getSavedDate()
        val currentDate = LocalDate.now().toString()

        if (savedDate == currentDate) {
            // Load from local
            val times = namazStorage.getTimes()
            binding.fajr.text = "${getString(R.string.fajr)}\n${times["fajr"]}"
            binding.dhuhr.text = "${getString(R.string.dhuhr)}\n${times["dhuhr"]}"
            binding.asr.text = "${getString(R.string.asr)}\n${times["asr"]}"
            binding.maghrib.text = "${getString(R.string.maghrib)}\n${times["maghrib"]}"
            binding.isha.text = "${getString(R.string.isha)}\n${times["isha"]}"

            val (nextPrayerName, nextPrayerTime) = namazStorage.getNextPrayer()

            Log.d("NextPrayer", "Next: $nextPrayerName at $nextPrayerTime")
            val currentTime = LocalTime.now()

            val fajrTime = LocalTime.parse(times["fajr"])
            val dhuhrTime = LocalTime.parse(times["dhuhr"])
            val asrTime = LocalTime.parse(times["asr"])
            val maghribTime = LocalTime.parse(times["maghrib"])
            val ishaTime = LocalTime.parse(times["isha"])

            val timeMap = mapOf(
                "Fajr" to fajrTime,
                "Dhuhr" to dhuhrTime,
                "Asr" to asrTime,
                "Maghrib" to maghribTime,
                "Isha" to ishaTime
            )

            // Find the next prayer time after the current time
            val nextPrayer = timeMap.filterValues { it.isAfter(currentTime) }.minByOrNull { it.value }

            // If the next prayer has changed, update the color and save it again
            nextPrayer?.let { entry ->
                when (entry.key) {
                    "Fajr" -> binding.fajr.setTextColor(Color.GREEN)
                    "Dhuhr" -> binding.dhuhr.setTextColor(Color.GREEN)
                    "Asr" -> binding.asr.setTextColor(Color.GREEN)
                    "Maghrib" -> binding.maghrib.setTextColor(Color.GREEN)
                    "Isha" -> binding.isha.setTextColor(Color.GREEN)
                }

                // Save next prayer details
                namazStorage.saveNextPrayer(entry.key, entry.value.toString())

                scheduleNamazNotification(entry.key, entry.value)

            }



        }
        else if (locationHelper.hasLocationPermission()) {
            locationHelper.getCurrentLocation { location ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude

                    lifecycleScope.launch {
                        try {
                            val response = ApiSource.namazTimeApi.getNamazTimes(latitude, longitude, currentDate)
                            if (response.isSuccessful) {
                                val namazTimes = response.body()
                                namazTimes?.let { times ->
                                    binding.fajr.text = "${getString(R.string.fajr)}\n${times.Fajr}"
//                                    binding.nextEvent.text = "${getString(R.string.sun)}\n${times.Sun}"
                                    binding.dhuhr.text = "${getString(R.string.dhuhr)}\n${times.Dhuhr}"
                                    binding.asr.text = "${getString(R.string.asr)}\n${times.Asr}"
                                    binding.maghrib.text = "${getString(R.string.maghrib)}\n${times.Maghrib}"
                                    binding.isha.text = "${getString(R.string.isha)}\n${times.Isha}"

                                    val fajrTime = LocalTime.parse(times.Fajr)
                                    val dhuhrTime = LocalTime.parse(times.Dhuhr)
                                    val asrTime = LocalTime.parse(times.Asr)
                                    val maghribTime = LocalTime.parse(times.Maghrib)
                                    val ishaTime = LocalTime.parse(times.Isha)

                                    val currentTime = LocalTime.now()

                                    val timeMap = mapOf(
                                        binding.fajr to fajrTime,
                                        binding.dhuhr to dhuhrTime,
                                        binding.asr to asrTime,
                                        binding.maghrib to maghribTime,
                                        binding.isha to ishaTime
                                    )

                                    // Find the next upcoming prayer
                                    val nextPrayer = timeMap
                                        .filterValues { it.isAfter(currentTime) }
                                        .minByOrNull { it.value }

                                    nextPrayer?.let { entry ->
                                        entry.key.setTextColor(Color.GREEN)

                                        val prayerName = when (entry.key) {
                                            binding.fajr -> "Fajr"
                                            binding.dhuhr -> "Dhuhr"
                                            binding.asr -> "Asr"
                                            binding.maghrib -> "Maghrib"
                                            binding.isha -> "Isha"
                                            else -> ""
                                        }

                                        namazStorage.saveNextPrayer(prayerName, entry.value.toString())
                                        scheduleNamazNotification(prayerName, entry.value)
                                    }

                                    val fajr = times.Fajr
                                    val dhuhr = times.Dhuhr
                                    val asr = times.Asr
                                    val maghrib = times.Maghrib
                                    val isha = times.Isha
                                    namazStorage.saveTimes(fajr, dhuhr, asr, maghrib, isha, currentDate)

                                }
                            } else {
                                Toast.makeText(requireContext(), "Failed to load Namaz times", Toast.LENGTH_SHORT).show()
                                Log.e("NamazTime", "API response error: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e("NamazTime", "Exception: ${e.message}", e)
                        }
                    }
                } ?:
                Toast.makeText(requireContext(), "Unable to get location.", Toast.LENGTH_SHORT).show()
                Log.e("Location", "Location is null")
            }
        } else {
            locationHelper.requestLocationPermission(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE)
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1002
        private const val REQUEST_CODE_SCHEDULE_EXACT_ALARM = 1001

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNamazNotification(prayerName: String, time: LocalTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!isExactAlarmPermissionGranted()) {
                requestExactAlarmPermission()
                return
            }
        }

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), NamazAlarmReceiver::class.java).apply {
            putExtra("PRAYER_NAME", prayerName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = LocalDate.now()
        val triggerTimeMillis = ZonedDateTime.of(now, time, ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }

    private fun isExactAlarmPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                val pm = requireContext().packageManager
                val applicationInfo = pm.getPackageInfo(requireContext().packageName, 0).applicationInfo
                applicationInfo?.targetSdkVersion ?: 0 >= Build.VERSION_CODES.S
            } catch (e: Exception) {
                false
            }
        } else {
            true
        }
    }


    private fun requestExactAlarmPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        startActivityForResult(intent, REQUEST_CODE_SCHEDULE_EXACT_ALARM)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCHEDULE_EXACT_ALARM) {
            if (isExactAlarmPermissionGranted()) {
                Toast.makeText(requireContext(), "Permission granted to schedule exact alarm", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}