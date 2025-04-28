from django.db import models

# Create your models here.
class Book(models.Model):
    title = models.CharField(max_length=255)
    author = models.CharField(max_length=255)
    cover_image = models.ImageField(upload_to="books/", blank=True, null=True)
    pdf = models.FileField(upload_to="books_pdfs/", blank=True, null=True)

    def __str__(self):
        return self.title


class Quote(models.Model):
    text = models.TextField()
    author = models.CharField(max_length=255, blank=True, null=True)
    date_added = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"Quote by {self.author}"
