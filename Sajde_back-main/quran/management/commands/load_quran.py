import json
from django.core.management.base import BaseCommand
from quran.models import Surah, Ayat  

class Command(BaseCommand):
    help = 'Loads Surahs and Ayats from a JSON file'

    def add_arguments(self, parser):
        parser.add_argument('file', type=str, help='Path to the JSON file')

    def handle(self, *args, **kwargs):
        path = kwargs['file']
        with open(path, 'r', encoding='utf-8') as f:
            data = json.load(f)

        for surah_data in data['surahs']:
            surah, created = Surah.objects.get_or_create(
                number=surah_data['number'],
                defaults={
                    'name': surah_data['name'],
                    'revelation_type': 'Meccan'  
                }
            )

            for verse in surah_data['verses']:
                Ayat.objects.create(
                    surah=surah,
                    number=verse['number'],
                    text=verse['arabicText'],
                    translation=verse['translation']
                )

        self.stdout.write(self.style.SUCCESS("Qur'an data loaded successfully"))
