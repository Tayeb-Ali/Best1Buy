package com.best1buy.shop.utils.oauthInterceptor

interface TimestampService {
    val timestampInSeconds: String
    val nonce: String
}