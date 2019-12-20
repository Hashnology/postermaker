package com.ashu.postermaker.interfaces

import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.model_classes.SubCategory

interface CategoryListener {

    fun onItemClick(position: Int, model: SubCategory)

    fun onSeeAllClick(position: Int, model: Category)

}