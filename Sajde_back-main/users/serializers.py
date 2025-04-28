from rest_framework import serializers
from .models import User, Profile


class RegisterSerializer(serializers.ModelSerializer):
    full_name = serializers.CharField(write_only=True)
    number = serializers.CharField(write_only=True, required=False)

    class Meta:
        model = User
        fields = ['email', 'username', 'password', 'full_name', 'number']
        extra_kwargs = {
            'password': {'write_only': True},
        }

    def create(self, validated_data):
        full_name = validated_data.pop('full_name')
        number = validated_data.pop('number')
        validated_data['role'] = 'user'

        user = User.objects.create_user(**validated_data)
        Profile.objects.create(user=user, full_name=full_name, number=number)
        return user

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = ['full_name', 'number', 'bio', 'profile_picture']


    def update(self, instance, validated_data):
        instance.full_name = validated_data.get('full_name', instance.full_name)
        instance.number = validated_data.get('number', instance.number)
        instance.bio = validated_data.get('bio', instance.bio)
        instance.profile_picture = validated_data.get('profile_picture', instance.profile_picture)

        instance.save()
        return instance
