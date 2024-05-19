package dataAnalyzer.domain.models.builder

import kotlinx.datetime.LocalDateTime

data class LiveDeliveryItem(
    val time : LocalDateTime,
    val extra : Float
)
