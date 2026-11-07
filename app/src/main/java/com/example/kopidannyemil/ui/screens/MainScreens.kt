package com.example.kopidannyemil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopidannyemil.data.AppViewModel
import com.example.kopidannyemil.data.MenuItem
import com.example.kopidannyemil.data.dummyMenu
import com.example.kopidannyemil.data.formatRupiah
import com.example.kopidannyemil.ui.theme.BorderBlue
import com.example.kopidannyemil.ui.theme.Blue
import com.example.kopidannyemil.ui.theme.ErrorRed
import com.example.kopidannyemil.ui.theme.Ink
import com.example.kopidannyemil.ui.theme.Muted
import com.example.kopidannyemil.ui.theme.Navy
import com.example.kopidannyemil.ui.theme.SoftBlue

private enum class Tab(val label: String) { Home("Home"), Order("Order"), Profile("Profile") }

/** Container untuk Home, Order, dan Profile dengan bottom navigation. */
@Composable
fun MainScreen(vm: AppViewModel, onOrderSuccess: () -> Unit, onLogout: () -> Unit) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val current = Tab.entries[tab]

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 4.dp) {
                Tab.entries.forEachIndexed { index, t ->
                    NavigationBarItem(
                        selected = tab == index,
                        onClick = { tab = index },
                        icon = {
                            val icon = when (t) {
                                Tab.Home -> Icons.Filled.Home
                                Tab.Order -> Icons.Filled.ShoppingBag
                                Tab.Profile -> Icons.Filled.Person
                            }
                            Icon(icon, contentDescription = t.label)
                        },
                        label = { Text(t.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Navy,
                            indicatorColor = Navy,
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize().statusBarsPadding()) {
            when (current) {
                Tab.Home -> HomeScreen(vm, goToOrder = { tab = 1 })
                Tab.Order -> OrderScreen(vm, onOrderSuccess)
                Tab.Profile -> ProfileScreen(vm, onLogout)
            }
        }
    }
}

/** 5. Home */
@Composable
private fun HomeScreen(vm: AppViewModel, goToOrder: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 20.dp, end = 20.dp, top = 16.dp, bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Halo ${vm.userName},", style = MaterialTheme.typography.headlineMedium, color = Navy)
                Spacer(Modifier.height(4.dp))
                Text("Mau pesan apa hari ini?", color = Muted)
                Spacer(Modifier.height(20.dp))
                Text("Makanan & Minuman", style = MaterialTheme.typography.titleLarge, color = Navy)
            }
            items(dummyMenu, key = { it.id }) { item ->
                MenuCard(
                    item = item,
                    qty = vm.cart[item.id] ?: 0,
                    onAdd = { vm.addToCart(item.id) },
                    onRemove = { vm.removeFromCart(item.id) }
                )
            }
        }

        if (vm.cartCount > 0) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Navy, RoundedCornerShape(16.dp))
                    .padding(horizontal = 18.dp, vertical = 14.dp)
                    .clickableNoRipple(goToOrder),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${vm.cartCount} item", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text("${formatRupiah(vm.cartTotal)}  •  Lihat pesanan", color = Color.White)
            }
        }
    }
}

@Composable
private fun MenuCard(item: MenuItem, qty: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BorderBlue, RoundedCornerShape(18.dp))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(68.dp).background(SoftBlue, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) { Text(item.emoji, fontSize = 32.sp) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, color = Ink)
                Text("• ${item.description}", fontSize = 12.sp, color = Muted)
                Spacer(Modifier.height(4.dp))
                Text(formatRupiah(item.price), fontWeight = FontWeight.Bold, color = Blue)
            }
            if (qty == 0) {
                IconButton(
                    onClick = onAdd,
                    modifier = Modifier.size(38.dp).background(Navy, CircleShape)
                ) { Icon(Icons.Filled.Add, "Tambah", tint = Color.White) }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(32.dp).border(1.5.dp, Navy, CircleShape)
                    ) { Icon(Icons.Filled.Remove, "Kurangi", tint = Navy, modifier = Modifier.size(18.dp)) }
                    Text("$qty", modifier = Modifier.width(30.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                    IconButton(
                        onClick = onAdd,
                        modifier = Modifier.size(32.dp).background(Navy, CircleShape)
                    ) { Icon(Icons.Filled.Add, "Tambah", tint = Color.White, modifier = Modifier.size(18.dp)) }
                }
            }
        }
    }
}


/** 7. Alamat pengiriman (Order) */
@Composable
private fun OrderScreen(vm: AppViewModel, onOrderSuccess: () -> Unit) {
    var fullName by remember { mutableStateOf(vm.currentUser?.name ?: "") }
    var address by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Alamat", style = MaterialTheme.typography.headlineMedium, color = Navy)
        Spacer(Modifier.height(4.dp))
        Text("Isi alamat pengiriman pesananmu", color = Muted)
        Spacer(Modifier.height(20.dp))

        // Ringkasan pesanan
        Card(
            colors = CardDefaults.cardColors(containerColor = SoftBlue),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Ringkasan pesanan", fontWeight = FontWeight.Bold, color = Navy)
                Spacer(Modifier.height(8.dp))
                if (vm.cart.isEmpty()) {
                    Text("Belum ada item. Pilih menu di tab Home dulu ya.", color = Muted, fontSize = 13.sp)
                } else {
                    dummyMenu.filter { vm.cart.containsKey(it.id) }.forEach { item ->
                        val qty = vm.cart[item.id] ?: 0
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${qty}x ${item.name}", fontSize = 13.sp)
                            Text(formatRupiah(item.price * qty), fontSize = 13.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", fontWeight = FontWeight.Bold)
                        Text(formatRupiah(vm.cartTotal), fontWeight = FontWeight.Bold, color = Navy)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        AppTextField(fullName, { fullName = it; error = null }, "Nama Lengkap")
        Spacer(Modifier.height(14.dp))
        AppTextField(address, { address = it; error = null }, "Alamat", singleLine = false, minLines = 3)
        Spacer(Modifier.height(14.dp))
        AppTextField(detail, { detail = it; error = null }, "Patokan atau detail lengkap", singleLine = false, minLines = 3)

        error?.let {
            Spacer(Modifier.height(10.dp))
            Text(it, color = ErrorRed, fontSize = 13.sp)
        }
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Order dan Kirim", {
            error = when {
                vm.cart.isEmpty() -> "Keranjang masih kosong, pilih menu dulu"
                fullName.isBlank() || address.isBlank() -> "Nama lengkap dan alamat wajib diisi"
                else -> null
            }
            if (error == null) onOrderSuccess()
        })
    }
}

/** 6. Profile */
@Composable
private fun ProfileScreen(vm: AppViewModel, onLogout: () -> Unit) {
    var name by remember { mutableStateOf(vm.currentUser?.name ?: "") }
    var saved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Halo ${vm.userName},", style = MaterialTheme.typography.headlineMedium, color = Navy)
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(64.dp).background(Navy, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    vm.userName.firstOrNull()?.uppercase() ?: "?",
                    color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text("Profile Saya", style = MaterialTheme.typography.titleLarge, color = Navy)
                Text("@${vm.currentUser?.username ?: "-"}", color = Muted)
            }
        }
        Spacer(Modifier.height(28.dp))
        Text("Profile Setting", fontWeight = FontWeight.SemiBold, color = Muted)
        Spacer(Modifier.height(10.dp))
        AppTextField(name, { name = it; saved = false }, "Nama")
        if (saved) {
            Spacer(Modifier.height(8.dp))
            Text("Profil berhasil disimpan ✓", color = Blue, fontSize = 13.sp)
        }
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Save", { saved = vm.updateName(name) })
        Spacer(Modifier.height(12.dp))
        SecondaryButton("Logout", onLogout)
    }
}

/** 8. Konfirmasi pesanan */
@Composable
fun ConfirmationScreen(vm: AppViewModel, onOk: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))
        Text(
            "Halo ${vm.userName},",
            style = MaterialTheme.typography.headlineMedium,
            color = Navy,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier.size(112.dp).background(SoftBlue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(76.dp).background(Navy, CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Check, "OK", tint = Color.White, modifier = Modifier.size(44.dp)) }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Terima kasih sudah memesan, tunggu aja sebentar kurir kami segera mengantarkan pesananmu!",
            textAlign = TextAlign.Center,
            color = Muted,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton("Oke", onOk)
    }
}
