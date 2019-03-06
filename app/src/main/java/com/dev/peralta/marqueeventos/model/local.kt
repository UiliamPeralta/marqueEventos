package com.dev.peralta.marqueeventos.model

data class Local(val estados: List<Estado>)
data class Estado(val sigla: String, val cidades: List<String>)

