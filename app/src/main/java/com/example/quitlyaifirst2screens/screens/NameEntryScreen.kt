package com.example.quitlyaifirst2screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitlyaifirst2screens.components.QuitlyButton
import com.example.quitlyaifirst2screens.components.QuitlyKicker
import com.example.quitlyaifirst2screens.components.QuitlyTopBar
import com.example.quitlyaifirst2screens.components.TopBarLead
import com.example.quitlyaifirst2screens.ui.theme.Caprasimo
import com.example.quitlyaifirst2screens.ui.theme.Figtree
import com.example.quitlyaifirst2screens.ui.theme.GroundCream
import com.example.quitlyaifirst2screens.ui.theme.Neutral200
import com.example.quitlyaifirst2screens.ui.theme.SandCard
import com.example.quitlyaifirst2screens.ui.theme.Terracotta
import com.example.quitlyaifirst2screens.ui.theme.TextDark
import com.example.quitlyaifirst2screens.ui.theme.TextMuted
import com.example.quitlyaifirst2screens.viewmodel.QuitlyViewModel

@Composable
fun NameEntryScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit,
    vm: QuitlyViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    val valid = name.trim().length >= 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GroundCream)
    ) {
        QuitlyTopBar(
            title = "",
            onLeadClick = onBack,
            lead = TopBarLead.BACK
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            QuitlyKicker("Let's make this yours")
            Spacer(Modifier.height(10.dp))
            Text(
                text = "What should\nwe call you?",
                fontFamily = Caprasimo,
                fontSize = 31.sp,
                lineHeight = 36.sp,
                color = TextDark
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Your coach uses it. Nobody else sees it.",
                fontFamily = Figtree,
                fontSize = 14.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it.take(20) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Your name",
                        fontFamily = Figtree,
                        fontSize = 15.sp,
                        color = TextMuted
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Terracotta,
                    unfocusedBorderColor = Neutral200,
                    focusedContainerColor = SandCard,
                    unfocusedContainerColor = SandCard,
                    cursorColor = Terracotta
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = Caprasimo,
                    fontSize = 20.sp,
                    color = TextDark
                )
            )
            Spacer(Modifier.height(24.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            QuitlyButton(
                text = "Continue",
                enabled = valid,
                onClick = {
                    vm.updateUserName(name)
                    onContinue()
                }
            )
        }
    }
}