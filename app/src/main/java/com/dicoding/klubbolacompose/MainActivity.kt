package com.dicoding.klubbolacompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.dicoding.klubbolacompose.ui.theme.KlubBolaComposeTheme

class MainActivity : ComponentActivity() {

    private val list by lazy { getListClub() }
    private val customFont = FontFamily(
        Font(R.font.lora)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KlubBolaComposeTheme {
                ProvideTextStyle(value = TextStyle(fontFamily = customFont)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            ClubList(list = list) { club ->
                                onClubItemClick(club)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onClubItemClick(club: Club) {
        startActivity(ClubDetailsActivity.newIntent(this, club))
    }

    private fun getListClub(): ArrayList<Club> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataFullName = resources.getStringArray(R.array.data_full_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val listClub = ArrayList<Club>()

        for (i in dataName.indices) {
            val club = Club(dataName[i], dataFullName[i], dataPhoto[i], dataDescription[i])
            listClub.add(club)
        }
        return listClub
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubList(list: ArrayList<Club>, onItemClick: (Club) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(filterClubList(list, searchText)) { club ->
                ClubListItem(club = club, onItemClick = onItemClick)
            }
        }
    }
}

fun filterClubList(list: List<Club>, searchText: String): List<Club> {
    return if (searchText.isEmpty()) {
        list
    } else {
        list.filter { club ->
            club.nameClub.contains(searchText, ignoreCase = true) ||
                    club.fullNameClub.contains(searchText, ignoreCase = true)
        }
    }
}

@Composable
fun ClubListItem(club: Club, onItemClick: (Club) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(club) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(club.photoClub),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(CenterVertically)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = club.nameClub,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = club.fullNameClub,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewClubListItem() {
    KlubBolaComposeTheme {
        ClubListItem(
            club = Club(
                nameClub = "Persib Bandung",
                fullNameClub = "Persib Bandung",
                photoClub = "https://upload.wikimedia.org/wikipedia/id/5/5b/Lambang_Persib.png",
                descClub = "Persib Bandung adalah sebuah klub sepak bola Indonesia yang berdiri pada 14 Maret 1933, bermarkas di Bandung, Jawa Barat. Persib saat ini bermain di Liga 1 Indonesia. Persib mempunyai julukan Maung Bandung dan Pangeran Biru."
            ),
            onItemClick = {}
        )
    }
}