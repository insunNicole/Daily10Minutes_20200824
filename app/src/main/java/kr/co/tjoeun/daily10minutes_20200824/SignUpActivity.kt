package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        이메일 입력칸의 내용이 변경된 경우 => 중복검사를 다시 하도록 유도

        signUpEmailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("입력문구", p0.toString())

                emailCheckResultTxt.text = "중복 확인을 해주세요."
            }

        })

        emailCheckBtn.setOnClickListener {

//            입력된이메일 확인 => 해당 이메일을 서버에 중복검사기능 요청 => 결과에 따른 문구 반영

            val inputEmail = signUpEmailEdt.text.toString()

            ServerUtil.getRequestEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    val code = json.getInt("code")

                    runOnUiThread {
                        if (code == 200) {
                            emailCheckResultTxt.text = "사용해도 좋은 이메일 입니다."
                        }
                        else {
                            emailCheckResultTxt.text = "중복된 이메일입니다."
                        }
                    }



                }

            })

        }

    }

    override fun setValues() {

    }

}