package com.worldline.kmm_mtls_sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.worldline.kmm_mtls_sample.RootViewModel
import com.worldline.kmm_mtls_sample.SampleActions
import com.worldline.kmm_mtls_sample.SampleViewModel
import com.worldline.kmm_mtls_sample.SampleViewState
import com.worldline.kmm_mtls_sample.ViewState
import de.charlex.compose.HtmlText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SampleView()
                }
            }
        }
    }
}

@Composable
fun SampleView() {
    val viewModel = remember { SampleViewModel(initialState = SampleViewState.Idle) }
    val state: ViewState = viewModel.stateWithLifecycle().value

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Welcome to the MTLS KMM test app")
            when (state) {
                is SampleViewState.Idle -> Text("Tap on the buttons to start testing")
                is SampleViewState.Loading -> CircularProgressIndicator()
                is SampleViewState.Error ->
                    Box(
                        modifier = Modifier.background(Color.Red)
                    ) {
                        HtmlText(text = state.error)
                    }

                is SampleViewState.Success ->
                    Box(
                        modifier = Modifier.background(Color.Green)
                    ) {
                        HtmlText(text = state.data)
                    }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Button(onClick = { viewModel.onAction(SampleActions.OnLoadWithMtls) }) {
                    Text("Load with MTLS")
                }
                Button(onClick = { viewModel.onAction(SampleActions.OnLoadWithoutMtls) }) {
                    Text("Load without MTLS")
                }
            }
        }
    }
}


@Composable
fun <S : ViewState, E> RootViewModel<S, E>.stateWithLifecycle(): State<S> {
    val lifecycleOwner = LocalLifecycleOwner.current

    val flow = remember(state, lifecycleOwner) {
        state.flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    return flow.collectAsState(state.value)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        SampleView()
    }
}
