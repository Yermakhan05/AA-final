import com.example.myfaith.entity.response.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationApi {
    @FormUrlEncoded
    @POST("users/register/")
    fun registerUser(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("full_name") fullName: String,
        @Field("number") number: String
    ): Call<RegistrationResponse>
}
