package com.example.myfaith

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myfaith.model.entity.Book
import com.example.myfaith.model.entity.sampleBooks
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(onBookClick: (Int) -> Unit) {
val books = sampleBooks
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Книги: ${books.size}",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C2733)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF1C2733))
        ) {
            items(books) { book ->
                BookListItem(book = book, onClick = { onBookClick(book.id) })
                Divider(color = Color(0xFF2D3B48), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun BookListItem(book: Book, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = book.coverUrl,
            contentDescription = book.title,
            modifier = Modifier
                .width(100.dp)
                .height(140.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = book.title,
                fontSize = 18.sp,
                color = Color.White,
                maxLines = 2
            )

            Text(
                text = book.publisher,
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "${book.pageCount} бет",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
