import re

from rest_framework.permissions import AllowAny
from rest_framework.views import APIView
from rest_framework.response import Response
from datetime import datetime, timedelta
import requests

class GetPrayerTimeView(APIView):
    permission_classes = [AllowAny]

    def get(self, request):
        latitude = request.GET.get('latitude')
        longitude = request.GET.get('longitude')
        date = request.GET.get('date')


        if not all([latitude, longitude, date]):
            return Response({'error': 'latitude, longitude, and date are required'}, status=400)

        url = "https://api.aladhan.com/v1/timings"
        params = {
            'latitude': latitude,
            'longitude': longitude,
            'date': date,
            'method': 3
        }

        try:
            response = requests.get(url, params=params)
            data = response.json()
            print(data)
        except Exception as e:
            return Response({'error': 'Failed to connect to prayer API'}, status=500)

        if response.status_code != 200 or "data" not in data or "timings" not in data["data"]:
            return Response({'error': 'Failed to fetch prayer times'}, status=500)

        timings = data["data"]["timings"]

        def adjust_time(time_str, offset_minutes):
            try:
                # Оставляем только часы и минуты (первые 5 символов типа "05:10")
                match = re.match(r'\d{1,2}:\d{2}', time_str)
                if not match:
                    return time_str
                clean_time = match.group()

                original_time = datetime.strptime(clean_time, "%H:%M")
                adjusted = original_time + timedelta(minutes=offset_minutes)
                return adjusted.strftime("%H:%M")
            except Exception:
                return time_str

        offsets = {
            "Fajr": 20,
            "Sunrise": -3,
            "Dhuhr": 4,
            "Asr": 63,
            "Maghrib": 3,
            "Isha": -13,
        }

        adjusted_timings = {}
        for prayer, time in timings.items():
            offset = offsets.get(prayer, 0)
            adjusted_timings[prayer] = adjust_time(time, offset)

        adjusted_timings['date'] = date
        return Response(adjusted_timings)
