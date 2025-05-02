from rest_framework import serializers
from .models import Hadith

class HadithSerializer(serializers.ModelSerializer):
    class Meta:
        model = Hadith
        fields = ['id', 'text', 'source']
