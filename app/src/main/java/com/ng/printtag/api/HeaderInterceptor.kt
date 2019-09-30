package ng.pdp.api

import android.content.Context
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.apputils.BaseSharedPreference
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor(var context: Context) : Interceptor {


    /**
     *  A concrete interceptor chain that carries the entire interceptor chain.
     *
     *  @param chain which Observes, modifies, and potentially short-circuits requests going out and the corresponding responses coming back in.
     *  @return Response which is HTTP response.the response body is a one-shot value that may be consumed only once and then closed.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header("Accept", "application/json ; charset=UTF-8")
            .header("Authorization", RequestMethods.getAuthToken(context))
            .addHeader(
                "languageCode", BaseSharedPreference.getInstance(context).getLanguage(
                    context.getString(R.string.pref_language)
                )
            )
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }

}