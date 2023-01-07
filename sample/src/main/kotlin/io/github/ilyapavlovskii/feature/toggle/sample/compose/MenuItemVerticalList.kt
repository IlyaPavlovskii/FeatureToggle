package io.github.ilyapavlovskii.feature.toggle.sample.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.ilyapavlovskii.feature.toggle.sample.R
import io.github.ilyapavlovskii.feature.toggle.sample.model.MenuItem
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.MenuItemFeatureToggle

@Composable
internal fun MenuItemVerticalList(
    featureToggle: MenuItemFeatureToggle,
    items: List<MenuItem>,
) {
    LazyColumn {
        items(items) { item: MenuItem ->
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            bounded = true,
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                        onClick = {},
                    )
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp),
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp),
                    painter = painterResource(id = item.icon),
                    contentDescription = stringResource(id = R.string.menu_item__image_content_description)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                ) {
                    Text(
                        text = stringResource(id = item.title),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = item.price,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                if (featureToggle.addToCartAvailable) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple(
                                    bounded = true,
                                    color = MaterialTheme.colorScheme.secondary,
                                ),
                                onClick = {},
                            )
                            .padding(8.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_shopping_cart_24),
                        contentDescription = stringResource(id = R.string.menu_item__add_to_cart_content_description)
                    )
                }

            }
        }
    }
}