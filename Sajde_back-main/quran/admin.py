from django.contrib import admin

# Register your models here.
from django.contrib import admin
from .models import Surah, Ayat

admin.site.register(Surah)
admin.site.register(Ayat)

