package com.jp.test.composedemo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jp.test.composedemo.R

@Composable
fun ImageFromURLWithPlaceHolder(imageUrl : String) {
    AsyncImage(
        contentScale = ContentScale.Fit,
        modifier = Modifier.width(100.dp).height(100.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_img_placeholder),
        contentDescription = stringResource(R.string.app_name)
//        colorFilter = ColorFilter.tint(Color.Blue)
    )
}

@Composable
fun ImageFromURLWithPlaceHolderDetails(imageUrl : String) {
    AsyncImage(
        contentScale = ContentScale.Fit,
        modifier = Modifier.width(150.dp).height(150.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_img_placeholder),
        contentDescription = stringResource(R.string.app_name)
//        colorFilter = ColorFilter.tint(Color.Blue)
    )
}