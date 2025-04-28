from django.urls import path
from .views import SurahDetailView, SurahNamesView

urlpatterns = [
    path('surah/<int:number>/', SurahDetailView.as_view(), name='surah-detail'),
    path('surah-names/', SurahNamesView.as_view(), name='surah-names'),
]
