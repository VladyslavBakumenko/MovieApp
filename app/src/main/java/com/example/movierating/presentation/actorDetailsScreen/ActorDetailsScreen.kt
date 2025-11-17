package com.example.movierating.presentation.actorDetailsScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movierating.R
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImageInfo
import com.example.movierating.domain.utils.TMDB_IMAGES_BASE_URL
import com.example.movierating.domain.utils.getPaddingValuesForLazyRowItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailsScreen(
    actor: Actor,
    backPressedClickListener: () -> Unit
) {

    val viewModel = hiltViewModel<ActorDetailsScreenViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getActorDetails(actor.id)
        viewModel.getActorImages(actor.id)
    })

    Scaffold(modifier = Modifier.background(Color.Black), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = actor.name)
            },
            navigationIcon = {
                IconButton(onClick = {
                    backPressedClickListener()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            }
        )
    }) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = 8.dp,
                end = 8.dp
            )
        ) {
            LazyColumn {
                item { ActorImageHorizontalPager(state.profileImageInfoList) }
                item {
                    Text(
                        text = stringResource(id = R.string.biography_text),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                    )
                }
                item {
                    Text(
                        text = state.profileDetailsResponse?.biography ?: "No biography available",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ActorImageHorizontalPager(profileImageInfoList: List<ProfileImageInfo>) {
    if (profileImageInfoList.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = {
        profileImageInfoList.size
    })

    HorizontalPager(state = pagerState) { page ->
        MovieActorItem(
            profileImageInfo = profileImageInfoList[page],
            paddingValues = getPaddingValuesForLazyRowItems(page, profileImageInfoList.size),
            onClick = {}
        )
    }
}

@Composable
private fun MovieActorItem(
    profileImageInfo: ProfileImageInfo,
    paddingValues: PaddingValues,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = TMDB_IMAGES_BASE_URL + profileImageInfo.filePath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onClick.invoke() }
        )
    }
}