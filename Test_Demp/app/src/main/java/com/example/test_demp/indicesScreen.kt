package com.example.test_demp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_demp.Utils.IndexData
import com.example.test_demp.Utils.loadIndicesFromJson


@Composable
fun IndicesScreen(context: Context) {
    val indexData = remember { loadIndicesFromJson(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF303030))

    ) {
        // Header with Title and Close Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E88E5))
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Indices",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
            }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        // Last Update Text
        CurrentTimeDisplay()


        // Grid for Indices
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(indexData) { index ->
                IndexItem(index)
            }
        }
    }
}
// Individual Index Item
@Composable
fun IndexItem(index: IndexData) {
    val backgroundColor = when {
        index.change > 0.5 && index.percent_change > 0.5 -> Color(0xFF4CAF50) // Dark green for significant positive change
        index.change > 0 && index.percent_change > 0 -> Color(0xFF7EBF80) // Light green for small positive change
     else -> Color(0xFFF44336)
    }

    Column(
        modifier = Modifier
          //  .padding(4.dp)
            .background(backgroundColor)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${if (index.change > 0) "+" else ""}${"%.2f".format(index.change)}",
                color = Color.White,
                fontSize = 12.sp
            )
            Text(
                text = "${if (index.percent_change > 0) "+" else ""}${"%.2f".format(index.percent_change)}%",
                color = Color.White,
                fontSize = 12.sp
            )
        }
        Text(
            text = index.short_name,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = index.price.toString(),
            color = Color.White,
            fontSize = 30.sp
        )
    }

}
