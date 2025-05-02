from django.db import models

class Hadith(models.Model):
    text = models.TextField()
    source = models.CharField(max_length=255)

    def __str__(self):
        return f"{self.text[:30]}..."  # Показывать первые 30 символов
