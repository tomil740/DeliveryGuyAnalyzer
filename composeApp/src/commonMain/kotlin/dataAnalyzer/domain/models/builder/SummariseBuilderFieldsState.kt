package dataAnalyzer.domain.models.builder


/*
SummariseBuilderFieldsState :
this class represent the builder state at live in order of presenting in sync live the
builder sumarise attributes of its data
 */
data class SummariseBuilderFieldsState(
    val totalTime:Boolean,
    val extra:Boolean,
    val delivers: Boolean
)