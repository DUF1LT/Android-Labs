package by.belstu.narkevich.lab14.data

import android.location.Location
import java.util.*

class Route(val id: String = UUID.randomUUID().toString(), var startLocation: Location?, var finishLocation: Location?)