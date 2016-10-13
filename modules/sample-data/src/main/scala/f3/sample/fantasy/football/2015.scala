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

private[sample] object `2015` {

  private[this] val tieBreakers     = List(BenchPoints)
  private[this] val playoffSettings = PlayoffSettings(6, 3, 0, List(TotalPointsFor))

  private[this] val rebels = Division(
    0,
    "Rebels",
    List(
      Team(0, 0, 0, 0, "HL"),
      Team(1, 1, 0, 0, "GBP"),
      Team(2, 2, 0, 0, "JP"),
      Team(3, 3, 0, 0, "SOG"),
      Team(4, 4, 0, 0, "BL"),
      Team(5, 5, 0, 0, "NTAB"),
      Team(6, 6, 0, 0, "BM"),
      Team(7, 7, 0, 0, "SSP"),
      Team(8, 8, 0, 0, "SMM"),
      Team(9, 9, 0, 0, "TR")
    )
  )

  private[this] val conferences = List(
    Conference(0, "", List(rebels))
  )

  private[this] val games = List(
    // Week 1
    Game(2015, 1, 4, 9, 118.0, 120.0),
    Game(2015, 1, 3, 5, 144.0, 129.0),
    Game(2015, 1, 7, 1, 111.0, 152.0),
    Game(2015, 1, 2, 0, 116.5, 095.5),
    Game(2015, 1, 6, 8, 085.0, 108.5),
    // Week 2
    Game(2015, 2, 7, 3, 086.0, 117.0),
    Game(2015, 2, 9, 2, 099.5, 130.5),
    Game(2015, 2, 4, 6, 099.5, 089.5),
    Game(2015, 2, 5, 0, 137.0, 073.5),
    Game(2015, 2, 1, 8, 133.0, 101.5),
    // Week 3
    Game(2015, 3, 6, 2, 149.5, 118.5),
    Game(2015, 3, 0, 3, 155.5, 063.5),
    Game(2015, 3, 8, 7, 70.5, 194.5),
    Game(2015, 3, 5, 9, 165.0, 126.0),
    Game(2015, 3, 1, 4, 124.0, 121.5),
    // Week 4
    Game(2015, 4, 8, 0, 073.5, 157.0),
    Game(2015, 4, 2, 5, 096.5, 082.0),
    Game(2015, 4, 6, 1, 075.5, 094.0),
    Game(2015, 4, 3, 9, 106.5, 084.5),
    Game(2015, 4, 7, 4, 137.5, 114.0),
    // Week 5
    Game(2015, 5, 1, 5, 152.5, 097.5),
    Game(2015, 5, 9, 0, 081.5, 160.0),
    Game(2015, 5, 4, 8, 090.5, 110.5),
    Game(2015, 5, 3, 2, 112.5, 093.5),
    Game(2015, 5, 7, 6, 142.0, 086.0),
    // Week 6
    Game(2015, 6, 3, 4, 148.5, 123.5),
    Game(2015, 6, 2, 7, 145.0, 125.0),
    Game(2015, 6, 0, 6, 165.0, 106.5),
    Game(2015, 6, 5, 8, 087.0, 104.0),
    Game(2015, 6, 9, 1, 118.0, 111.5),
    // Week 7
    Game(2015, 7, 4, 2, 115.0, 097.0),
    Game(2015, 7, 7, 0, 134.5, 113.0),
    Game(2015, 7, 6, 5, 140.0, 104.5),
    Game(2015, 7, 8, 9, 097.5, 135.0),
    Game(2015, 7, 1, 3, 115.0, 141.0),
    // Week 8
    Game(2015, 8, 0, 4, 097.5, 094.5),
    Game(2015, 8, 5, 7, 100.5, 111.0),
    Game(2015, 8, 9, 6, 111.0, 093.5),
    Game(2015, 8, 3, 8, 126.0, 141.0),
    Game(2015, 8, 2, 1, 118.0, 139.5),
    // Week 9
    Game(2015, 9, 9, 7, 123.5, 102.5),
    Game(2015, 9, 5, 4, 147.5, 119.5),
    Game(2015, 9, 0, 1, 135.5, 161.0),
    Game(2015, 9, 2, 8, 137.5, 117.0),
    Game(2015, 9, 3, 6, 074.0, 107.5),
    // Week 10
    Game(2015, 10, 4, 3, 110.0, 138.5),
    Game(2015, 10, 7, 2, 085.0, 061.0),
    Game(2015, 10, 6, 0, 123.0, 092.0),
    Game(2015, 10, 8, 5, 143.5, 108.0),
    Game(2015, 10, 1, 9, 094.0, 139.0),
    // Week 11
    Game(2015, 11, 2, 4, 087.0, 076.5),
    Game(2015, 11, 0, 7, 138.0, 129.5),
    Game(2015, 11, 5, 6, 070.0, 122.0),
    Game(2015, 11, 9, 8, 113.0, 071.5),
    Game(2015, 11, 3, 1, 052.0, 113.5),
    // Week 12
    Game(2015, 12, 4, 0, 109.5, 119.5),
    Game(2015, 12, 7, 5, 121.0, 110.0),
    Game(2015, 12, 6, 9, 080.0, 120.0),
    Game(2015, 12, 8, 3, 127.5, 148.0),
    Game(2015, 12, 1, 2, 099.5, 144.5),
    // Week 13 (Playoffs Round 1)
    Game(2015, 13, 7, 0, 160.0, 132.5),
    Game(2015, 13, 2, 9, 126.0, 087.0),
    Game(2015, 13, 6, 8, 104.5, 134.5),
    Game(2015, 13, 4, 5, 107.5, 158.5),
    Game(2015, 13, -1, 1, 000.0, 000.0),
    Game(2015, 13, -1, 3, 000.0, 000.0),
    // Week 14 (Playoffs Round 2)
    Game(2015, 14, 7, 1, 105.0, 140.0),
    Game(2015, 14, 2, 3, 149.5, 112.5),
    Game(2015, 14, 0, 9, 133.0, 080.5),
    Game(2015, 14, 5, 8, 124.0, 095.0),
    Game(2015, 14, 4, 6, 116.0, 085.5),
    // Week 15 (Playoffs Round 3)
    Game(2015, 15, 2, 1, 126.5, 141.5),
    Game(2015, 15, 7, 3, 132.0, 171.5),
    Game(2015, 15, 0, 9, 128.0, 089.0),
    Game(2015, 15, 4, 5, 112.5, 135.0),
    Game(2015, 15, 6, 8, 100.5, 118.5)
  )

  val season = Season(2015, conferences, 12, games, tieBreakers, playoffSettings.some)
}
