package imd.ntub.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import imd.ntub.myapplication.databinding.ActivitySecBinding
import androidx.appcompat.app.AlertDialog

class secActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 宣告
        val rbnHaw = findViewById<RadioButton>(R.id.rbnHaw)
        val rbnMa = findViewById<RadioButton>(R.id.rbnMa)
        val rbnDgc = findViewById<RadioButton>(R.id.rbnDgc)
        val rbnCola = findViewById<RadioButton>(R.id.rbnCola)
        val rbnRed = findViewById<RadioButton>(R.id.rbnRed)
        val chbSeven = findViewById<CheckBox>(R.id.chbSeven)
        val chbBlack = findViewById<CheckBox>(R.id.chbBlack)

        findViewById<Button>(R.id.btnCount).setOnClickListener {
            // 取得所選擇的主餐飲料甜點
            val selectedMainMeal = when {
                rbnHaw.isChecked -> "夏威夷披薩"
                rbnMa.isChecked -> "美式臘腸披薩"
                rbnDgc.isChecked -> "地中海披薩"
                else -> ""
            }

            val selectedDrink = when {
                rbnCola.isChecked -> "可樂"
                rbnRed.isChecked -> "紅茶"
                else -> ""
            }

            val selectedAddons = mutableListOf<String>()
            if (chbSeven.isChecked) selectedAddons.add("戚風蛋糕")
            if (chbBlack.isChecked) selectedAddons.add("黑森林")

            // 計算價格
            val totalPrice = calculateTotalPrice(selectedMainMeal, selectedDrink, selectedAddons)

            // 顯示確認對話框
            showConfirmationDialog(selectedMainMeal, selectedDrink, selectedAddons, totalPrice)
        }
    }

    // 顯示確認對話框
    private fun showConfirmationDialog(mainMeal: String, drink: String, addons: List<String>, totalPrice: Int) {
        AlertDialog.Builder(this)
            .setTitle("通知!!")
            .setMessage("是否確定送出")
            .setPositiveButton("確定") { dialog, i ->
                // 回傳的Intent
                val resultIntent = Intent()
                resultIntent.putExtra("key_selected_main_meal", mainMeal)
                resultIntent.putExtra("key_selected_drink", drink)
                resultIntent.putExtra("key_selected_addons", addons.toTypedArray())
                resultIntent.putExtra("key_total_price", totalPrice)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            .setNegativeButton("再想一下") { dialog, i -> }
            .show()
    }

    // 計算價格
    private fun calculateTotalPrice(mainMeal: String, drink: String, addons: List<String>): Int {
        val mainMealPrice = getPriceOfItem(mainMeal)
        val drinkPrice = getPriceOfItem(drink)
        val addonsPrice = addons.sumBy { getPriceOfItem(it) }
        return mainMealPrice + drinkPrice + addonsPrice
    }

    // 價格表
    private fun getPriceOfItem(item: String): Int {
        return when (item) {
            "夏威夷披薩" -> 100
            "美式臘腸披薩" -> 90
            "地中海披薩" -> 120
            "可樂" -> 10
            "紅茶" -> 30
            "戚風蛋糕" -> 50
            "黑森林" -> 90
            else -> 0
        }
    }
}
