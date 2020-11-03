@file:Suppress("SpellCheckingInspection")

package io.github.ovso.massage.main.f_acupoints

import android.content.res.Resources
import io.github.ovso.massage.R
import io.github.ovso.massage.data.ImageRequest
import io.github.ovso.massage.data.ImageService
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel
import io.github.ovso.massage.main.f_acupoints.model.Documents
import io.github.ovso.massage.main.f_acupoints.network.ImagesNetwork
import io.github.ovso.massage.utils.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AcupointsPresenterImpl(
    private val view: AcupointsPresenter.View,
    private val adapterDataModel: BaseAdapterDataModel<Documents>,
    private val compositeDisposable: CompositeDisposable,
    private val imagesNetwork: ImagesNetwork
) : AcupointsPresenter {
    override fun onActivityCreate(res: Resources) {
        view.setRecyclerView()
        reqImages(res)
    }

    private fun reqImages(res: Resources) {
        val query1 = res.getString(R.string.query1)
        val query2 = res.getString(R.string.query2)
        val query3 = res.getString(R.string.query3)
        val query4 = res.getString(R.string.query4)
        val query5 = res.getString(R.string.query5)
        val query6 = res.getString(R.string.query6)
        val query7 = res.getString(R.string.query7)

        val baseUrl = "https://dapi.kakao.com"
        val searchKey = "KakaoAK 7296bb6b63d625d940275dbc7a78ae41"
        val queries = listOf(query1, query2, query3, query4, query5, query6, query7)

        val singles = queries.map {
            val queryMap = hashMapOf<String, Any>(
                "query" to it,
                "sort" to "accuracy",
                "page" to 1,
                "size" to 10
            )
            ImageRequest(baseUrl, ImageService::class.java).api.getImages(searchKey, queryMap)
                .map { result ->
                    result.documents
                }
        }

        fun onSuccess(items: List<Documents?>) {
            adapterDataModel.addAll(items)
            view.refresh()
        }

        fun onFailure(t: Throwable) {
            println(t.message)
            view.showMessage(R.string.error_server)
        }

        compositeDisposable += Single.zip(singles) {
            it.flatMap { any ->
                any as List<*>
            }.toList()
        }.map {
            it.map { docs ->
                (docs as? Documents)
            }.toList()
        }.subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .doOnSubscribe { view.showLoading() }
            .doOnError { view.hideLoading() }
            .doOnSuccess { view.hideLoading() }
            .subscribe(::onSuccess, ::onFailure)
    }

    override fun onItemClick(item: Documents) {
        view.navigateToImage(item)
    }

    override fun onDocUrlItemClick(item: Documents) {
        view.showWebViewDialog(item.doc_url)
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
    }
}

private operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
    add(subscribe)
}
