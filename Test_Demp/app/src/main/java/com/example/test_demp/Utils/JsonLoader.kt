package com.example.test_demp.Utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.room.Index

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

// Function to load JSON and parse it into a list of IndexData
fun loadIndicesFromJson(context: Context): List<IndexData> {
    val json = context.assets.open("indices.json").bufferedReader().use { it.readText() }
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = moshi.adapter(IndexResponse::class.java)

    val indexResponse = adapter.fromJson(json)
    return indexResponse?.indices ?: emptyList()

}
data class IndexResponse(val indices: List<IndexData>)
data class IndexData(
    val symbol: String,
    val short_name: String,
    val price: Double,
    val change: Double,
    val percent_change: Double,
)
fun loadPortfolioFromJson(context: Context): List<PortfolioData> {
    val json = context.assets.open("portfolio.json").bufferedReader().use { it.readText() }
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, PortfolioData::class.java)
    val adapter = moshi.adapter<List<PortfolioData>>(listType)

    return adapter.fromJson(json) ?: emptyList()
}
data class PortfolioResponse(val portfolio: List<PortfolioData>)
data class PortfolioData(
    val plan_id: String,
    val title: String,
    val enable: Int,
    val order: Int,
    val description: String,
    val image_plan_bg: String,
    val image_plan: String,
    val withdrawable_point: Double,
    val pending_point: Double,
    val cost: Double,
    val change: Double,
    val all: Double
)