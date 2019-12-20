package com.ashu.postermaker.model_classes

import java.io.Serializable

data class Category(val id: Int, val name: String, val created_at: String, val updated_at: String) : Serializable
