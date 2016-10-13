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

private[sample] object `2014` {

  private[this] val tieBreakers     = List(BenchPoints)
  private[this] val playoffSettings = PlayoffSettings(6, 3, 0, List(TotalPointsFor))

  private[this] val rebels = Division(
    0,
    "Rebels",
    List(
      Team(0, 0, 0, 0, "GP"),
      Team(2, 2, 0, 0, "TDD"),
      Team(3, 3, 0, 0, "MoS"),
      Team(5, 5, 0, 0, "NTAB"),
      Team(9, 9, 0, 0, "AINIOB")
    ))

  private[this] val outlaws = Division(
    1,
    "Outlaws",
    List(
      Team(1, 1, 0, 1, "GBP"),
      Team(4, 4, 0, 1, "BYS"),
      Team(6, 6, 0, 1, "ATGHAP"),
      Team(7, 7, 0, 1, "CC"),
      Team(8, 10, 0, 1, "TM")
    ))

  private[this] val conferences = List(
    Conference(0, "", List(rebels, outlaws))
  )

  private[this] val games = List(
    // Week 1
    Game(2014, 1, 4, 9, 128.5, 118.0),
    Game(2014, 1, 3, 5, 074.5, 096.0),
    Game(2014, 1, 7, 1, 099.0, 183.5),
    Game(2014, 1, 2, 0, 110.5, 101.0),
    Game(2014, 1, 6, 8, 114.0, 084.0),
    // Week 2
    Game(2014, 2, 7, 3, 097.5, 094.0),
    Game(2014, 2, 9, 2, 080.0, 105.0),
    Game(2014, 2, 4, 6, 084.5, 130.5),
    Game(2014, 2, 5, 0, 187.0, 100.5),
    Game(2014, 2, 1, 8, 087.0, 089.0),
    // Week 3
    Game(2014, 3, 6, 2, 098.5, 095.5),
    Game(2014, 3, 0, 3, 099.0, 095.0),
    Game(2014, 3, 8, 7, 083.0, 095.0),
    Game(2014, 3, 5, 9, 112.5, 096.0),
    Game(2014, 3, 1, 4, 134.0, 086.0),
    // Week 4
    Game(2014, 4, 8, 0, 112.5, 71.5),
    Game(2014, 4, 2, 5, 102.0, 131.0),
    Game(2014, 4, 6, 1, 135.5, 110.0),
    Game(2014, 4, 3, 9, 087.0, 151.0),
    Game(2014, 4, 7, 4, 049.0, 141.5),
    // Week 5
    Game(2014, 5, 1, 5, 112.0, 147.0),
    Game(2014, 5, 9, 0, 078.5, 102.0),
    Game(2014, 5, 4, 8, 108.5, 150.5),
    Game(2014, 5, 3, 2, 140.5, 103.5),
    Game(2014, 5, 7, 6, 123.0, 108.5),
    // Week 6
    Game(2014, 6, 3, 4, 138.5, 132.0),
    Game(2014, 6, 2, 7, 098.5, 097.5),
    Game(2014, 6, 0, 6, 088.5, 066.5),
    Game(2014, 6, 5, 8, 143.0, 132.0),
    Game(2014, 6, 9, 1, 085.0, 123.5),
    // Week 7
    Game(2014, 7, 4, 2, 115.5, 080.0),
    Game(2014, 7, 7, 0, 083.0, 094.5),
    Game(2014, 7, 6, 5, 110.0, 153.0),
    Game(2014, 7, 8, 9, 110.5, 072.5),
    Game(2014, 7, 1, 3, 116.0, 98.5),
    // Week 8
    Game(2014, 8, 0, 4, 164.0, 151.5),
    Game(2014, 8, 5, 7, 158.5, 112.5),
    Game(2014, 8, 9, 6, 074.5, 086.5),
    Game(2014, 8, 3, 8, 112.5, 176.5),
    Game(2014, 8, 2, 1, 104.5, 110.0),
    // Week 9
    Game(2014, 9, 9, 7, 64.5, 103.5),
    Game(2014, 9, 5, 4, 115.0, 103.5),
    Game(2014, 9, 0, 1, 174.5, 141.0),
    Game(2014, 9, 2, 8, 078.0, 084.0),
    Game(2014, 9, 3, 6, 132.0, 099.0),
    // Week 10
    Game(2014, 10, 4, 3, 113.0, 142.5),
    Game(2014, 10, 7, 2, 105.0, 141.0),
    Game(2014, 10, 6, 0, 124.5, 112.5),
    Game(2014, 10, 8, 5, 142.0, 173.5),
    Game(2014, 10, 1, 9, 110.0, 081.5),
    // Week 11
    Game(2014, 11, 2, 4, 108.5, 135.5),
    Game(2014, 11, 0, 7, 081.5, 099.5),
    Game(2014, 11, 5, 6, 121.5, 089.5),
    Game(2014, 11, 9, 8, 085.5, 114.5),
    Game(2014, 11, 3, 1, 099.0, 106.0),
    // Week 12
    Game(2014, 12, 4, 0, 138.0, 133.5),
    Game(2014, 12, 7, 5, 091.0, 150.0),
    Game(2014, 12, 6, 9, 117.0, 083.0),
    Game(2014, 12, 8, 3, 126.5, 129.0),
    Game(2014, 12, 1, 2, 109.5, 128.5),
    // Week 13 (Playoffs Round 1)
    Game(2014, 13, 0, 6, 146.5, 098.0),
    Game(2014, 13, 4, 8, 137.5, 101.0),
    Game(2014, 13, 2, 3, 099.0, 093.5),
    Game(2014, 13, 9, 7, 081.5, 126.5),
    Game(2014, 13, -1, 5, 000.0, 000.0),
    Game(2014, 13, -1, 1, 000.0, 000.0),
    // Week 14 (Playoffs Round 2)
    Game(2014, 14, 0, 5, 129.5, 155.5),
    Game(2014, 14, 4, 1, 138.0, 184.0),
    Game(2014, 14, 6, 8, 150.5, 127.5),
    Game(2014, 14, 7, 2, 115.5, 66.5),
    Game(2014, 14, 9, 3, 108.5, 097.5),
    // Week 15 (Playoffs Round 3)
    Game(2014, 15, 1, 5, 142.0, 108.5),
    Game(2014, 15, 4, 0, 088.5, 144.5),
    Game(2014, 15, 6, 8, 084.0, 099.5),
    Game(2014, 15, 9, 7, 079.5, 134.0),
    Game(2014, 15, 2, 3, 082.5, 107.0)
  )

  val season = Season(2014, conferences, 12, games, tieBreakers, playoffSettings.some)
}
