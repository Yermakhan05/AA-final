from django.db import models

# Create your models here.

class PrayerTime(models.Model):
    city = models.CharField(max_length=100)
    fajr = models.TimeField()
    dhuhr = models.TimeField()
    asr = models.TimeField()
    maghrib = models.TimeField()
    isha = models.TimeField()
    date = models.DateField(auto_now_add=True)

    def __str__(self):
        return f"Prayer Times for {self.city} - {self.date}"