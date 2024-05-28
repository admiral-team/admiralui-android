package com.admiral.uikit.compose.control

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

@OptIn(ExperimentalFoundationApi::class)
@Suppress("LongParameterList", "LongParameter", "MagicNumber")
@Composable
fun LinerPageControl(
    modifier: Modifier = Modifier,
    pageCount: Int = 2,
    pagerState: PagerState = rememberPagerState(pageCount = { pageCount }),
    pageSelectedColor: Color = AdmiralTheme.colors.elementSecondary,
    pageUnselectedColor: Color = AdmiralTheme.colors.elementAdditional,
) {
    val indicatorScrollState = rememberLazyListState()
    val currentPage = pagerState.currentPage

    LaunchedEffect(key1 = currentPage) {
        val size = indicatorScrollState.layoutInfo.visibleItemsInfo.size
        val lastVisibleIndex = indicatorScrollState.layoutInfo.visibleItemsInfo.last().index
        val firstVisibleItemIndex = indicatorScrollState.firstVisibleItemIndex

        if (currentPage > lastVisibleIndex - 1) {
            indicatorScrollState.animateScrollToItem(currentPage - size + 2)
        } else if (currentPage <= firstVisibleItemIndex + 1) {
            indicatorScrollState.animateScrollToItem((currentPage - 1).coerceAtLeast(0))
        }
    }

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
    ) {
        LazyRow(
            state = indicatorScrollState,
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                item(key = "item$iteration") {
                    if (iteration != 0) Spacer(modifier = Modifier.width(DIMEN_X2))
                    Box(
                        modifier = Modifier
                            .width(DIMEN_X4)
                            .height(DIMEN_X1)
                            .background(
                                color = if (currentPage == iteration) pageSelectedColor else pageUnselectedColor,
                                shape = RoundedCornerShape(DIMEN_X2)
                            )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PagerPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            LinerPageControl(
                modifier = Modifier.fillMaxWidth(),
                pageCount = 8
            )
        }
    }
}