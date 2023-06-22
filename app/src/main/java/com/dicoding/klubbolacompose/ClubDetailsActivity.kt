package com.dicoding.klubbolacompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dicoding.klubbolacompose.ui.theme.KlubBolaComposeTheme

class ClubDetailsActivity : ComponentActivity() {

    private val customFont = FontFamily(
        Font(R.font.lora)
    )

    private lateinit var club: Club
    private var isFavorite by mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        club = intent.getParcelableExtra(CLUB_EXTRA) ?: return finish()

        setContent {
            KlubBolaComposeTheme {
                ProvideTextStyle(value = TextStyle(fontFamily = customFont)) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .verticalScroll(state = rememberScrollState()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            TopAppBar(
                                title = { Text(text = "Detail Club") },
                                navigationIcon = {
                                    IconButton(onClick = { onBackPressed() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = stringResource(R.string.back)
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(
                                        onClick = { isFavorite = !isFavorite },
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            ClubDetailsContent(club = club)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val CLUB_EXTRA = "club_extra"

        fun newIntent(context: Context, club: Club): Intent {
            return Intent(context, ClubDetailsActivity::class.java).apply {
                putExtra(CLUB_EXTRA, club)
            }
        }
    }
}


@Composable
fun ClubDetailsContent(club: Club) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(club.photoClub),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = club.fullNameClub,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = club.descClub,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
    }
}