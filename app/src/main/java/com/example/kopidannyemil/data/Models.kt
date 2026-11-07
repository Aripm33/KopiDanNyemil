package com.example.kopidannyemil.data

data class User(val name: String, val username: String, val password: String)

data class MenuItem(
    val id: Int,
    val emoji: String,
    val name: String,
    val description: String,
    val price: Int,
    val category: String
)

fun formatRupiah(value: Int): String =
    "Rp " + String.format("%,d", value).replace(',', '.')

/** Data statis (dummy), tidak memakai database. */
val dummyMenu = listOf(
    MenuItem(1, "☕", "Kopi Susu Gula Aren", "Espresso, susu, gula aren", 22000, "Minuman"),
    MenuItem(2, "🍩", "Donat Cokelat", "Donat empuk, glaze cokelat", 12000, "Makanan"),
    MenuItem(3, "🍞", "Sourdough Loaf", "Roti fermentasi alami", 35000, "Makanan"),
    MenuItem(4, "☕", "Cappuccino", "Espresso, foam susu lembut", 25000, "Minuman"),
    MenuItem(5, "🍵", "Matcha Latte", "Matcha Jepang, susu segar", 27000, "Minuman"),
    MenuItem(6, "🥐", "Croissant Butter", "Renyah, butter Prancis", 20000, "Makanan"),
    MenuItem(7, "🍋", "Es Teh Lemon", "Teh segar, lemon, es batu", 15000, "Minuman")
)
