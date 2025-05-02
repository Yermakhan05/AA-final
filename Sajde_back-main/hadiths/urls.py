from django.urls import path
from .views import HadithListView

urlpatterns = [
    path('api/hadiths/', HadithListView.as_view(), name='hadith-list'),
]
