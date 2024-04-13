package imd.ntub.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import imd.ntub.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // secActivity
        binding.btnOpenMeun.setOnClickListener {
            val intent = Intent(this, secActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // 從回傳 Intent 中取得選擇的主餐飲料甜點總價格
            val selectedMainMeal = data?.getStringExtra("key_selected_main_meal")
            val selectedDrink = data?.getStringExtra("key_selected_drink")
            val selectedAddons = data?.getStringArrayExtra("key_selected_addons")
            val totalPrice = data?.getIntExtra("key_total_price", 0)

            // 更新 TextView 回傳資料
            binding.txtMainIn.text = selectedMainMeal
            binding.txtDrinkIn.text = selectedDrink
            binding.txtDesertIn.text = selectedAddons?.joinToString(", ") ?: ""
            binding.txtRes.text = totalPrice.toString()
        }
    }
}
