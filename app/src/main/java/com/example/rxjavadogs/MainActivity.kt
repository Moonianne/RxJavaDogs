@file:OptIn(ExperimentalMaterialApi::class)

package com.example.rxjavadogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rxjavadogs.domain.GetDogItemsUseCase
import com.example.rxjavadogs.ui.theme.RxJavaDogsTheme
import com.example.rxjavadogs.view.Dog

class MainActivity : ComponentActivity() {

    private val itemsUseCase = GetDogItemsUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadDogs(::setContent)
    }

    private fun loadDogs(callback: (List<Dog>) -> Unit) {
        itemsUseCase(callback = callback)
    }

    private fun setContent(dogs: List<Dog>) {
        setContent {
            RxJavaDogsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                    content = { List(dogs) },
                )
            }
        }
    }
}

@Composable
fun List(dogs: List<Dog>) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(dogs) { dog: Dog ->
            ListItem(
                Modifier.padding(vertical = 4.dp),
            ) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(dog.imageUrl.value),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp),
                    )
                    Text(
                        text = dog.breed.value,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RxJavaDogsTheme {
        Greeting("Android")
    }
}
