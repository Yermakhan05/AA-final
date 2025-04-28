from rest_framework import serializers
from .models import Surah, Ayat

class AyatSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ayat
        fields = ['id', 'number', 'arabicText', 'transliteration', 'translation']

class SurahSerializer(serializers.ModelSerializer):
    ayats = AyatSerializer(many=True, read_only=True)

    class Meta:
        model = Surah
        fields = ['id', 'name', 'number_of_ayats', 'ayats']
