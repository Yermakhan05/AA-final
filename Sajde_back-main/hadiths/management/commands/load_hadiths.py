import json
import os
from django.core.management.base import BaseCommand
from hadiths.models import Hadith

class Command(BaseCommand):
    help = 'Load hadiths from a JSON file'

    def handle(self, *args, **kwargs):
        # Получаем путь к файлу
        base_dir = os.path.dirname(os.path.dirname(os.path.dirname(__file__)))  # возвращаемся назад из management/commands/
        file_path = os.path.join(base_dir, 'data', 'hadiths.json')

        self.stdout.write(self.style.WARNING(f'Loading hadiths from {file_path}'))

        with open(file_path, encoding='utf-8') as f:
            hadiths_data = json.load(f)

        for hadith in hadiths_data:
            Hadith.objects.create(
                text=hadith['text'],
                source=hadith['source']
            )

        self.stdout.write(self.style.SUCCESS('Hadiths loaded successfully!'))
