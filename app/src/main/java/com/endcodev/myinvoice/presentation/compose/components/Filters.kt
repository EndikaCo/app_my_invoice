package com.endcodev.myinvoice.presentation.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.domain.models.common.FilterModel


@Composable
fun FiltersView(onFiltersChanged: (List<FilterModel>) -> Unit, filters: List<FilterModel>) {


    LazyRow(Modifier.fillMaxWidth()) {
        items(filters) {
            FilterItem(it, onFilterClick = {

                // return filters list without the clicked filter
                val newFilters = filters.filter { filter -> filter != it }
                onFiltersChanged(newFilters)

            })
        }
    }
}

@Composable
fun FilterItem(filter: FilterModel, onFilterClick: (FilterModel) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onFilterClick(filter) }
            .padding(start = 5.dp, end = 5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.purple_200),
            contentColor = Color.Black
        )
    ) {
        Box(
            Modifier
                .height(25.dp)
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = filter.text,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}