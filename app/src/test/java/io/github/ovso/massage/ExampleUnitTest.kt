package io.github.ovso.massage

import io.github.ovso.massage.data.ImageRequest
import io.github.ovso.massage.data.ImageService
import io.github.ovso.massage.main.f_acupoints.model.DResult
import io.github.ovso.massage.main.f_acupoints.model.Documents
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun imgReqTest() {
        val query1 = "손 혈자리"
        val query2 = "발 혈자리"
        val query3 = "귀 혈자리"
        val query4 = "다리 혈자리"

        val baseUrl = "https://dapi.kakao.com"
        val searchKey = "KakaoAK 7296bb6b63d625d940275dbc7a78ae41"

        fun onSuccess(items: List<Documents>) {
            println(items.toString())
        }

        fun onFailure(t: Throwable) {
            println(t.message)
        }

        val queries = hashMapOf(
            "query" to query1,
            "sort" to "accuracy",
            "page" to 1,
            "size" to 10
        )
        val queries2 = hashMapOf(
            "query" to query2,
            "sort" to "accuracy",
            "page" to 1,
            "size" to 10
        )

        val images = ImageRequest(baseUrl, ImageService::class.java)
            .api
            .getImages(searchKey, queries)

        val images2 = ImageRequest(baseUrl, ImageService::class.java)
            .api
            .getImages(searchKey, queries2)

        Single.zip(
            images,
            images2,
            BiFunction<DResult, DResult, List<Documents>> { t1: DResult, t2: DResult ->
                mutableListOf<Documents>().apply {
                    addAll(t1.documents)
                    addAll(t2.documents)
                    shuffle()
                }
            }).observeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe(::onSuccess, ::onFailure)

/*
        ImageRequest(baseUrl, ImageService::class.java)
            .api
            .getImages(searchKey, queries)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe(::onSuccess, ::onFailure)
*/
    }

    object SchedulerProvider {
        fun ui() = Schedulers.trampoline()
        fun io() = Schedulers.trampoline()
    }
}
