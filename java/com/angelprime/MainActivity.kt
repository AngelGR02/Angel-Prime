package com.angelprime

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val days = listOf(
            DayItem("Lunes",    "Empuje: Pecho y Tríceps",       false),
            DayItem("Martes",   "Tracción: Espalda y Bíceps",    false),
            DayItem("Miercoles","Pierna (Cuidado Lumbar)",        false),
            DayItem("Jueves",   "Torso Híbrido",                 false),
            DayItem("Viernes",  "Pierna (Cuidado Femoral)",       false),
            DayItem("Dieta",    "Plan Nutricional: 2,700 kcal",  true)
        )

        val rv = findViewById<RecyclerView>(R.id.rvDays)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = DayAdapter(days) { day ->
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("DAY", day.name)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}

data class DayItem(val name: String, val focus: String, val isDiet: Boolean)

class DayAdapter(
    private val items: List<DayItem>,
    private val onClick: (DayItem) -> Unit
) : RecyclerView.Adapter<DayAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView  = view.findViewById(R.id.tvDayName)
        val tvFocus: TextView = view.findViewById(R.id.tvDayFocus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layout = if (viewType == 1) R.layout.item_day_diet else R.layout.item_day
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return VH(v)
    }

    override fun getItemViewType(position: Int) = if (items[position].isDiet) 1 else 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvName.text  = item.name
        holder.tvFocus.text = item.focus
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
