package com.example.nagwatask.main.dagger2

import com.example.nagwatask.main.connections.Api
import com.example.nagwatask.main.dataclass.Video
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. LoginRetrofitService).
    // Function parameters are the dependencies of this type.
    @Provides
    fun createClient() : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply{level = HttpLoggingInterceptor.Level.BODY}).build()
    }
    @Provides
    fun provideApi(client : OkHttpClient): Api {
        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.


        return object :Api{
            override fun getAllVids(): Single<List<Video>> {
                val items = ArrayList<Video>()
                items.add(Video(id = 1,name = "video 1 ",type = " test",url="https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"))
                items.add(Video(id = 2,name = "video 2 ",type = " test",url="https://jsoncompare.org/LearningContainer/SampleFiles/Video/MP4/Sample-MP4-Video-File-Download.mp4"))
                items.add(Video(id = 3,name = "video 3 ",type = " test",url="https://jsoncompare.org/LearningContainer/SampleFiles/Video/MP4/Sample-Video-File-For-Testing.mp4"))
                items.add(Video(id = 4,name = "video 4 ",type = " test",url="https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"))
                return Single.just(items)
            }
        }

//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://nagwa.free.beeceptor.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        val retrofitService = retrofit.create(Api::class.java)
//        return retrofitService
    }
}
const val VideoJSON = "[\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4\",\n" +
        "    \"name\": \"Video 1\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://bestvpn.org/html5demos/assets/dizzy.mp4\",\n" +
        "    \"name\": \"Video 2\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"type\": \"PDF\",\n" +
        "    \"url\": \"(https://kotlinlang.org/docs/kotlin-reference.pdf\",\n" +
        "    \"name\": \"PDF 3\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4\",\n" +
        "    \"name\": \"Video 4\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 5,\n" +
        "    \"type\": \"PDF\",\n" +
        "    \"url\": \"https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf\",\n" +
        "    \"name\": \"PDF 5\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 6,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://storage.googleapis.com/exoplayer-test-media-1/mp4/android-screens-10s.mp4\",\n" +
        "    \"name\": \"Video 6\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 7,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4\",\n" +
        "    \"name\": \"Video 7\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 8,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://storage.googleapis.com/exoplayer-test-media-1/mp4/android-screens-25s.mp4\",\n" +
        "    \"name\": \"Video 8\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 9,\n" +
        "    \"type\": \"PDF\",\n" +
        "    \"url\": \"https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf\",\n" +
        "    \"name\": \"PDF 9\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 10,\n" +
        "    \"type\": \"PDF\",\n" +
        "    \"url\": \"https://en.unesco.org/inclusivepolicylab/sites/default/files/dummy-pdf_2.pdf\",\n" +
        "    \"name\": \"PDF 10\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 11,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4\",\n" +
        "    \"name\": \"Video 11\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 12,\n" +
        "    \"type\": \"VIDEO\",\n" +
        "    \"url\": \"https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4\",\n" +
        "    \"name\": \"Video 12\"\n" +
        "  }\n" +
        "]"