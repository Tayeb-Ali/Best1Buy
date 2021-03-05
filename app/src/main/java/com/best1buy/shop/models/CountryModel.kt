package com.best1buy.shop.models

import java.io.Serializable

data class CountryModel(
    val code : String,
    val name : String,
    val states : ArrayList<State>,
    val _links: Links
    ): Serializable

data class State(
    val code : String,
    val name : String
) : Serializable