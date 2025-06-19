package org.example.kepos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun NewGreenhouse(
    idDendro: String,
    onIdDendroChange: (String) -> Unit,
    onSearchGreenhouse: () -> Unit
) {
    val isInputValid = idDendro.length == 12

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Adicionar estufa",
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF2F855A)
        )

        Spacer(modifier = Modifier.height(48.dp))

        TextField(
            value = idDendro,
            onValueChange = {
                if (it.length <= 12) {
                    onIdDendroChange(it)
                }
            },
            placeholder = {
                Text(
                    "ABCD-EFGH-IJKL",
                    color = Color(0xFF9E9E9E),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .width(320.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF38A169),
                unfocusedIndicatorColor = Color(0xFF38A169),
                disabledIndicatorColor = Color.Gray,
                cursorColor = Color(0xFF38A169)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Characters
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

        TextButton(
            onClick = onSearchGreenhouse,
            enabled = isInputValid,
            colors = ButtonDefaults.textButtonColors(
                contentColor = if (isInputValid) Color(0xFF2F855A) else Color.LightGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buscar",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}
