package by.bulba.feature.toggle.sample.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import by.bulba.feature.toggle.sample.R
import by.bulba.feature.toggle.sample.model.MenuItem
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle

@Composable
internal fun MenuItemCard(featureToggle: MenuItemFeatureToggle, item: MenuItem) {
    Column(
        Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(128.dp)
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
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            painter = painterResource(id = item.icon),
            contentDescription = stringResource(id = R.string.menu_item__image_content_description)
        )
        Row {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(id = item.title),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = item.price,
                    maxLines = 1,
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