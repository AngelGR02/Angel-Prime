package com.angelprime

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import kotlin.math.abs

class ExerciseActivity : AppCompatActivity() {

    private val rutina: Map<String, List<Exercise>> = mapOf(
        "Lunes" to listOf(
            Exercise("Press Banca Plano", "4x10", "bench.gif",
                "Acuéstate, saca el pecho y mantén los pies bien pegados al piso para tener estabilidad. Baja la barra controlado hasta el pecho inferior y empuja con fuerza hacia arriba. ¡No despegues las nalgas del banco!"),
            Exercise("Press Inclinado Barra", "3x12", "inclined.gif",
                "Es para la parte de arriba del pecho. No abras los codos hacia afuera (90°), mételos un poquito hacia tu cuerpo para no lastimarte los hombros al bajar."),
            Exercise("Peck Deck", "3x15", "peck.gif",
                "Imagina que vas a darle un abrazo a alguien muy grande. Mantén los brazos un poco doblados y aprieta fuerte el pecho 1 segundo cuando tus manos se junten al centro."),
            Exercise("Elevaciones Laterales", "4x15", "lateral.gif",
                "Para que se te vean hombros anchos. Sube las pesas hacia los lados como si estuvieras intentando volar, pero no pases de la altura de tus hombros. Controla la bajada."),
            Exercise("Ext. Vertical Neutra", "3x12", "triceps_vertical.gif",
                "Agarra la cuerda en la polea alta con las palmas mirándose. Mantén los codos pegaditos a tus orejas y estira las manos hacia el techo. Baja lento sintiendo el estiramiento en la parte de atrás del brazo.")
        ),
        "Martes" to listOf(
            Exercise("Jalón al Pecho", "4x12", "lat.gif",
                "Siéntate derecho y jala la barra hacia tu pecho (no atrás de la nuca). Imagina que quieres bajar la barra usando los codos, no las manos, para sentir la espalda."),
            Exercise("Remo con Barra", "3x10", "row.gif",
                "Inclínate hacia adelante con la espalda totalmente derecha y el abdomen duro. Jala la barra hacia tu ombligo. Si sientes que te duele la espalda baja, sube un poco el torso y dobla más las rodillas."),
            Exercise("Facepulls", "3x15", "facepull.gif",
                "Jala la cuerda hacia tu frente. Cuando llegue cerca, separa las puntas de la cuerda hacia afuera. Es perfecto para arreglar la postura de estar frente a la PC."),
            Exercise("Curl Barra Z", "3x12", "biceps_z.gif",
                "El clásico para el conejo. Mantén los codos pegados a las costillas y no te balancees con el cuerpo. Sube la pesa y bájala despacio."),
            Exercise("Curl Martillo", "3x12", "hammer.gif",
                "Igual que el anterior, pero con las palmas mirándose todo el tiempo. Esto desarrolla el músculo que hace que el brazo se vea más grueso de lado.")
        ),
        "Miercoles" to listOf(
            Exercise("Sentadilla Búlgara", "3x10", "bulgarian.gif",
                "Pon un pie atrás en un banco y el otro adelante. Baja como si te fueras a hincar con la rodilla de atrás suavemente. Si te cuesta el equilibrio, agárrate de algo ligero al principio."),
            Exercise("Extensión Cuádriceps", "4x15", "leg_ext.gif",
                "En la maquinita sentado, patea hacia arriba hasta estirar las piernas. Aprieta los muslos 1 segundo arriba y baja muy suave."),
            Exercise("Elevación Pantorrilla", "4x20", "calves.gif",
                "Ponte en la orilla de un escalón o en la máquina. Baja el talón todo lo que puedas para estirar y sube lo más que puedas de puntitas. Aguanta arriba 1 segundo."),
            Exercise("Plancha Abdominal", "3x60s", "plank.gif",
                "Quédate tieso como una tabla apoyado en los codos. Aprieta fuerte la panza y las nalgas. Esto te quitará el dolor de espalda baja si lo haces seguido.")
        ),
        "Jueves" to listOf(
            Exercise("Press Mancuernas", "3x10", "db_press.gif",
                "Como el de barra del lunes, pero con mancuernas. Baja las pesas controlado a los lados de tus hombros y júntalas arriba (sin que choquen)."),
            Exercise("Remo Polea Baja", "3x12", "cable_row.gif",
                "Siéntate, saca el pecho y jala el manubrio hacia tu panza. No te muevas excesivamente hacia adelante y atrás, quédate fijo."),
            Exercise("Press Militar Sentado", "3x12", "shoulder_press.gif",
                "Sentado con la espalda bien apoyada en el banco. Sube las pesas por encima de tu cabeza. Mantén tu espalda baja pegada al respaldo, no arquees como puente."),
            Exercise("Press Francés", "3x12", "french_press.gif",
                "Acostado, dobla los codos y baja las pesas suave hacia tus orejas (o sienes). Luego estira los brazos hacia el techo. ¡Solo se deben mover tus antebrazos!")
        ),
        "Viernes" to listOf(
            Exercise("Peso Muerto Rumano", "3x12", "romanian.gif",
                "Con las piernas casi estiradas (solo una flexión mínima), echa la pompa hacia atrás como si quisieras empujar una pared, mientras bajas la barra pegada a tus piernas. Solo baja hasta las rodillas para cuidar tu lumbar."),
            Exercise("Curl Femoral", "4x15", "leg_curl.gif",
                "Acostado boca abajo en la máquina, jala el rodillo con tus talones hacia tus nalgas. Mantén la cadera pegada al asiento, no la levantes para subir el peso."),
            Exercise("Zancadas (Lunges)", "3x12", "lunges.gif",
                "Da un paso largo hacia adelante y baja la rodilla de atrás casi hasta tocar el piso. Mantén el cuerpo derechito y erguido, no te inclines."),
            Exercise("Puente de Glúteo", "3x15", "glute_bridge.gif",
                "Acostado boca arriba con las rodillas dobladas, sube la cadera hacia el techo apretando las nalgas fuerte arriba 1 segundo. ¡Es el mejor para glúteo sin dolor de espalda!")
        ),
        "Dieta" to listOf(
            Exercise("Plan de Ganancia", "2700 kcal / 130g Proteína", "food.png",
                "Desayuno: Avena y Huevos. Almuerzo: Pollo con Arroz y Aguacate. Cena: Papa con Pavo. Meriendas: Yogur Griego y Nueces. Si te llenas rápido, añade una cucharada de aceite de oliva extra a tus comidas.")
        )
    )

    private lateinit var tvDayTitle: TextView
    private lateinit var tvExName: TextView
    private lateinit var tvExSets: TextView
    private lateinit var tvExTip: TextView
    private lateinit var tvExIndex: TextView
    private lateinit var ivGif: ImageView
    private lateinit var btnPrev: View
    private lateinit var btnNext: View

    private var currentDayData: List<Exercise> = emptyList()
    private var currentIndex = 0

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val day = intent.getStringExtra("DAY") ?: "Lunes"
        currentDayData = rutina[day] ?: emptyList()

        tvDayTitle = findViewById(R.id.tvDayTitle)
        tvExName   = findViewById(R.id.tvExName)
        tvExSets   = findViewById(R.id.tvExSets)
        tvExTip    = findViewById(R.id.tvExTip)
        tvExIndex  = findViewById(R.id.tvExIndex)
        ivGif      = findViewById(R.id.ivExGif)
        btnPrev    = findViewById(R.id.btnPrev)
        btnNext    = findViewById(R.id.btnNext)

        tvDayTitle.text = day.uppercase()

        btnPrev.setOnClickListener { prevEx() }
        btnNext.setOnClickListener { nextEx() }
        findViewById<View>(R.id.btnBack).setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Swipe gesture
        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent, vX: Float, vY: Float): Boolean {
                val diffX = (e2.x - (e1?.x ?: e2.x))
                if (abs(diffX) > 100 && abs(vX) > 100) {
                    if (diffX < 0) nextEx() else prevEx()
                    return true
                }
                return false
            }
        })

        updateExercise()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun updateExercise() {
        val ex = currentDayData[currentIndex]
        tvExName.text  = ex.name
        tvExSets.text  = ex.sets
        tvExTip.text   = ex.tip
        tvExIndex.text = "${currentIndex + 1} / ${currentDayData.size}"

        btnPrev.alpha = if (currentIndex == 0) 0.3f else 1f
        btnNext.alpha = if (currentIndex == currentDayData.size - 1) 0.3f else 1f

        // Cargar imagen o GIF desde assets según extensión
        val path = "file:///android_asset/gifs/${ex.gif}"
        if (ex.gif.endsWith(".png") || ex.gif.endsWith(".jpg")) {
            Glide.with(this)
                .load(path)
                .into(ivGif)
        } else {
            Glide.with(this)
                .asGif()
                .load(path)
                .into(ivGif)
        }
    }

    private fun nextEx() {
        if (currentIndex < currentDayData.size - 1) {
            currentIndex++
            updateExercise()
        }
    }

    private fun prevEx() {
        if (currentIndex > 0) {
            currentIndex--
            updateExercise()
        }
    }
}

data class Exercise(val name: String, val sets: String, val gif: String, val tip: String)
