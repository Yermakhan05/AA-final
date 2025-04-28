from django.urls import path
from .views import GetPrayerTimeView

urlpatterns = [
    path('get-prayer-times/', GetPrayerTimeView.as_view()),
]