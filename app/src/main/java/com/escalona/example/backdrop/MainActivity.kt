package com.escalona.example.backdrop

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.escalona.example.backdrop.ui.theme.BackdropExampleTheme

class MainActivity : FragmentActivity() {

    private val cards = listOf(
        ItemCardState(
            icon = Icons.Default.CheckCircle,
            title = "Tasks",
            subtitle = "Check pending",
        ),
        ItemCardState(
            icon = Icons.Default.AccountCircle,
            title = "Profile",
            subtitle = "Configure your address",
        ),
        ItemCardState(
            icon = Icons.Default.Email,
            title = "Email",
            subtitle = "Send an email",
        ),
    )

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackdropExampleTheme {
                BackdropScaffold(
                    appBar = { },
                    scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
                    frontLayerContent = {
                            val localView = LocalView.current
                            val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }
                            val container = remember { mutableStateOf<FragmentContainerView?>(null) }
                            val viewBlock: (Context) -> View = remember(localView) {
                                { context ->
                                    FragmentContainerView(context)
                                        .apply { id = containerId }
                                        .also {
                                            supportFragmentManager.commit { add(it.id, RecyclerViewFragment()) }
                                            container.value = it
                                        }
                                }
                            }
                            AndroidView(
                                modifier = Modifier.fillMaxSize(),
                                factory = viewBlock,
                                update = {},
                            )
                    },
                    frontLayerScrimColor = Color.Transparent,
                    backLayerContent = {
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            items(cards) { card ->
                                ItemCard(itemCardState = card)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                )
            }
        }
    }
}