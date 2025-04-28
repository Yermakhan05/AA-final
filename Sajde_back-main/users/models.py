from django.db import models
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    USER_ROLES = (
        ('user', 'User'),
        ('ustaz', 'Ustaz'),
        ('admin', 'Admin')
    )

    email = models.EmailField(unique=True)
    role = models.CharField(max_length=10, choices=USER_ROLES, default='user')

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username']

    def __str__(self):
        return f"{self.email} - {self.role}"


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    full_name = models.CharField(max_length=150, default='Unknown')
    number = models.CharField(max_length=15, blank=True, null=True)
    bio = models.TextField(blank=True, default='Unknown')
    profile_picture = models.TextField(blank=True, null=True)

    def __str__(self):
        return f"{self.user.email}'s Profile"


