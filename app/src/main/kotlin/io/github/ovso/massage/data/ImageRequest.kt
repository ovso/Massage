package io.github.ovso.massage.data

class ImageRequest(
    baseUrl: String,
    clz: Class<ImageService>
) : BaseRequest<ImageService>(baseUrl, clz)