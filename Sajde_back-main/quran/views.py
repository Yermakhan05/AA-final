from django.shortcuts import render
from rest_framework.views import APIView, View
from rest_framework.response import Response
from rest_framework import status
from .models import Surah
from .serializers import SurahSerializer
from django.http import JsonResponse

class SurahDetailView(APIView):
    def get(self, request, number):
        try:
            surah = Surah.objects.get(number=number)
            serializer = SurahSerializer(surah)

            return Response(serializer.data, status=status.HTTP_200_OK)
        except Surah.DoesNotExist:
            return Response({"detail": "Surah not found."}, status=status.HTTP_404_NOT_FOUND)
    


class SurahNamesView(View):
    def get(self, request, *args, **kwargs):
        surahs = Surah.objects.all().values('number', 'name')
        return JsonResponse(list(surahs), safe=False)

