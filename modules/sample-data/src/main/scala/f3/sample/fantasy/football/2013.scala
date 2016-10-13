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

private[sample] object `2013` {

  private[this] val tieBreakers     = List(BenchPoints)
  private[this] val playoffSettings = PlayoffSettings(6, 3, 0, List(TotalPointsFor))

  private[this] val rebels = Division(
    0,
    "Rebels",
    List(
      Team(0, 0, 0, 0, "GP"),
      Team(2, 2, 0, 0, "TDD"),
      Team(3, 3, 0, 0, "BM"),
      Team(5, 5, 0, 0, "NTAB"),
      Team(9, 9, 0, 0, "YCYB")
    ))

  private[this] val outlaws = Division(
    1,
    "Outlaws",
    List(
      Team(1, 1, 0, 1, "GBP"),
      Team(4, 4, 0, 1, "SM"),
      Team(6, 6, 0, 1, "TFTF"),
      Team(7, 7, 0, 1, "CC"),
      Team(8, 8, 0, 1, "TM")
    ))

  private[this] val conferences = List(
    Conference(0, "", List(rebels, outlaws))
  )

  private[this] val games = List(
    // Week 1
    Game(2013, 1, 4, 9, 145.5, 123.0),
    Game(2013, 1, 3, 5, 203.0, 87.0),
    Game(2013, 1, 7, 1, 095.0, 113.0),
    Game(2013, 1, 2, 0, 131.0, 098.5),
    Game(2013, 1, 6, 8, 127.5, 164.5),
    // Week 2
    Game(2013, 2, 7, 3, 139.0, 099.5),
    Game(2013, 2, 9, 2, 100.0, 105.5),
    Game(2013, 2, 4, 6, 109.5, 100.0),
    Game(2013, 2, 5, 0, 147.5, 129.0),
    Game(2013, 2, 1, 8, 071.0, 101.0),
    // Week 3
    Game(2013, 3, 6, 2, 098.5, 104.0),
    Game(2013, 3, 0, 3, 094.0, 085.5),
    Game(2013, 3, 8, 7, 118.5, 153.0),
    Game(2013, 3, 5, 9, 111.0, 101.5),
    Game(2013, 3, 1, 4, 108.0, 107.5),
    // Week 4
    Game(2013, 4, 8, 0, 139.0, 092.5),
    Game(2013, 4, 2, 5, 168.5, 096.5),
    Game(2013, 4, 6, 1, 152.0, 152.5),
    Game(2013, 4, 3, 9, 147.0, 151.5),
    Game(2013, 4, 7, 4, 107.5, 121.0),
    // Week 5
    Game(2013, 5, 1, 5, 115.5, 171.5),
    Game(2013, 5, 9, 0, 087.5, 095.5),
    Game(2013, 5, 4, 8, 084.0, 122.0),
    Game(2013, 5, 3, 2, 106.0, 124.0),
    Game(2013, 5, 7, 6, 105.0, 095.5),
    // Week 6
    Game(2013, 6, 3, 4, 118.5, 100.0),
    Game(2013, 6, 2, 7, 086.0, 077.0),
    Game(2013, 6, 0, 6, 081.5, 098.0),
    Game(2013, 6, 5, 8, 150.0, 074.5),
    Game(2013, 6, 9, 1, 120.0, 131.0),
    // Week 7
    Game(2013, 7, 4, 2, 102.0, 120.0),
    Game(2013, 7, 7, 0, 157.5, 122.0),
    Game(2013, 7, 6, 5, 096.0, 129.0),
    Game(2013, 7, 8, 9, 107.5, 071.5),
    Game(2013, 7, 1, 3, 109.5, 139.0),
    // Week 8
    Game(2013, 8, 0, 4, 091.0, 083.5),
    Game(2013, 8, 5, 7, 147.5, 112.0),
    Game(2013, 8, 9, 6, 130.5, 128.5),
    Game(2013, 8, 3, 8, 108.0, 148.0),
    Game(2013, 8, 2, 1, 089.0, 132.5),
    // Week 9
    Game(2013, 9, 9, 7, 135.5, 99.5),
    Game(2013, 9, 5, 4, 116.5, 107.0),
    Game(2013, 9, 0, 1, 131.0, 164.0),
    Game(2013, 9, 2, 8, 146.5, 096.5),
    Game(2013, 9, 3, 6, 113.5, 055.5),
    // Week 10
    Game(2013, 10, 4, 3, 119.5, 152.5),
    Game(2013, 10, 7, 2, 118.0, 111.0),
    Game(2013, 10, 6, 0, 089.5, 083.5),
    Game(2013, 10, 8, 5, 109.5, 146.5),
    Game(2013, 10, 1, 9, 111.5, 089.0),
    // Week 11
    Game(2013, 11, 2, 4, 152.5, 093.0),
    Game(2013, 11, 0, 7, 101.5, 120.0),
    Game(2013, 11, 5, 6, 110.5, 097.0),
    Game(2013, 11, 9, 8, 114.5, 113.0),
    Game(2013, 11, 3, 1, 142.5, 084.0),
    // Week 12
    Game(2013, 12, 4, 0, 121.5, 099.5),
    Game(2013, 12, 7, 5, 101.0, 148.5),
    Game(2013, 12, 6, 9, 083.0, 072.0),
    Game(2013, 12, 8, 3, 104.0, 132.5),
    Game(2013, 12, 1, 2, 120.0, 092.5),
    // Week 13 (Playoffs Round 1)
    Game(2013, 13, 8, 3, 093.5, 122.0),
    Game(2013, 13, 7, 2, 139.0, 154.5),
    Game(2013, 13, 4, 9, 080.0, 093.0),
    Game(2013, 13, 0, 6, 124.5, 086.5),
    Game(2013, 13, -1, 5, 000.0, 000.0),
    Game(2013, 13, -1, 1, 000.0, 000.0),
    // Week 14 (Playoffs Round 2)
    Game(2013, 14, 3, 5, 120.0, 143.5),
    Game(2013, 14, 2, 1, 106.5, 177.0),
    Game(2013, 14, 7, 8, 137.5, 099.0),
    Game(2013, 14, 0, 9, 110.0, 056.5),
    Game(2013, 14, 6, 4, 093.0, 095.0),
    // Week 15 (Playoffs Round 3)
    Game(2013, 15, 1, 5, 121.5, 113.0),
    Game(2013, 15, 3, 2, 204.0, 104.0),
    Game(2013, 15, 7, 8, 084.5, 126.5),
    Game(2013, 15, 0, 4, 105.0, 106.5),
    Game(2013, 15, 6, 9, 054.5, 119.5)
  )

  val season = Season(2013, conferences, 12, games, tieBreakers, playoffSettings.some)
}
