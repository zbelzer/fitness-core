/**
  * From http://www.exrx.net/Lists/Directory.html
  */

package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.model.MuscleGroup.{Core, LowerBody, UpperBody}
import enumeratum._

sealed trait MuscleGroup extends EnumEntry
object MuscleGroup extends Enum[MuscleGroup] {
  val values = findValues

  case object UpperBody extends MuscleGroup
  case object LowerBody extends MuscleGroup
  case object Core extends MuscleGroup
}

sealed class Muscle(val group: MuscleGroup) extends EnumEntry
object Muscle extends Enum[Muscle] {
  val values = findValues

  case object Adductors extends Muscle(Core)
  case object ErectorSpinae extends Muscle(Core)
  case object Obliques extends Muscle(Core)
  case object QuadratusLumborum extends Muscle(Core)
  case object RectusAbdominis extends Muscle(Core)
  case object SerratusAnterior extends Muscle(Core)
  case object TransverseAbdominus extends Muscle(Core)

  case object BicepsBrachii extends Muscle(UpperBody)
  case object Brachialis extends Muscle(UpperBody)
  case object Brachioradialis extends Muscle(UpperBody)
  case object DeltoidAnterior extends Muscle(UpperBody)
  case object DeltoidLateral extends Muscle(UpperBody)
  case object DeltoidPosterior extends Muscle(UpperBody)
  case object Infraspinatus extends Muscle(UpperBody)
  case object LatissimusDorsi extends Muscle(UpperBody)
  case object LevatorScapulae extends Muscle(UpperBody)
  case object PectoralisMajor extends Muscle(UpperBody)
  case object PectoralisMinor extends Muscle(UpperBody)
  case object Rhomboids extends Muscle(UpperBody)
  case object Splenius extends Muscle(UpperBody)
  case object Sternocleidomastoid extends Muscle(UpperBody)
  case object Subscapularis extends Muscle(UpperBody)
  case object Supraspinatus extends Muscle(UpperBody)
  case object TeresMajor extends Muscle(UpperBody)
  case object TeresMinor extends Muscle(UpperBody)
  case object TrapeziusLower extends Muscle(UpperBody)
  case object TrapeziusMiddle extends Muscle(UpperBody)
  case object TrapeziusUpper extends Muscle(UpperBody)
  case object TricepsBrachii extends Muscle(UpperBody)
  case object WristExensors extends Muscle(UpperBody)
  case object WristFlexors extends Muscle(UpperBody)

  case object Gastrocnemius extends Muscle(LowerBody)
  case object GluteusMaximus extends Muscle(LowerBody)
  case object GluteusMedius extends Muscle(LowerBody)
  case object GluteusMinimus extends Muscle(LowerBody)
  case object Gracilis extends Muscle(LowerBody)
  case object Hamstrings extends Muscle(LowerBody)
  case object HipExternalRotators extends Muscle(LowerBody)
  case object Iliopsoas extends Muscle(LowerBody)
  case object Pectineous extends Muscle(LowerBody)
  case object Popliteus extends Muscle(LowerBody)
  case object Quadriceps extends Muscle(LowerBody)
  case object RectusFemoris extends Muscle(LowerBody)
  case object Sartorius extends Muscle(LowerBody)
  case object Soleus extends Muscle(LowerBody)
  case object TensorFasciaeLatae extends Muscle(LowerBody)
  case object TibialisAnterior extends Muscle(LowerBody)
}
