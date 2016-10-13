/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ian McIntosh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package f3
package sample.fantasy.football

import cats.implicits._

import f3.core._

private[sample] object `2016` {

  private[this] val tieBreakers     = List(BenchPoints)
  private[this] val playoffSettings = PlayoffSettings(6, 3, 0, List(TotalPointsFor))

  private[this] val rebels = Division(
    0,
    "Rebels",
    List(
      Team(0, 0, 0, 0, "hewth"),
      Team(1, 1, 0, 0, "GBP"),
      Team(2, 2, 0, 0, "JP"),
      Team(3, 3, 0, 0, "LD"),
      Team(4, 4, 0, 0, "MLCH"),
      Team(5, 5, 0, 0, "NTAB"),
      Team(6, 6, 0, 0, "PW"),
      Team(7, 7, 0, 0, "SSP"),
      Team(8, 8, 0, 0, "SMN"),
      Team(9, 9, 0, 0, "TR")
    )
  )

  private[this] val conferences = List(
    Conference(0, "", List(rebels))
  )

  private[this] val games = List(
    // Week 1
    Game(2016, 1, 3, 6, 130.5, 156.0),
    Game(2016, 1, 7, 4, 073.0, 139.0),
    Game(2016, 1, 5, 9, 120.0, 112.0),
    Game(2016, 1, 0, 1, 095.5, 146.5),
    Game(2016, 1, 8, 2, 119.0, 122.5),
    // Week 2
    Game(2016, 2, 7, 3, 121.5, 081.0),
    Game(2016, 2, 6, 5, 100.0, 154.5),
    Game(2016, 2, 4, 0, 109.0, 090.5),
    Game(2016, 2, 9, 8, 109.5, 114.0),
    Game(2016, 2, 1, 2, 127.5, 126.5),
    // Week 3
    Game(2016, 3, 3, 5, 108.5, 100.5),
    Game(2016, 3, 0, 7, 104.5, 116.0),
    Game(2016, 3, 8, 6, 118.5, 105.5),
    Game(2016, 3, 2, 4, 102.0, 168.5),
    Game(2016, 3, 1, 9, 110.0, 094.5),
    // Week 4
    Game(2016, 4, 0, 3, 099.0, 116.0),
    Game(2016, 4, 5, 8, 121.0, 077.5),
    Game(2016, 4, 7, 2, 110.5, 148.5),
    Game(2016, 4, 6, 1, 153.5, 084.5),
    Game(2016, 4, 4, 9, 149.0, 133.0),
    // Week 5
    Game(2016, 5, 3, 8, 114.5, 053.0),
    Game(2016, 5, 2, 0, 111.5, 118.5),
    Game(2016, 5, 1, 5, 125.0, 145.0),
    Game(2016, 5, 9, 7, 122.5, 158.5),
    Game(2016, 5, 4, 6, 146.5, 089.0),
    // Week 6
    Game(2016, 6, 2, 3, 059.0, 108.0),
    Game(2016, 6, 8, 1, 065.5, 088.0),
    Game(2016, 6, 0, 9, 117.5, 097.0),
    Game(2016, 6, 5, 4, 147.5, 095.0),
    Game(2016, 6, 7, 6, 110.0, 205.0),
    // Week 7
    Game(2016, 7, 3, 1, 134.0, 128.0),
    Game(2016, 7, 9, 2, 124.5, 119.0),
    Game(2016, 7, 4, 8, 098.5, 067.5),
    Game(2016, 7, 6, 0, 115.0, 126.0),
    Game(2016, 7, 7, 5, 112.5, 084.0),
    // Week 8
    Game(2016, 8, 9, 3, 144.5, 125.0),
    Game(2016, 8, 1, 4, 081.0, 102.5),
    Game(2016, 8, 2, 6, 107.5, 106.0),
    Game(2016, 8, 8, 7, 092.5, 091.5),
    Game(2016, 8, 0, 5, 145.5, 126.0),
    // Week 9
    Game(2016, 9, 3, 4, 114.0, 135.5),
    Game(2016, 9, 6, 9, 132.0, 120.5),
    Game(2016, 9, 7, 1, 104.5, 106.5),
    Game(2016, 9, 5, 2, 115.5, 135.5),
    Game(2016, 9, 0, 8, 087.0, 088.5),
    // Week 10
    Game(2016, 10, 6, 3, 127.0, 130.5),
    Game(2016, 10, 4, 7, 117.0, 100.0),
    Game(2016, 10, 9, 5, 141.5, 121.5),
    Game(2016, 10, 1, 0, 132.0, 100.5),
    Game(2016, 10, 2, 8, 138.0, 103.0),
    // Week 11
    Game(2016, 11, 3, 7, 095.0, 154.0),
    Game(2016, 11, 5, 6, 092.0, 090.5),
    Game(2016, 11, 0, 4, 119.0, 103.5),
    Game(2016, 11, 8, 9, 066.5, 140.5),
    Game(2016, 11, 2, 1, 080.5, 147.5),
    // Week 12
    Game(2016, 12, 5, 3, 115.5, 150.0),
    Game(2016, 12, 7, 0, 137.5, 085.5),
    Game(2016, 12, 6, 8, 108.0, 116.0),
    Game(2016, 12, 4, 2, 157.0, 119.0),
    Game(2016, 12, 9, 1, 096.5, 104.5)
  )

  val season = Season(2016, conferences, 12, games, tieBreakers, playoffSettings.some)
}
