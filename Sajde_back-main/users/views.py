from rest_framework import generics, status
from rest_framework.response import Response
from django.contrib.auth import get_user_model
from rest_framework.permissions import IsAuthenticated, AllowAny
from .models import User, Profile
from .serializers import RegisterSerializer, ProfileSerializer
from .permissions import IsUstaz, IsAdminUser
from rest_framework.parsers import MultiPartParser, FormParser

User = get_user_model()


class RegisterView(generics.CreateAPIView):
    queryset = User.objects.all()
    serializer_class = RegisterSerializer
    permission_classes = [AllowAny]
    serializer_class = RegisterSerializer
    permission_classes = [AllowAny]

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        if not serializer.is_valid():
            return Response({"error": serializer.errors}, status=400)
        self.perform_create(serializer)
        return Response(serializer.data, status=201)


class ProfileView(generics.RetrieveAPIView):
    serializer_class = ProfileSerializer
    permission_classes = [AllowAny]

    def get_queryset(self):
        return Profile.objects.filter(user=self.request.user)

    def get_object(self):
        try:
            return self.get_queryset().get()
        except Profile.DoesNotExist:
            raise Response({"error": "Profile not found"}, status=status.HTTP_404_NOT_FOUND)


class UstazOnlyView(generics.ListAPIView):
    serializer_class = RegisterSerializer
    permission_classes = [IsUstaz]

    def get_queryset(self):
        try:
            return User.objects.filter(role="ustaz")
        except Exception as e:
            raise Response({"error": "Unable to retrieve ustaz users"}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class AdminOnlyView(generics.ListAPIView):
    serializer_class = RegisterSerializer
    permission_classes = [IsAdminUser]

    def get_queryset(self):
        try:
            return User.objects.all()
        except Exception as e:
            raise Response({"error": "Unable to retrieve admin users"}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)


class ProfileDetailView(generics.RetrieveUpdateAPIView):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = [AllowAny]
    parser_classes = [MultiPartParser, FormParser]

    def get_object(self):
        return Profile.objects.get(user=self.request.user)