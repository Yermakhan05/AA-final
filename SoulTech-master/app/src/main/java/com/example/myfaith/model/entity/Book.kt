package com.example.myfaith.model.entity

data class Book(
    val id: Int,
    val title: String,
    val publisher: String,
    val pageCount: Int,
    val coverUrl: String,
    val year: Int = 2023,
    val isbn: String = "",
    val description: String = "",
    val fileUrl: String = ""   // URL для скачивания PDF
)

val sampleBooks = listOf(
    Book(
        id = 1,
        title = "РАССКАЖИ МНЕ ОБ ИСЛАМЕ",
        publisher = "ДАРУЛЬ-ФИКР",
        pageCount = 178,
        coverUrl = "https://azan.ru/media/images/books/64160cc846799.jpg",
        description = "Расскажи мне об Исламе\n" +
                " \n" +
                "Вы недавно приняли Ислам? Или недавно начали соблюдать предписания религии? Или, может быть, знакомый немусульманин просит вас дать почитать что-то об Исламе? Тогда наша книга — для вас.",
        fileUrl = "https://azan.ru/upload/Расскажи%20мне%20об%20Исламе.pdf"
    ),
    Book(
        id = 2,
        title = "ПРИЗНАКИ СУДНОГО ДНЯ",
        publisher = "Абу Али аль-Ашари",
        pageCount = 180,
        coverUrl = "https://azan.ru/media/images/books/60f090482da56.jpg",
        description = "Часто приходится слышать, что мы живем в «последние времена» и что те или иные признаки Судного дня уже проявились. Поэтому нам — мусульманам — важно обращаться к нашим источникам: Корану и Сунне — для понимания этих знамений и признаков Судного дня, вера в который является условием действительности имана.",
        fileUrl = "https://azan.ru/upload/Признаки%20Судного%20дня.pdf"
    )
)
