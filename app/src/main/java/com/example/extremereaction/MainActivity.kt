package com.example.extremereaction

import android.app.*
import android.os.*
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.*
import android.widget.*
import kotlin.math.max
import kotlin.random.Random

class MainActivity : Activity() {
    private lateinit var root: LinearLayout
    private lateinit var title: TextView
    private lateinit var info: TextView
    private lateinit var button: TextView
    private lateinit var timer: TextView
    private lateinit var scoreView: TextView
    private var score=0; private var combo=0; private var round=0; private var playing=false; private var deadline=0L
    private val handler=Handler(Looper.getMainLooper())
    private var correct=""
    private val colors=listOf("红色" to Color.rgb(255,75,95),"蓝色" to Color.rgb(70,145,255),"绿色" to Color.rgb(50,205,130),"黄色" to Color.rgb(255,205,60))

    override fun onCreate(b: Bundle?) { super.onCreate(b); build(); showHome() }
    private fun build(){
        root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(28,28,28,28);setBackgroundColor(Color.rgb(9,11,20))}
        title=TextView(this).apply{textSize=30f;setTextColor(Color.WHITE);gravity=Gravity.CENTER;fontWeight=700}
        info=TextView(this).apply{textSize=16f;setTextColor(Color.LTGRAY);gravity=Gravity.CENTER;setPadding(0,18,0,18)}
        timer=TextView(this).apply{textSize=48f;setTextColor(Color.WHITE);gravity=Gravity.CENTER}
        scoreView=TextView(this).apply{textSize=15f;setTextColor(Color.GRAY);gravity=Gravity.CENTER}
        button=TextView(this).apply{textSize=34f;setTextColor(Color.WHITE);gravity=Gravity.CENTER;setPadding(20,20,20,20)}
        root.addView(title,LinearLayout.LayoutParams(-1,0,0.12f));root.addView(timer,LinearLayout.LayoutParams(-1,0,0.12f));root.addView(info,LinearLayout.LayoutParams(-1,0,0.16f));root.addView(button,LinearLayout.LayoutParams(-1,0,0.48f));root.addView(scoreView,LinearLayout.LayoutParams(-1,0,0.12f));setContentView(root)
    }
    private fun bg(c:Int,r:Float=32f)=GradientDrawable().apply{setColor(c);cornerRadius=r}
    private fun showHome(){ playing=false; title.text="⚡ 10秒极限反应"; timer.text="10"; info.text="看清指令，立刻点击。\n错一次，连击归零。"; scoreView.text="最高分：${getPreferences(0).getInt("best",0)}";button.text="开始游戏";button.background=bg(Color.rgb(103,70,240));button.setOnClickListener{startGame()} }
    private fun startGame(){score=0;combo=0;round=0;playing=true;nextRound()}
    private fun nextRound(){ if(!playing)return; round++; val remaining=max(0,10000-(System.currentTimeMillis()-deadline)); if(round==1)deadline=System.currentTimeMillis()+10000
        val target=colors.random(); correct=target.first
        val mode=Random.nextInt(3); val text=when(mode){0->"点击 ${target.first}";1->"不要点 ${colors.filter{it.first!=target.first}.random().first}";else->"点击${target.first}色块"}
        info.text=text; scoreView.text="得分 $score   •   连击 x$combo   •   第 $round 回合"
        val shown=if(mode==1) colors.filter{it.first!=target.first}.random() else target
        button.text=shown.first; button.background=bg(shown.second); button.setOnClickListener{ if(!playing)return@setOnClickListener; if(mode==1 && shown.first!=correct || mode!=1 && shown.first==correct) hit() else miss() }
        tick()
    }
    private fun hit(){ val add=10+combo*3;score+=add;combo++; if(System.currentTimeMillis()<deadline)nextRound() else finish() }
    private fun miss(){combo=0;finish("反应错误！")}
    private fun tick(){ if(!playing)return; val left=deadline-System.currentTimeMillis(); if(left<=0){finish();return};timer.text=String.format("%.1f",left/1000.0);handler.postDelayed({tick()},80) }
    private fun finish(reason:String="时间到！") { if(!playing)return;playing=false;handler.removeCallbacksAndMessages(null);val p=getPreferences(0);val old=p.getInt("best",0);if(score>old)p.edit().putInt("best",score).apply();title.text=when{score>=220->"🔥 反应怪物";score>=140->"⚡ 手速离谱";score>=70->"🧠 还不错";else->"🥲 人类需要练习"};timer.text=score.toString();info.text="$reason\n本局 $score 分，最高 ${max(score,old)} 分";scoreView.text="连击最高 x$combo";button.text="再来一局";button.background=bg(Color.rgb(103,70,240));button.setOnClickListener{startGame()} }
}
