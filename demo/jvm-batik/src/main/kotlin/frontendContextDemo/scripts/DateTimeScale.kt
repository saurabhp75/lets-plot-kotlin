/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package frontendContextDemo.scripts

import jetbrains.datalore.base.datetime.Date
import jetbrains.datalore.base.datetime.DateTime
import jetbrains.datalore.base.datetime.Month
import jetbrains.datalore.base.datetime.tz.TimeZone
import jetbrains.letsPlot.geom.geom_line
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.scale.scale_x_datetime
import frontendContextDemo.ScriptInBatikContext
import kotlin.random.Random

object DateTimeScale {
    @JvmStatic
    fun main(args: Array<String>) {
        ScriptInBatikContext.eval("DateTime Scale") {
            val second = 1000.0
            val minute = 60.0 * second
            val hour = 60.0 * minute
            val day = 24.0 * hour

            val instant = TimeZone.UTC.toInstant(DateTime(Date(1, Month.FEBRUARY, 2003)))

            val nDays = 30
            val rnd = Random(0)

            val daysData = mapOf<String, Any>(
                "days" to (0..nDays).map { instant.timeSinceEpoch + it * day },
                "val" to (0..nDays).map { rnd.nextDouble(0.0, 20.0) }
            )

            val p = ggplot(daysData) +
                    geom_line() { x = "days"; y = "val" } +
                    scale_x_datetime()

            p.show()

            val nSeconds = 1000
            val secondsData = mapOf<String, Any>(
                "seconds" to (0..nSeconds).map { instant.timeSinceEpoch + it * second },
                "val" to (0..nSeconds).map { rnd.nextDouble(0.0, 10.0) }
            )

            val t = ggplot(secondsData) +
                    geom_line() { x = "seconds"; y = "val" } +
                    scale_x_datetime("Time (min)")
            t.show()
        }
    }
}