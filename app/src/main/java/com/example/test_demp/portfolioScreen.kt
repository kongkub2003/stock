package com.example.test_demp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.test_demp.Utils.PortfolioData
import com.example.test_demp.Utils.loadPortfolioFromJson


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PortfolioScreen() {
    val context = LocalContext.current // Obtain Context
    var portfolioItems by remember { mutableStateOf(emptyList<PortfolioData>()) }

    // Load JSON data once
    LaunchedEffect(Unit) {
        portfolioItems = loadPortfolioFromJson(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(text = "แผนการลงทุนของคุณ", color = Color.White)
                } }
                       , backgroundColor = Color(0xFFFF4D4D), // Use custom color similar to the image
                elevation = 0.dp, // No shadow for a flat look
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.img_3),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )

                    }

                })
            Box(
                modifier = Modifier.fillMaxWidth(),

                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(onClick = {
                }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        },


        content = { padding ->
            PortfolioContent(
                portfolioItems = portfolioItems,
                modifier = Modifier.padding(padding)
            )
        }
    )
}

@Composable
fun PortfolioContent(portfolioItems: List<PortfolioData>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)) // Light gray background for the list
    ) {
        items(portfolioItems) { item ->
            PortfolioItem(data = item)
        }
    }
}
@Composable
fun PortfolioItem(data: PortfolioData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data.image_plan),
            contentDescription = data.title,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Title of the investment plan
            Text(
                text = data.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Pending points, only shown if greater than 0
                if (data.pending_point > 0) {
                    Text(
                        text = "Point รอลงทุน",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "%.2f".format(data.pending_point),
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {

                if (data.withdrawable_point > 0) {
                    Text(
                        text = "Point ลงทุนแล้ว",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("%.2f".format(data.withdrawable_point))
                        }
                        append(" ")
                        withStyle(style = SpanStyle(color = if (data.change > 0) Color.Green else Color.Red)) {
                            append(if (data.change > 0) "(+${"%.2f".format(data.change)})" else "(${data.change})")
                        }
                    },
                    fontSize = 12.sp
                )

            }


        }
    }
}
    }

