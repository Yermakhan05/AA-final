package com.example.myfaith.entity

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
        title = "Мұхаммед пайғамбар  өмір жолы",
        publisher = "Kitap Al",
        pageCount = 1165,
        coverUrl = "https://api.bookmate.ru/assets/books-covers/07/84/XrfxE171-ipad.jpeg?image_hash=626c8855bb49cb97943f1fdc63600e4d",
        description = "Көзі қарақты оқырмандардың назарына ұсынылып отырған кітап, он сегіз мың ғаламның сардары болған ақырғы елші Мұхаммед пайғамбардың (саллаллаһу аләйһи уә сәлләм) тағылымға толы өмір жолынан сыр шерте отырып, Ислам тарихында орын алған ірілі-ұсақты оқиғалардың шынайы шындығын нақтылы деректермен бедерлеп, айшықтап берумен қатар, тарих көшіне ілесіп ғасырлар бойы өз биігіне шырқай түскен Ислам дінінің бүкіл адамзатқа ортақ құндылықтары туралы ой-толғаныстарын да көркем тілмен баян етеді. Сөз жоқ, кеңінен көрініс тапқан тың ойлардың, соны пайымдаулар мен терең байыпты ізденістерге толы зерттеулердің мәні айрықша. Адамзаттың асыл тұлғасының ғажайып әлемі әспеттелген бұл еңбек оқырманның жүрегін тербеп, санасына сәуле түсірері күмәнсіз. Смартфонға немесе планшетке Play Market-тен немесе Apple Store— дан кез келген QRcode reader программасын көшіріп алғаннан кейін, тақырыптың тұсындағы QR-код-ты танытып, мәтінді тыңдауға болады. Еңбек барша оқырман қауымға арналады.",
        fileUrl = "https://books.yandex.kz/books/XrfxE171"
    ),
    Book(
        id = 2,
        title = "Қажылық пен ұмра",
        publisher = "Kitap AL",
        pageCount = 180,
        coverUrl = "https://api.bookmate.ru/assets/books-covers/91/59/RpfJGf27-ipad.png?image_hash=bfbef1ffcecf48b79e5f639d3d42dbbf",
        description = "Қажылық пен ұмра рәсімдерін үйрететін кітап.",
        fileUrl = "https://books.yandex.ru/books/RpfJGf27"
    )
)
