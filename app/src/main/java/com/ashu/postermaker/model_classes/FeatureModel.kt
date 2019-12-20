package com.ashu.postermaker.model_classes

import java.io.Serializable

data class FeatureModel(val id: Int, val name: String, val category_id: Int,val user_id: String,val is_home: Int, val image: String, val is_active: Int, val is_deleted: Int) : Serializable
