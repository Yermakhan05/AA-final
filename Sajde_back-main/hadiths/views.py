from rest_framework import generics
from rest_framework.permissions import AllowAny

from .models import Hadith
from .serializers import HadithSerializer

class HadithListView(generics.ListAPIView):
    queryset = Hadith.objects.all()
    serializer_class = HadithSerializer
    permission_classes = [AllowAny]  # 👈👈👈 ОТКРЫТЫЙ ДОСТУП
