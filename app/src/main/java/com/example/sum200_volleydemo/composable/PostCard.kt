package com.example.sum200_volleydemo.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sum200_volleydemo.data.Post
import com.example.sum200_volleydemo.ui.theme.SUM200VolleyDemoTheme

@Composable
fun PostCard(post: Post) {
    Card(Modifier.padding(8.dp)) {
        Column(Modifier.padding(all = 8.dp)) {
            Text(
                text = post.title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "User ID: " + post.userId.toString(),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text= post.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    val post = Post(
        userId = 1,
        title = "Hello World",
        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
    SUM200VolleyDemoTheme {
        PostCard(post)
    }
}