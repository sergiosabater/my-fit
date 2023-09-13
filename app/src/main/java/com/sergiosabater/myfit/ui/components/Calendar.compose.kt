package com.sergiosabater.myfit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiosabater.myfit.model.DayOfWeek
import com.sergiosabater.myfit.model.DaysOfMonth

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    startDayOfWeek: DayOfWeek,
    totalDays: DaysOfMonth
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .border(2.dp, Color.Blue, shape = RoundedCornerShape(24.dp)),  // Bordes redondeados aquí),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WeekDaysHeader()
        CalendarDaysGrid(startDayOfWeek, totalDays)
    }
}

@Composable
fun WeekDaysHeader() {
    val daysOfWeek = listOf("L", "M", "X", "J", "V", "S", "D")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))  // Agrega clip aquí
            .background(Color.Blue),  // Fondo azul para la Row entera
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { dayName ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dayName,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CalendarDaysGrid(startDayOfWeek: DayOfWeek, totalDays: DaysOfMonth) {

    val days = (1..totalDays.value).toList()
    val emptyDaysAtStart = startDayOfWeek.ordinal - 1
    val totalDaysWithEmptySpaces = emptyDaysAtStart + days.size
    val emptyDaysAtEnd =
        if (totalDaysWithEmptySpaces % 7 == 0) 0 else 7 - (totalDaysWithEmptySpaces % 7)
    val completeMonth = List(emptyDaysAtStart) { 0 } + days + List(emptyDaysAtEnd) { 0 }

    completeMonth.chunked(7).forEach { week ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            week.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                ) {
                    if (day != 0) {
                        DayCell(day, day in listOf(1, 5, 8, 15))
                    }
                }
            }
        }
    }
}

@Composable
fun DayCell(day: Int, hasEvent: Boolean) {
    if (day != 0) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (hasEvent) {
                Box(
                    modifier = Modifier
                        .size(40.dp) // Asegura que sea cuadrado
                        .background(Color.Blue, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = day.toString(), color = Color.White)
                }
            } else {
                Text(text = day.toString(), color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    Calendar(startDayOfWeek = DayOfWeek.Wednesday, totalDays = DaysOfMonth(31))
}


