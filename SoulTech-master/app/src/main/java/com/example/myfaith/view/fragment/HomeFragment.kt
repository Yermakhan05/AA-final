package com.example.myfaith.view.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
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
import com.example.myfaith.AppDatabase
import com.example.myfaith.PrayerTimeRepository
import com.example.myfaith.R
import com.example.myfaith.databinding.HomePageFragmentBinding
import com.example.myfaith.model.datasource.ApiSource
import com.example.myfaith.receiver.NamazAlarmReceiver
import com.example.myfaith.model.utils.LocationHelper
import com.example.myfaith.model.utils.NamazTimeStorage
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
    private lateinit var prayerTimeRepository: PrayerTimeRepository


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomePageFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val dao = AppDatabase.getInstance(requireContext()).prayerTimeDao()
        prayerTimeRepository = PrayerTimeRepository(dao)


        locationHelper = LocationHelper(requireContext())
        namazStorage = NamazTimeStorage(requireContext())

        val quranButton = view.findViewById<Button>(R.id.quran_button)
        val quoteButton = view.findViewById<Button>(R.id.quote_button)
        val zikrButton = view.findViewById<Button>(R.id.zikr_button)
        val compassButton = view.findViewById<Button>(R.id.compass_button)
        val booksButton = view.findViewById<Button>(R.id.books_button)

        quranButton.setOnClickListener { findNavController().navigate(R.id.quranFragment) }
        quoteButton.setOnClickListener { findNavController().navigate(R.id.quoteFragment) }
        zikrButton.setOnClickListener { findNavController().navigate(R.id.zikrFragment) }
        compassButton.setOnClickListener { findNavController().navigate(R.id.compassFragment) }

        quranButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quranFragment)
        }

        quoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quoteFragment)
        }

        zikrButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_zikrFragment)
        }

        booksButton.setOnClickListener {
            findNavController().navigate(R.id.booksFragment)
        }

        compassButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_compassFragment)
        }
        val savedDate = namazStorage.getSavedDate()
        val currentDate = LocalDate.now().toString()

        if (savedDate == currentDate) {
            val times = namazStorage.getTimes()
            binding.fajr.text = "${getString(R.string.fajr)}\n${times["fajr"]}"
            binding.dhuhr.text = "${getString(R.string.dhuhr)}\n${times["dhuhr"]}"
            binding.asr.text = "${getString(R.string.asr)}\n${times["asr"]}"
            binding.maghrib.text = "${getString(R.string.maghrib)}\n${times["maghrib"]}"
            binding.isha.text = "${getString(R.string.isha)}\n${times["isha"]}"

            val currentTime = LocalTime.now()
            val timeMap = mapOf(
                "Fajr" to LocalTime.parse(times["fajr"]),
                "Dhuhr" to LocalTime.parse(times["dhuhr"]),
                "Asr" to LocalTime.parse(times["asr"]),
                "Maghrib" to LocalTime.parse(times["maghrib"]),
                "Isha" to LocalTime.parse(times["isha"])
            )

            val nextPrayer = timeMap.filterValues { it.isAfter(currentTime) }.minByOrNull { it.value }
            nextPrayer?.let { entry ->
                when (entry.key) {
                    "Fajr" -> binding.fajr.setTextColor(Color.GREEN)
                    "Dhuhr" -> binding.dhuhr.setTextColor(Color.GREEN)
                    "Asr" -> binding.asr.setTextColor(Color.GREEN)
                    "Maghrib" -> binding.maghrib.setTextColor(Color.GREEN)
                    "Isha" -> binding.isha.setTextColor(Color.GREEN)
                }

                namazStorage.saveNextPrayer(entry.key, entry.value.toString())
                scheduleNamazNotification(entry.key, entry.value)
            }
        } else if (locationHelper.hasLocationPermission()) {
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
                                    binding.dhuhr.text = "${getString(R.string.dhuhr)}\n${times.Dhuhr}"
                                    binding.asr.text = "${getString(R.string.asr)}\n${times.Asr}"
                                    binding.maghrib.text = "${getString(R.string.maghrib)}\n${times.Maghrib}"
                                    binding.isha.text = "${getString(R.string.isha)}\n${times.Isha}"

                                    val timeMap = mapOf(
                                        binding.fajr to LocalTime.parse(times.Fajr),
                                        binding.dhuhr to LocalTime.parse(times.Dhuhr),
                                        binding.asr to LocalTime.parse(times.Asr),
                                        binding.maghrib to LocalTime.parse(times.Maghrib),
                                        binding.isha to LocalTime.parse(times.Isha)
                                    )

                                    val currentTime = LocalTime.now()
                                    val nextPrayer = timeMap.filterValues { it.isAfter(currentTime) }.minByOrNull { it.value }

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

                                    namazStorage.saveTimes(
                                        times.Fajr, times.Dhuhr, times.Asr, times.Maghrib, times.Isha, currentDate
                                    )
                                }
                            } else {
                                Toast.makeText(requireContext(), "Ошибка загрузки времени намаза", Toast.LENGTH_SHORT).show()
                                Log.e("NamazTime", "Response error: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            // если API недоступен, загружаем из локальной БД
                            lifecycleScope.launch {
                                prayerTimeRepository.getPrayerTimes(currentDate, latitude, longitude).collect { localTimes ->
                                    if (localTimes.isNotEmpty()) {
                                        val time = localTimes.first()

                                        binding.fajr.text = "${getString(R.string.fajr)}\n${time.fajr}"
                                        binding.dhuhr.text = "${getString(R.string.dhuhr)}\n${time.dhuhr}"
                                        binding.asr.text = "${getString(R.string.asr)}\n${time.asr}"
                                        binding.maghrib.text = "${getString(R.string.maghrib)}\n${time.maghrib}"
                                        binding.isha.text = "${getString(R.string.isha)}\n${time.isha}"

                                        // установить следующее время намаза
                                        val timeMap = mapOf(
                                            binding.fajr to LocalTime.parse(time.fajr),
                                            binding.dhuhr to LocalTime.parse(time.dhuhr),
                                            binding.asr to LocalTime.parse(time.asr),
                                            binding.maghrib to LocalTime.parse(time.maghrib),
                                            binding.isha to LocalTime.parse(time.isha)
                                        )

                                        val currentTime = LocalTime.now()
                                        val nextPrayer = timeMap.filterValues { it.isAfter(currentTime) }.minByOrNull { it.value }

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

                                        namazStorage.saveTimes(
                                            time.fajr, time.dhuhr, time.asr, time.maghrib, time.isha, currentDate
                                        )
                                    } else {
                                        Toast.makeText(requireContext(), "Нет данных о намазе в оффлайне", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                    }
                } ?: run {
                    Toast.makeText(requireContext(), "Не удалось получить местоположение.", Toast.LENGTH_SHORT).show()
                    Log.e("Location", "Location is null")
                }
            }
        } else {
            locationHelper.requestLocationPermission(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE)
        }
        val alarmButton = view.findViewById<Button>(R.id.set_alarm_button)



        val time = LocalTime.now().plusMinutes(1)

//        alarmButton.setOnClickListener {
//            val nextPrayerTime = time
//            AlarmReminder.
//        }


        scheduleNamazNotification("Test Prayer", time)

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
    private fun setAlarmWithAlarmManager(context: Context, hour: Int, minute: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(intent)

        Toast.makeText(context, "Будильник установлен", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNamazNotification(prayerName: String, time: LocalTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !isExactAlarmPermissionGranted()) {
            requestExactAlarmPermission()
            return
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
        } else true
    }

    private fun requestExactAlarmPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        startActivityForResult(intent, REQUEST_CODE_SCHEDULE_EXACT_ALARM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCHEDULE_EXACT_ALARM) {
            if (isExactAlarmPermissionGranted()) {
                Toast.makeText(requireContext(), "Разрешение получено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Разрешение не получено", Toast.LENGTH_SHORT).show()
            }
        }
    }
}