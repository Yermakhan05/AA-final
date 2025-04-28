from django.db import models

# Create your models here.
class Surah(models.Model):
    number = models.IntegerField(unique=True)
    name = models.CharField(max_length=255)
    revelation_type = models.CharField(
        max_length=50,
        choices=[("Meccan", "Meccan"), ("Medinan", "Medinan")],
        default="Meccan"  
    )

    def __str__(self):
        return f"{self.number}. {self.name}"
    

class Ayat(models.Model):
    surah = models.ForeignKey(Surah, on_delete=models.CASCADE, related_name="ayats")
    number = models.IntegerField()
    text = models.TextField()
    translation = models.TextField(blank=True, null=True)
    transliteration = models.TextField(blank=True, null=True)

    def __str__(self):
        return f"Surah {self.surah.name}, Ayat {self.number}"


