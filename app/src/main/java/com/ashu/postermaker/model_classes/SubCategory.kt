package com.ashu.postermaker.model_classes
import java.io.Serializable

data class SubCategory(val id: Int, val name: String, val image: String, val is_active: Int, val is_deleted: Int) : Serializable
