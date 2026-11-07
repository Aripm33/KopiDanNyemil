package com.example.kopidannyemil.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    // Akun demo supaya bisa langsung login: demo / 123456
    private val users = mutableStateListOf(User("Pengguna Demo", "demo", "123456"))

    var currentUser by mutableStateOf<User?>(null)
        private set

    /** id menu -> jumlah */
    val cart = mutableStateMapOf<Int, Int>()

    val userName: String get() = currentUser?.name ?: "Teman"

    val cartCount: Int get() = cart.values.sum()

    val cartTotal: Int
        get() = cart.entries.sumOf { (id, qty) ->
            (dummyMenu.find { it.id == id }?.price ?: 0) * qty
        }

    /** Mengembalikan pesan error, atau null jika berhasil. */
    fun register(name: String, username: String, password: String): String? {
        if (name.isBlank() || username.isBlank() || password.isBlank())
            return "Semua kolom wajib diisi"
        if (password.length < 6) return "Password minimal 6 karakter"
        if (users.any { it.username.equals(username.trim(), true) })
            return "Username sudah dipakai"
        val user = User(name.trim(), username.trim(), password)
        users.add(user)
        currentUser = user
        return null
    }

    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val user = users.find { it.username.equals(username.trim(), true) && it.password == password }
            ?: return "Username atau password salah"
        currentUser = user
        return null
    }

    fun updateName(newName: String): Boolean {
        val user = currentUser ?: return false
        if (newName.isBlank()) return false
        val updated = user.copy(name = newName.trim())
        val index = users.indexOf(user)
        if (index >= 0) users[index] = updated
        currentUser = updated
        return true
    }

    fun addToCart(id: Int) {
        cart[id] = (cart[id] ?: 0) + 1
    }

    fun removeFromCart(id: Int) {
        val qty = cart[id] ?: return
        if (qty <= 1) cart.remove(id) else cart[id] = qty - 1
    }

    fun clearCart() = cart.clear()

    fun logout() {
        currentUser = null
        cart.clear()
    }
}
